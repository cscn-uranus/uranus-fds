package com.cscn.uranus.fds.fie.event;

import com.cscn.uranus.fds.fie.FieInitializer;
import io.micrometer.core.lang.NonNullApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@NonNullApi
public class FieContextRefreshedEventListener implements
    ApplicationListener<ContextRefreshedEvent> {

  private final Logger _logger = LoggerFactory.getLogger(FieContextRefreshedEventListener.class);

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    ApplicationContext ctx = event.getApplicationContext();
    FieInitializer initializer = ctx.getBean(FieInitializer.class);
    initializer.doInitialize();
  }
}
