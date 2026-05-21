package com.msd.summer.exception;

import static com.msd.summer.constant.enums.ErrorCode.CONTROLLER_RETURN_TYPE_INVALID;

public class ControllerResponseInvalidException extends RuntimeException {

  public ControllerResponseInvalidException() {
    super(CONTROLLER_RETURN_TYPE_INVALID.getMessage());
  }
}
