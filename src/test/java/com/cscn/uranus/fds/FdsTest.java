package com.cscn.uranus.fds;

import com.cscn.uranus.fds.annotation.ExcludeFromTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
  excludeFilters =
      @ComponentScan.Filter(type = FilterType.ANNOTATION, value = ExcludeFromTest.class)
)
public class FdsTest extends SpringBootServletInitializer {
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(FdsApplication.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(FdsApplication.class, args);
  }
}
