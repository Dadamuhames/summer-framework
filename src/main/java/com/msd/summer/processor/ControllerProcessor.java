package com.msd.summer.processor;

import com.msd.summer.ApplicationContext;
import com.msd.summer.ControllerHolder;
import com.msd.summer.annotation.Controller;
import com.msd.summer.annotation.httpmethod.HttpMethod;
import com.msd.summer.constant.enums.HttpMethods;
import com.msd.summer.dto.RequestHandler;

public class ControllerProcessor implements BeanProcessor {
  @Override
  public void process(Object t, ApplicationContext context) {
    System.out.println("ControllerProcessor running");

    if (t.getClass().isAnnotationPresent(Controller.class)) {
      Controller annotation = t.getClass().getAnnotation(Controller.class);

      String basePath = annotation.value();

      var methods = t.getClass().getMethods();

      var controllerHolder = context.getBean(ControllerHolder.class);

      for (var method : methods) {
        if (method.isAnnotationPresent(HttpMethod.class)) {
          var methodAnnotation = method.getAnnotation(HttpMethod.class);

          HttpMethods httpMethod = methodAnnotation.method();
          String path = methodAnnotation.value();

          var requestPath = basePath.concat(path);
          var requestHandler = new RequestHandler(requestPath, httpMethod, t, method);

          controllerHolder.addRequestHandler(requestHandler);
        }
      }
    }
  }
}
