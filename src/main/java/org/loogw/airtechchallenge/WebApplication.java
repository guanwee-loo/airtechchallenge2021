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
   public String getAirports() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
	HttpHeaders headers = new HttpHeaders();
	headers.set("api-key",apiKey);
        HttpEntity <String> entity = new HttpEntity<String>(headers);
        String response= restTemplate.exchange("https://open-atms.airlab.aero/api/v1/airac/airports",HttpMethod.GET, entity, String.class).getBody();
        List<Map<String, Object>> airports = objectMapper.readValue(response,new TypeReference<List<Map<String, Object>>>(){});
        for (int i=0; i< airports.size(); i++) {
             System.out.println("["+ String.valueOf(i+1) + "]");
             for(Map.Entry<String,Object>it:airports.get(i).entrySet())
                  System.out.println(it.getKey() + "=" + String.valueOf(it.getValue()));
             System.out.println();
        }
	return response;
   }

   @RequestMapping(value = "/sids/airport/{icao}")
   public String getSIDsForAirport(@PathVariable("icao") String id) {
      
      HttpHeaders headers = new HttpHeaders();
      headers.set("api-key",apiKey);
      HttpEntity<String> entity = new HttpEntity<String>(headers);
      String response=restTemplate.exchange("https://open-atms.airlab.aero/api/v1/airac/sids/airport/"+id,HttpMethod.GET, entity, String.class).getBody();
      return response; 
   }

   @RequestMapping(value = "/stars/airport/{icao}")
   public String getSTARSForAirport(@PathVariable("icao") String id){
      HttpHeaders headers = new HttpHeaders();
      headers.set("api-key",apiKey);
      HttpEntity<String> entity = new HttpEntity<String>(headers);
      String response=restTemplate.exchange("https://open-atms.airlab.aero/api/v1/airac/stars/airport/"+id,HttpMethod.GET, entity, String.class).getBody();
      return response;

   }

   @RequestMapping(value = "/sids/airport/{icao}/topWaypoints/{n}")
   public String getTopNSIDWaypointsForAirport(@PathVariable("icao") String id, @PathVariable("n") int N) {
	String resp= getSIDsForAirport(id);
	jsonToObject(resp);
	return "Requesting top " +  String.valueOf(N) + " SID waypoints for icao " + id; 
   }

   @RequestMapping(value = "/stars/airport/{icao}/topWaypoints/{n}")
   public String getTopNSTARWaypointsForAirport(@PathVariable("icao") String id, @PathVariable("n") int N) {
 	String resp = getSTARSForAirport(id);
	jsonToObject(resp);
        return "Requesting top " +  String.valueOf(N) + " STAR waypoints for icao " + id;
   }

   //"Brute force" way to count the number of waypoints in each SID for an airport
   // Extract waypoints 
   // Insert into HashMap using uid as key, initiate count to 1
   //
   private String generateResults (String icao,String req) {
	HashMap map=new HashMap();

	jsonToObject(req);	
	return new String();
   }

   //Perform JSON string to object deserialization
   //"Brute force" way to count the number of waypoints in each SID for an airport
   // Extract waypoints data and insert its name into a HashMap using UID as key 
   //    If key is available in map, increment count else add key and initiate to 1 
   private List<Map<String, Object>> jsonToObject(String jsonString) {
	ObjectMapper objectMapper = new ObjectMapper();
	List<Map<String, Object>> objs =null;
	try {
		objs = objectMapper.readValue(jsonString,new TypeReference<List<Map<String, Object>>>(){});
		HashMap hm = new HashMap();
      		for (int i=0; i< objs.size(); i++) {
             		System.out.println("["+ String.valueOf(i+1) + "]");
             		for(Map.Entry<String,Object>it:objs.get(i).entrySet())
				if (it.getKey() == "waypoints") {
			 		ArrayList a= (ArrayList) it.getValue();
					for (int j=0; j<a.size();j++) {
						//ArrayList of LinkedHashedMap
						//System.out.println(a.get(j).getClass().getName());
						//Keys: uid, name , lat, lng
						//Just extract the "name" 
						//check if key is already in hashmap, if so increment value of key, else insert new key
						LinkedHashMap hm1=(LinkedHashMap) a.get(j);
						String t=(String)hm1.get("name");
						Integer k= (Integer) hm.get(t);
						if (k==null) {
							//Set to 1 if SID is not found
							hm.put(t,1);
						} else {
							//Increase count for existing SID
							hm.put(t,k+1);
						}			
					}		
				}
      		}
		hm=sortByCount(hm);
		hm.forEach((key, value) -> System.out.println(key + ":" + value));	
	} catch (Exception e) {
		System.out.println(e);
	}	
        return objs;
   }

    private HashMap<String, Integer> sortByCount(HashMap<String, Integer> hm)
    {

        List<Map.Entry<String, Integer> > list =
               new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
         
  
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

   //Build a HashMap of waypoints with name as key and value as counts (shared by all SIDS in the airport) 
   private HashMap getResults() {
	return new HashMap();
   }  

}
