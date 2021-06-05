package org.loogw.airtechchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WebApplication {
   @Value("${api.key}")
   private String apiKey;

   public static void main(String[] args) {
      SpringApplication.run(WebApplication.class, args);
   }

   @RequestMapping(value="/")
   public String apiKey() {
	return apiKey;
   }

}
