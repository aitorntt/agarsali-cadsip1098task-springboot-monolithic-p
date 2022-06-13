package com.dedalow.cad.monolithic;

import com.dedalow.cad.monolithic.commons.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({SwaggerConfig.class})
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class);
  }
}
