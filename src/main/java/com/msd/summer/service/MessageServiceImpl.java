package com.msd.summer.service;

import com.msd.summer.annotation.Component;
import com.msd.summer.annotation.Inject;

@Component
public class MessageServiceImpl implements MessageService {

  @Inject public LogService logService;

  @Override
  public void printMessage() {
    System.out.println("Hello world!");

    logService.printLog();
  }
}
