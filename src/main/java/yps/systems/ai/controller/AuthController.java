package yps.systems.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yps.systems.ai.object.SignIn;
import yps.systems.ai.object.SignUp;
import yps.systems.ai.service.AuthenticationService;

@RestController
@RequestMapping("/authService")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUp signUp) {
        return authenticationService.signUp(signUp);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody SignIn signIn) {
        return authenticationService.signIn(signIn);
    }

}
