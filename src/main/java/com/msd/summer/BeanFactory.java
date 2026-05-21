package com.msd.summer;

import com.msd.summer.processor.BeanProcessor;
import com.msd.summer.processor.BeanProxyProcessor;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;

public class BeanFactory {

  private final ApplicationContext context;
  private List<BeanProcessor> beanProcessors = new ArrayList<>();
  private List<BeanProxyProcessor> proxyProcessors = new ArrayList<>();

  @SneakyThrows
  public BeanFactory(ApplicationContext context) {
    this.context = context;

    for (Class<? extends BeanProcessor> bP :
        context.getConfig().getScanner().getSubTypesOf(BeanProcessor.class)) {
      beanProcessors.add(bP.getDeclaredConstructor().newInstance());
    }

    for (Class<? extends BeanProxyProcessor> bPP :
        context.getConfig().getScanner().getSubTypesOf(BeanProxyProcessor.class)) {
      proxyProcessors.add(bPP.getDeclaredConstructor().newInstance());
    }
  }

  public <T> T createBean(Class<T> implClass) {
    T t = create(implClass);

    runBeanProcessor(t);

    t = runBeanProxyProcessor(implClass, t);

    return t;
  }

  private <T> void runBeanProcessor(T t) {
    System.out.printf("Running Bean Processors on %s\n", t);
    beanProcessors.forEach(bp -> bp.process(t, context));
  }

  @SuppressWarnings(value = "unchecked")
  private <T> T runBeanProxyProcessor(Class<T> implClass, T t) {

    for (var bPP : proxyProcessors) {
      t = (T) bPP.wrapWithProxy(t, implClass);
    }

    return t;
  }

  @SneakyThrows
  private <T> T create(Class<T> implClass) {
    return implClass.getDeclaredConstructor().newInstance();
  }
}
