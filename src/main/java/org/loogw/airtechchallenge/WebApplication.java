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
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.List;
import java.util.*;
import com.fasterxml.jackson.core.type.TypeReference;


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
	return "<b>List all airport: /airports <br> List SIDS in airport: /sids/airport/{icao} <br> List STARS in airport: /stars/airport/{icao} <BR> List top n SIDS waypoints for airport : /sids/airport/{icao}/topWaypoints/{n} ";
   }

   @RequestMapping(value="/airports")
   public String getAirports() {
	HttpHeaders headers = new HttpHeaders();
	headers.set("api-key",apiKey);
        HttpEntity <String> entity = new HttpEntity<String>(headers);
        return restTemplate.exchange("https://open-atms.airlab.aero/api/v1/airac/airports",HttpMethod.GET, entity, String.class).getBody();

   }

   @RequestMapping(value = "/sids/airport/{icao}")
   public String getSIDsForAirport(@PathVariable("icao") String id) throws Exception{
      
      ObjectMapper objectMapper = new ObjectMapper();
      HttpHeaders headers = new HttpHeaders();
      headers.set("api-key",apiKey);
      HttpEntity<String> entity = new HttpEntity<String>(headers);
      String response=restTemplate.exchange("https://open-atms.airlab.aero/api/v1/airac/sids/airport/"+id,HttpMethod.GET, entity, String.class).getBody();

      //JSON to List of Map
      //List contains a Map of 3 key : "name", "airport" and "waypoints"
      List<Map<String, Object>> sids = objectMapper.readValue(response,new TypeReference<List<Map<String, Object>>>(){}); 
      for (int i=0; i< sids.size(); i++) {
	     System.out.println("["+ String.valueOf(i+1) + "]");
	     for(Map.Entry<String,Object>it:sids.get(i).entrySet())
        	  System.out.println(it.getKey() +"=" + it.getValue() + " Type=" + it.getValue().getClass().getName());
	     System.out.println();
      }
      return response; 
   }

   @RequestMapping(value = "/stars/airport/{icao}")
   public String getSTARSForAirport(@PathVariable("icao") String id) throws Exception {
      ObjectMapper objectMapper = new ObjectMapper();
      HttpHeaders headers = new HttpHeaders();
      headers.set("api-key",apiKey);
      HttpEntity<String> entity = new HttpEntity<String>(headers);
      String response=restTemplate.exchange("https://open-atms.airlab.aero/api/v1/airac/stars/airport/"+id,HttpMethod.GET, entity, String.class).getBody();
      List<Map<String, Object>> sids = objectMapper.readValue(response,new TypeReference<List<Map<String, Object>>>(){});
      for (int i=0; i< sids.size(); i++) {
             System.out.println("["+ String.valueOf(i+1) + "]");
             for(Map.Entry<String,Object>it:sids.get(i).entrySet())
                  System.out.println(it.getKey() +"=" + it.getValue() + " Type=" + it.getValue().getClass().getName());
             System.out.println();
      }
      return response;

   }

   @RequestMapping(value = "/sids/airport/{icao}/topWaypoints/{n}")
   public String getTopNSIDWaypointsForAirport(@PathVariable("icao") String id, @PathVariable("n") int N) {
	return "Requesting top " +  String.valueOf(N) + " waypoints for icao " + id; 
   }
}
