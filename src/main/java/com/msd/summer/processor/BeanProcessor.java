package com.msd.summer.processor;

import com.msd.summer.ApplicationContext;

public interface BeanProcessor {
  void process(Object t, ApplicationContext context);
}
