package com.msd.summer.constant.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
  CONTROLLER_RETURN_TYPE_INVALID("Controller return type invalid");

  private final String message;

  ErrorCode(String message) {
    this.message = message;
  }
}
