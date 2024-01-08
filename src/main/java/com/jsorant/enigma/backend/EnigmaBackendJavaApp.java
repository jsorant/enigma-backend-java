package com.jsorant.enigma.backend;

import com.jsorant.enigma.backend.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ExcludeFromGeneratedCodeCoverage(reason = "Not testing logs")
public class EnigmaBackendJavaApp {

  private static final Logger log = LoggerFactory.getLogger(EnigmaBackendJavaApp.class);

  public static void main(String[] args) {
    Environment env = SpringApplication.run(EnigmaBackendJavaApp.class, args).getEnvironment();

    if (log.isInfoEnabled()) {
      log.info(ApplicationStartupTraces.of(env));
    }
  }
}
