package org.loogw.airtechchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;


@SpringBootApplication
@RestController
public class WebApplication {
   @Value("${api.key}")
   private String apiKey;

   @Autowired
   private RestTemplate restTemplate;
   @Bean
   public RestTemplate restTemplate() {
     return new RestTemplate();
   }


   public static void main(String[] args) {
      SpringApplication.run(WebApplication.class, args);
   }

   @RequestMapping(value="/")
   public String home() {
	return "List all airports /airports <br> List SIDS in airport /sids/airport/{icao} <br> List STARS in airport /stars/airport/{icao}";
   }

   @RequestMapping(value="/airports")
   public String getAirports() {
	HttpHeaders headers = new HttpHeaders();
	headers.set("api-key",apiKey);
        HttpEntity <String> entity = new HttpEntity<String>(headers);
        return restTemplate.exchange("https://open-atms.airlab.aero/api/v1/airac/airports",HttpMethod.GET, entity, String.class).getBody();

   }

   @RequestMapping(value = "/sids/airport/{icao}")
   public String getSIDsForAirport(@PathVariable("icao") String id) {
      HttpHeaders headers = new HttpHeaders();
      headers.set("api-key",apiKey);
      HttpEntity<String> entity = new HttpEntity<String>(headers);
      return restTemplate.exchange("https://open-atms.airlab.aero/api/v1/airac/sids/airport/"+id,HttpMethod.GET, entity, String.class).getBody();
   }

   @RequestMapping(value = "/stars/airport/{icao}")
   public String getSTARSForAirport(@PathVariable("icao") String id) {
      HttpHeaders headers = new HttpHeaders();
      headers.set("api-key",apiKey);
      HttpEntity<String> entity = new HttpEntity<String>(headers);
      return restTemplate.exchange("https://open-atms.airlab.aero/api/v1/airac/stars/airport/"+id,HttpMethod.GET, entity, String.class).getBody();
   }

}
