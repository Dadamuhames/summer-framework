package com.msd.summer.dto;

import com.msd.summer.constant.enums.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEntityImpl<T> implements ResponseEntity<T> {

  HttpStatus status;
  Map<String, String> headers;
  T body;

  public ResponseEntityImpl(HttpStatus status, T body) {
    this.status = status;
    this.body = body;
    this.headers = new HashMap<>();
    this.headers.put("content-type", "application/json");
  }

  public static <T> ResponseEntityImpl<T> ok(T t) {
    return new ResponseEntityImpl<T>(HttpStatus.OK, t);
  }
}
