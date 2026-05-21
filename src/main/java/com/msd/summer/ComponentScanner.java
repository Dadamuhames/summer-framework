package com.msd.summer;

import com.msd.summer.annotation.Bean;
import com.msd.summer.annotation.Component;
import com.msd.summer.annotation.Configuration;
import com.msd.summer.annotation.Controller;
import org.reflections.Reflections;

public class ComponentScanner {

  private final ApplicationContext context;

  public ComponentScanner(ApplicationContext context) {
    this.context = context;
  }

  public void loadComponents() throws Exception {
    var scanner = context.getConfig().getExternalScanner();

    scanComponents(scanner);
    scanConfigs(scanner);
    scanControllers(scanner);
  }

  private void scanComponents(final Reflections scanner) {
    var components = scanner.getTypesAnnotatedWith(Component.class);

    for (var component : components) {
      context.createAndStoreBean(component);
    }
  }

  private void scanControllers(final Reflections scanner) {
    var components = scanner.getTypesAnnotatedWith(Controller.class);

    for (var component : components) {
      context.createAndStoreBean(component);
    }
  }

  private void scanConfigs(final Reflections scanner) throws Exception {
    var configurations = scanner.getTypesAnnotatedWith(Configuration.class);

    for (var config : configurations) {
      var configInstance = context.createAndStoreBean(config);

      var methods = configInstance.getClass().getMethods();

      for (var method : methods) {
        if (!method.isAnnotationPresent(Bean.class)) continue;

        if (method.getReturnType().equals(Void.class)) {}

        Object bean = method.invoke(configInstance);

        context.storeBean(bean);
      }
    }
  }
}
