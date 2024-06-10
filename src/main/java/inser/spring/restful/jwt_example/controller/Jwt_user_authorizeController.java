package inser.spring.restful.jwt_example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import innui.jwt_example.Jwt_user;
import static innui.jwt_example.Jwt_user.k_password_tex;
import static innui.jwt_example.Jwt_user.k_user_tex;
import innui.modelos.errors.Oks;
import inser.spring.restful.jwt_example.component.JsonsComponent;
import inser.spring.restful.jwt_example.security.Jwt_utils;
import org.springframework.beans.factory.annotation.Autowired;
import static inser.spring.restful.jwt_example.security.Jwt_utils.k_mapping_jwt_user;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
/**
 *
 * @author emilio
 */
public class Jwt_user_authorizeController {
    
    @Autowired
    public Jwt_utils jwt_util;
    @Autowired
    public JsonsComponent jsonsComponent;
    
    /**
     * Example of a call:
     * POST /jwt_user?user=Emilio&password=password_sent HTTP/1.1
     * Accept: application/json
     * Content-Length: 0
     * Content-Type: application/json
     * Host: localhost:8080
     * @param username
     * @param password
     * @return
     * @throws Exception 
     */
    @PostMapping(k_mapping_jwt_user)
    public ResponseEntity user(@RequestParam(k_user_tex) String username, @RequestParam(k_password_tex) String password) throws Exception {
        ResponseEntity<Jwt_user> retorno = null;
        Oks ok = new Oks();
//        ResourceBundle in;
//        in = ResourceBundles.getBundle(k_in_route);
        do {
            try {
                Jwt_user jw_user = new Jwt_user();
                jw_user.setUser(username);
                jw_user.setPassword(password);
                ObjectMapper objectMapper = jsonsComponent.getObjectMapper(ok);
                if (ok.is == false) { break; }
                String jw_user_tex = objectMapper.writeValueAsString(jw_user);
                String token = jwt_util.get_jwt_token(jw_user_tex);
                jw_user.setToken(token);
                retorno = new ResponseEntity<>(jw_user, HttpStatus.OK);
            } catch (Exception e) {
                ok.setTxt(e);
            }
        } while (false);
        if (ok.is == false) {
            return new ResponseEntity<>(ok.getTxt(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return retorno; 
    }

}
