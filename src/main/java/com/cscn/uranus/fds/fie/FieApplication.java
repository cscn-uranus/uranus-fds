package com.cscn.uranus.fds.fie;

import com.cscn.uranus.fds.fie.event.FieContextRefreshedEventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableIntegration
public class FieApplication extends SpringBootServletInitializer {
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(FieApplication.class);
  }

  public static void main(String[] args) {
    ConfigurableApplicationContext ctx = SpringApplication.run(FieApplication.class, args);
    ctx.publishEvent(new FieContextRefreshedEventListener());
  }
}
