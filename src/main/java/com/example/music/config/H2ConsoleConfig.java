package com.example.music.config;

import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@Configuration
public class H2ConsoleConfig {

  private Server webServer;

  @EventListener(ContextRefreshedEvent.class)
  public void start() throws SQLException {
    log.info("started h2 console at port {}.", 8090);
    webServer = Server.createWebServer("-webPort", "8090").start();
  }

  @EventListener(ContextClosedEvent.class)
  public void stop() {
    log.info("stopped h2 console at port {}.", "8090");
    webServer.stop();
  }
}
