package com.example.employees.config;

import com.example.employees.adapter.AssignmentToWorkTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

  @Bean
  public AssignmentToWorkTime adapter() {
    return new AssignmentToWorkTime();
  }
}
