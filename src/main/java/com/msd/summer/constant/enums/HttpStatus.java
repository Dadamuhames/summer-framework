package com.msd.summer.constant.enums;

import lombok.Getter;

@Getter
public enum HttpStatus {
  OK(200, "Success"),
  NOT_FOUND(404, "Not found"),
  METHOD_NOT_ALLOWED(405, "Method Not Allowed");

  private final int code;
  private final String message;

  HttpStatus(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
