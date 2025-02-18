package yps.systems.ai.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RoleService {

    private final RestTemplate restTemplate;

    @Value("${env.url.role-command-service}")
    private String roleCommandServiceUrl;

    @Value("${env.url.role-query-service}")
    private String roleQueryServiceUrl;

    @Autowired
    public RoleService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Role getRoleById(String elementId) {
        String auxURL = roleQueryServiceUrl + "/" + elementId;
        ResponseEntity<Role> response = restTemplate.getForEntity(auxURL, Role.class);
        return response.getBody();
    }

    public String saveRelationRole(String roleElementId, String personElementId) {
        String auxURL = roleCommandServiceUrl + "/" + roleElementId + "/" + personElementId;
        ResponseEntity<String> response = restTemplate.postForEntity(auxURL, null, String.class);
        return response.getBody();
    }

}
