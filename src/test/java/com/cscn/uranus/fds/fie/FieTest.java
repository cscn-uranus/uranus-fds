package com.cscn.uranus.fds.fie;

import com.cscn.uranus.fds.fie.annotation.ExcludeFromTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
  excludeFilters =
      @ComponentScan.Filter(type = FilterType.ANNOTATION, value = ExcludeFromTest.class)
)
public class FieTest extends SpringBootServletInitializer {
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(FieTest.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(FieTest.class, args);
  }
}
