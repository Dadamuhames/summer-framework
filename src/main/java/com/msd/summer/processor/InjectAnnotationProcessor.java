package com.msd.summer.processor;

import com.msd.summer.ApplicationContext;
import com.msd.summer.annotation.Inject;
import lombok.SneakyThrows;

public class InjectAnnotationProcessor implements BeanProcessor {

  @Override
  @SneakyThrows
  public void process(Object t, ApplicationContext context) {
    for (var field : t.getClass().getFields()) {
      if (field.isAnnotationPresent(Inject.class)) {
        field.setAccessible(true);
        Object dependency = context.getBean(field.getType());
        field.set(t, dependency);
      }
    }
  }
}
