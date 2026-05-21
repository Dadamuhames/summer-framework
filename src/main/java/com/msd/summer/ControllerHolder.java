package com.msd.summer;

import com.msd.summer.annotation.Component;
import com.msd.summer.constant.enums.HttpMethods;
import com.msd.summer.constant.enums.HttpStatus;
import com.msd.summer.dto.RequestHandler;
import com.msd.summer.exception.HttpException;
import com.msd.summer.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@Component
public class ControllerHolder {

  private final Map<String, Map<HttpMethods, RequestHandler>> requestHandlers =
      new ConcurrentHashMap<>();
  private final List<String> urlPatterns = new ArrayList<>();

  public void addRequestHandler(RequestHandler handler) {
    this.urlPatterns.add(handler.path());

    var path = handler.path();
    var method = handler.method();

    var handlers = getHandlersByPath(path);
    handlers.put(method, handler);
  }

  private Map<HttpMethods, RequestHandler> getHandlersByPath(String path) {
    return requestHandlers.computeIfAbsent(path, (k) -> new ConcurrentHashMap<>());
  }

  private String matchUrlToPattern(String path) {

    for (String pattern : urlPatterns) {
      String regex = "^" + pattern.replaceAll("\\{[^}]+\\}", "[^/]+") + "$";

      if (Pattern.matches(regex, path)) {
        return pattern;
      }
    }

    throw new ResourceNotFoundException(path);
  }

  public RequestHandler findHandlerByPathAndMethod(String path, HttpMethods method) {
    String pattern = matchUrlToPattern(path);

    System.out.printf("Pattern: %s\n", pattern);

    var handlers = getHandlersByPath(pattern);

    if (handlers.isEmpty()) {
      throw new ResourceNotFoundException(path);
    }

    var requestHandler = handlers.get(method);

    if (requestHandler == null) {
      throw new HttpException(path, HttpStatus.METHOD_NOT_ALLOWED);
    }

    return requestHandler;
  }
}
