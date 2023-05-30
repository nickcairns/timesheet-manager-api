package ie.mizenlandscapes.timesheets.controller;

import ie.mizenlandscapes.timesheets.model.AuthResponse;
import ie.mizenlandscapes.timesheets.model.AuthenticationRequest;
import ie.mizenlandscapes.timesheets.model.User;
import ie.mizenlandscapes.timesheets.model.UserRole;
import ie.mizenlandscapes.timesheets.repository.UserRepository;
import ie.mizenlandscapes.timesheets.security.JwtUtils;
import ie.mizenlandscapes.timesheets.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @CrossOrigin
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        System.out.println("Received email: " + request.getEmail());
        User user = authenticationService.authenticate(request.getEmail(), request.getPassword());
        String token = jwtUtils.generateToken(user);
        UserRole userRole = user.getRole();
        String role = userRole.name();

        AuthResponse authResponse = new AuthResponse(user.getEmail(), token, role, user.getId());
        return ResponseEntity.ok(authResponse);
        //return ResponseEntity.ok(authenticationService.authenticate(request.getEmail(), request.getPassword()).getToken());
    }

    /*authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            return ResponseEntity.ok(jwtUtils.generateToken(user));
        }
        return ResponseEntity.status(400).body("An error has occurred");*/

}
