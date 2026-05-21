package com.msd.summer.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import org.reflections.Reflections;

public class ApplicationConfig implements Config {

  @Getter private final Reflections internalScanner;
  @Getter private final Reflections externalScanner;
  private final Map<Class, Class> ifc2ImplClass;

  public ApplicationConfig(String packageToScan) {
    this.internalScanner = new Reflections("com.msd.summer");
    this.externalScanner = new Reflections(packageToScan);
    this.ifc2ImplClass = new HashMap<>();
  }

  @Override
  public <T> Class<? extends T> getImplClass(Class<T> ifc) {
    return ifc2ImplClass.computeIfAbsent(
        ifc,
        aClass -> {
          Set<Class<? extends T>> classes = externalScanner.getSubTypesOf(ifc);

          // TODO: load all impls

          return classes.iterator().next();
        });
  }
}
