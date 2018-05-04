package com.cscn.uranus.fds.asx;

import com.cscn.uranus.fds.asx.event.AsxContextRefreshedEventListener;
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
public class AsxApplication extends SpringBootServletInitializer {
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(AsxApplication.class);
  }

  public static void main(String[] args) {
    ConfigurableApplicationContext ctx = SpringApplication.run(AsxApplication.class, args);
    ctx.publishEvent(new AsxContextRefreshedEventListener());
    ctx.close();
  }
}
