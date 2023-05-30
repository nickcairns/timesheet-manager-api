package ie.mizenlandscapes.timesheets.service;

import ie.mizenlandscapes.timesheets.model.AuthResponse;
import ie.mizenlandscapes.timesheets.model.User;
import ie.mizenlandscapes.timesheets.security.JwtUtils;
import ie.mizenlandscapes.timesheets.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    private final JwtUtils jwtUtils;
    private final UserService userService;
    private UserDetails userDetails;

    public AuthenticationService(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    public User authenticate(String email, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userService.findUserByEmail(email);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("email", email);

        // Creating a UserDetails object based on the authenticated User object
        UserDetails userDetails = new UserPrincipal(user);

        String token = jwtUtils.createToken(claims, user);
        System.out.println("Token: " + token);
        return user;
    }
}
