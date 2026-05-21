package com.msd.summer.service;

import com.msd.summer.annotation.Component;

@Component
public class LogServiceImpl implements LogService {

  @Override
  public void printLog() {
    System.out.println("Long printed");
  }
}
