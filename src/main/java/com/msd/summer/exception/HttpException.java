package com.msd.summer.exception;

import com.msd.summer.constant.enums.HttpStatus;
import lombok.Getter;

@Getter
public class HttpException extends RuntimeException {
  private final String path;
  private final HttpStatus status;

  public HttpException(String path, HttpStatus status) {
    this.path = path;
    this.status = status;
  }
}
