package ie.mizenlandscapes.timesheets.model;

public class AuthResponse {

    private final String email;
    private final String token;
    private final String role;
    private final Long userId;

    public AuthResponse(String email, String token, String role, Long userId) {
        this.email = email;
        this.token = token;
        this.role = role;
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }


    public String getRole() {
        return role;
    }

    public Long getUserId() {
        return userId;
    }
}
