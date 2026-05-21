package com.msd.summer.annotation.httpmethod;

import com.msd.summer.constant.enums.HttpMethods;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpMethod {

    HttpMethods method() default HttpMethods.GET;

    String value() default "";
}
