package com.msd.summer.exception;

import com.msd.summer.constant.enums.HttpStatus;

public class ResourceNotFoundException extends HttpException {
  public ResourceNotFoundException(String path) {
    super(path, HttpStatus.NOT_FOUND);
  }
}
