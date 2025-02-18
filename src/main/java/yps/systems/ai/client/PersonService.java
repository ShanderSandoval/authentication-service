package yps.systems.ai.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PersonService {

    private final RestTemplate restTemplate;

    @Value("${env.url.person-command-service}")
    private String personCommandServiceUrl;

    @Value("${env.url.person-query-service}")
    private String personQueryServiceUrl;

    @Autowired
    public PersonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Person getPersonById(String personId) {
        String auxURL = personQueryServiceUrl + "/" + personId;
        ResponseEntity<Person> response = restTemplate.getForEntity(auxURL, Person.class);
        return response.getBody();
    }

    public String createPerson(Person person) {
        ResponseEntity<String> response = restTemplate.postForEntity(personCommandServiceUrl, person, String.class);
        return response.getBody();
    }

    public String updatePerson(String personElementId, Person person) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> entity = new HttpEntity<>(person, headers);
        String auxURL = personCommandServiceUrl + "/" + personElementId;
        ResponseEntity<String> response = restTemplate.exchange(auxURL, HttpMethod.PUT, entity, String.class);
        return response.getBody();
    }

    public String deletePerson(String personElementId) {
        String auxURL = personCommandServiceUrl + "/" + personElementId;
        restTemplate.delete(auxURL);
        return "Person deleted successfully.";
    }

}
