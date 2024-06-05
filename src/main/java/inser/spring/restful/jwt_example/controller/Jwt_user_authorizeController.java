package inser.spring.restful.jwt_example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import inser.spring.restful.jwt_example.entities.Jwt_user;
import inser.spring.restful.jwt_example.security.Jwt_utils;
import static inser.spring.restful.jwt_example.security.Jwt_utils.get_jwt_token;
import static inser.spring.restful.jwt_example.security.Jwt_utils.k_password_tex;
import static inser.spring.restful.jwt_example.security.Jwt_utils.k_user_tex;
import org.springframework.beans.factory.annotation.Autowired;
import static inser.spring.restful.jwt_example.security.Jwt_utils.k_mapping_jwt_user;

@RestController
/**
 *
 * @author emilio
 */
public class Jwt_user_authorizeController {
    
    @Autowired
    public Jwt_utils jwt_util;
    
    @PostMapping(k_mapping_jwt_user)
    public Jwt_user user(@RequestParam(k_user_tex) String username, @RequestParam(k_password_tex) String pwd) {
        String token = get_jwt_token(username, jwt_util);
        Jwt_user user = new Jwt_user();
        user.setUser(username);
        user.setToken(token);
        return user;
    }

}
