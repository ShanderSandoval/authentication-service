package yps.systems.ai.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private final RestTemplate restTemplate;

    @Value("${env.url.user-command-service}")
    private String userCommandServiceUrl;

    @Value("${env.url.user-query-service}")
    private String userQueryServiceUrl;

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User getUserById(String elementId) {
        String auxURL = userQueryServiceUrl + "/" + elementId;
        ResponseEntity<User> response = restTemplate.getForEntity(auxURL, User.class);
        return response.getBody();
    }

    public String createUSer(User user) {
        ResponseEntity<String> response = restTemplate.postForEntity(userCommandServiceUrl, user, String.class);
        return response.getBody();
    }

    public String updateUser(String elementId, User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        String auxURL = userCommandServiceUrl + "/" + elementId;
        ResponseEntity<String> response = restTemplate.exchange(auxURL, HttpMethod.PUT, entity, String.class);
        return response.getBody();
    }

    public String deleteUser(String elementId) {
        String auxURL = userCommandServiceUrl + "/" + elementId;
        restTemplate.delete(auxURL);
        return "User deleted successfully.";
    }

}
