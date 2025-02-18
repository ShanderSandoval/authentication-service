package yps.systems.ai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import yps.systems.ai.client.PersonService;
import yps.systems.ai.client.RoleService;
import yps.systems.ai.client.User;
import yps.systems.ai.client.UserService;
import yps.systems.ai.object.SignIn;
import yps.systems.ai.object.SignUp;

import java.util.HashMap;

@Service
public class AuthenticationService {

    private final PersonService personService;
    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationService(PersonService personService, UserService userService, RoleService roleService, JwtUtil jwtUtil) {
        this.personService = personService;
        this.userService = userService;
        this.roleService = roleService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public ResponseEntity<String> signUp(SignUp signUp) {
        String encodedPassword = passwordEncoder.encode(signUp.user().getPassword());
        User user = signUp.user();
        user.setPassword(encodedPassword);
        String personElementId = personService.createPerson(signUp.person());
        userService.createUSer(user);
        roleService.saveRelationRole("", personElementId);
        String token = jwtUtil.generateToken(new HashMap<String, Object>());
        return ResponseEntity.ok(token);
    }

    public ResponseEntity<String> signIn(SignIn signIn) {
        String storedPassword = "$2a$10$EXAMPLE_HASHED_PASSWORD";
        if (passwordEncoder.matches(signIn.password(), storedPassword)) {
            return ResponseEntity.ok("Inicio de sesión exitoso.");
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas.");
        }
    }
}
