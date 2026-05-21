package com.msd.summer.dto;

import com.msd.summer.constant.enums.HttpStatus;

import java.util.Map;

public interface ResponseEntity<T> {

  HttpStatus getStatus();

  Map<String, String> getHeaders();

  T getBody();
}
