package com.msd.summer;

import com.msd.summer.config.ApplicationConfig;

public class Application {

  public static ApplicationContext prepareContext(String packageToScan) throws Exception {
    ApplicationConfig config = new ApplicationConfig(packageToScan);

    ApplicationContext context = new ApplicationContext(config);

    BeanFactory factory = new BeanFactory(context);

    context.setFactory(factory);

    ComponentScanner scanner = new ComponentScanner(context);
    scanner.loadComponents();

    return context;
  }

  public static void run(Class<?> mainClass, String[] args) throws Exception {
    var packageName = mainClass.getPackage().getName();
    var context = Application.prepareContext(packageName);
    Server.start(context);
  }
}
