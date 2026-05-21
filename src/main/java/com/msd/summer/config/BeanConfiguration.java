package com.msd.summer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msd.summer.annotation.Bean;
import com.msd.summer.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
