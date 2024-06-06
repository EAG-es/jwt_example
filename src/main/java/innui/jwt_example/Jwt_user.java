package innui.jwt_example;

/**
 *
 * @author emilio
 */
public class Jwt_user {
    public static final String k_user_tex = "user";
    public static final String k_password_tex = "password";
    public static final String k_token_tex = "token";

    public String user;
    public String password;
    public String jwt_token;

    public String getUser() {
            return user;
    }

    public void setUser(String user) {
            this.user = user;
    }

    public String getPassword() {
            return password;
    }

    public void setPassword(String password) {
            this.password = password;
    }

    public String getToken() {
            return jwt_token;
    }

    public void setToken(String token) {
            this.jwt_token = token;
    }
    
}
