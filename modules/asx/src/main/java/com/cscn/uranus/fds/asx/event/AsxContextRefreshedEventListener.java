package com.cscn.uranus.fds.asx.event;

import com.cscn.uranus.fds.asx.AsxInitializer;
import io.micrometer.core.lang.NonNullApi;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@NonNullApi
public class AsxContextRefreshedEventListener implements
    ApplicationListener<ContextRefreshedEvent> {

  private final Logger _logger = LoggerFactory.getLogger(AsxContextRefreshedEventListener.class);

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    ApplicationContext ctx = event.getApplicationContext();
    AsxInitializer initializer = ctx.getBean(AsxInitializer.class);
    initializer.doInitialize();
  }
}
