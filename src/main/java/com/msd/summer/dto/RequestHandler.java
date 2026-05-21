package com.msd.summer.dto;

import com.msd.summer.constant.enums.HttpMethods;

import java.lang.reflect.Method;

public record RequestHandler(String path, HttpMethods method, Object controller, Method handler) {}
