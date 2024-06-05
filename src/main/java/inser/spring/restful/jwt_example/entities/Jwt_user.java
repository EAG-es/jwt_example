package inser.spring.restful.jwt_example.entities;

/**
 *
 * @author emilio
 */
public class Jwt_user {

	private String user;
	private String password;
	private String jwt_token;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return password;
	}

	public void setPwd(String pwd) {
		this.password = pwd;
	}

	public String getToken() {
		return jwt_token;
	}

	public void setToken(String token) {
		this.jwt_token = token;
	}
    
}
