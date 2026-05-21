package com.msd.summer.processor;

public interface BeanProxyProcessor {

  Object wrapWithProxy(Object t, Class implClass);
}
