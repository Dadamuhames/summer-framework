package com.msd.summer;

import com.msd.summer.config.Config;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.Setter;

public class ApplicationContext {

  @Setter private BeanFactory factory;
  private final Map<Class, Object> context = new ConcurrentHashMap<>();
  @Getter private final Config config;

  public ApplicationContext(Config config) {
    this.config = config;
  }

  public <T> T createAndStoreBean(Class<T> implClass) {
    T bean = factory.createBean(implClass);
    context.put(implClass, bean);
    return bean;
  }

  public void storeBean(Object bean) {
    context.put(bean.getClass(), bean);
  }

  @SuppressWarnings("unchecked")
  public <T> T getBean(Class<T> type) {
    if (context.containsKey(type)) {
      return (T) context.get(type);
    }

    Class<? extends T> implClass = type;

    if (type.isInterface()) {
      implClass = config.getImplClass(type);
    }
    T t = factory.createBean(implClass);

    context.put(type, t);

    return t;
  }
}
