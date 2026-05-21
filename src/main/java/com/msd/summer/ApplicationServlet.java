package com.msd.summer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msd.summer.constant.enums.HttpMethods;
import com.msd.summer.dto.ResponseEntity;
import com.msd.summer.exception.ControllerResponseInvalidException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

public class ApplicationServlet extends HttpServlet {

  private final ApplicationContext context;
  private final ObjectMapper objectMapper;

  public ApplicationServlet(ApplicationContext context) {
    this.context = context;
    this.objectMapper = context.getBean(ObjectMapper.class);
  }

  @Override
  @SneakyThrows
  @SuppressWarnings("unchecked")
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    var path = req.getRequestURI();
    var httpMethod = HttpMethods.valueOf(req.getMethod().toUpperCase());

    var controllerHolder = context.getBean(ControllerHolder.class);
    var requestHandler = controllerHolder.findHandlerByPathAndMethod(path, httpMethod);

    var response = requestHandler.handler().invoke(requestHandler.controller());

    if (!(response instanceof ResponseEntity<?> responseEntity)) {
      throw new ControllerResponseInvalidException();
    }

    writeResponse(resp, responseEntity);
  }

  private void writeResponse(
      final HttpServletResponse resp,
      final ResponseEntity<?> responseEntity
  ) throws Exception {

    resp.setStatus(responseEntity.getStatus().getCode());

    for (var e : responseEntity.getHeaders().entrySet()) {
      resp.setHeader(e.getKey(), e.getValue());
    }

    var responseString = objectMapper.writeValueAsString(responseEntity.getBody());

    resp.getWriter().write(responseString);
  }
}
