package com.cscn.uranus.fds;

import com.cscn.uranus.fds.annotation.ExcludeFromTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@ExcludeFromTest
@SpringBootApplication
@EnableScheduling
public class FdsApplication extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(FdsApplication.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(FdsApplication.class, args);
  }
}
