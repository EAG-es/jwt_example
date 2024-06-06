package inser.spring.restful.jwt_example.controller;

import innui.modelos.configurations.Initials;
import inser.spring.restful.jwt_example.component.SessionsComponent;
import innui.modelos.errors.Oks;
import innui.jwt_example.Api_example;
import innui.jwt_example.Jsons;
import innui.jwt_example.Jwt_user;
import innui.modelos.configurations.ResourceBundles;
import innui.modelos.internacionalization.Tr;
import inser.spring.restful.jwt_example.component.Api_exampleComponent;
import inser.spring.restful.jwt_example.component.JsonsComponent;
import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static inser.spring.restful.jwt_example.controller.K_names.k_last_map_tex;
import inser.spring.restful.jwt_example.security.Jwt_utils;
import static inser.spring.restful.jwt_example.security.Jwt_utils.get_claims_map;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 *
 * @author emilio
 */
// @PropertySource("classpath:application") // application.properties or application.yaml
@RestController
@RequestMapping("/api")
public class Api_exampleController {
    // Properties file for translactions
    public static String k_in_route;
    static {
        String paquete_tex = Api_exampleController.class.getPackage().getName();
        paquete_tex = paquete_tex.replace(".", File.separator);
        k_in_route = "in/" + paquete_tex + "/in";
    }
    @Value(value = "${spring.application.name}")
    public String application_name;    
    public SessionsComponent sessionComponent;
    public Api_example api_json;
    public JsonsComponent jsonComponent;
    public Initials initial;
    public Jwt_utils jwt_util;
    
    // Injection point laike with @Autowired
    public Api_exampleController(Api_exampleComponent api_jsonComponent
       , JsonsComponent _jsonsComponent
       , SessionsComponent _sessionComponent
       , Initials application_initial
       , Jwt_utils _jwt_util) {
        this.sessionComponent = _sessionComponent;
        api_json = api_jsonComponent;
        jsonComponent = _jsonsComponent;
        initial = application_initial;
        jwt_util = _jwt_util;
    }
    /**
     * Example of POST call:
     * POST /api/jwt_example HTTP/1.1
     * Accept: application/json
     * Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJKV1RfaWQiLCJzdWIiOiJ7XCJ1c2VyXCI6XCJFbWlsaW9cIixcInBhc3N3b3JkXCI6XCJjb250cmFzZcOxYVwiLFwidG9rZW5cIjpudWxsfSIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE3MTc2NjkwNjcsImV4cCI6MTcxNzc1NTQ2N30.xi41tvLqudaBQ-bLmj0FZn5aPTGRdajcFF0--xyWFfmgBU5IneAaazyVRi3WOn0KfWnLe_D15D_8fhJFAQI7Rg
     * Content-Length: 0
     * Content-Type: application/json
     * Host: localhost:8080
     * @param headers
     * @return
     * @throws Exception 
     */
    @PostMapping(
      path = "/jwt_example"
      // , consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
    )
    public ResponseEntity<String> process_form(@RequestHeader MultiValueMap<String, String> headers) throws Exception { // @RequestParam MultiValueMap<String,String> params_map) 
        ResponseEntity<String> retorno = null;
        Oks ok = new Oks();
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_route);
        do {
            try {
                LinkedHashMap<String, Object> in_map, jwt_uset_map, out_map;
                out_map = new LinkedHashMap<>();
                in_map = new LinkedHashMap<>();
//                in_map = jsonComponent.convert_to_map(json_tex, ok);
                String jwt_util_k_header = headers.getFirst(jwt_util.k_header);
                if (ok.no_nul(k_in_route, Tr.in(in, "Not found header: ") + jwt_util.k_header) == false) { break; }
                jwt_uset_map = get_claims_map(jwt_util_k_header, jwt_util, ok);
                if (ok.is == false) { break; }
                in_map.putAll(jwt_uset_map);
                in_map.put(Jwt_user.k_token_tex, jwt_util_k_header);
                api_json.process(in_map, out_map, ok);
                if (ok.is == false) { break; }
                sessionComponent.httpSession.setAttribute(k_last_map_tex, in_map);
                String json_out_tex = jsonComponent.objectMapper.writeValueAsString(out_map);
                retorno = new ResponseEntity<>(json_out_tex, HttpStatus.OK);
            } catch (Exception e) {
                ok.setTxt(e);
            }
        } while (false);
        if (ok.is == false) {
            retorno = new ResponseEntity<>(ok.getTxt(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return retorno; 
    }
    // Just for testing
    /**
     * Example of a call:
     * GET /api/jwt_example/test HTTP/1.1
     * Accept: * /*
     * Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJKV1RfaWQiLCJzdWIiOiJ7XCJ1c2VyXCI6XCJFbWlsaW9cIixcInBhc3N3b3JkXCI6XCJjb250cmFzZcOxYVwiLFwidG9rZW5cIjpudWxsfSIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE3MTc2NjkwNjcsImV4cCI6MTcxNzc1NTQ2N30.xi41tvLqudaBQ-bLmj0FZn5aPTGRdajcFF0--xyWFfmgBU5IneAaazyVRi3WOn0KfWnLe_D15D_8fhJFAQI7Rg
     * Host: localhost:8080
     * @return 
     * @throws Exception 
     */
    @GetMapping("/jwt_example/test")
    public ResponseEntity<String> test() throws Exception {
        ResponseEntity<String> retorno = null;
        Oks ok = new Oks();
        do {
            try {
                String tex = application_name;
                retorno = new ResponseEntity<>(tex, HttpStatus.OK);
            } catch (Exception e) {
                ok.setTxt(e);
            }
        } while (false);
        if (ok.is == false) {
            retorno = new ResponseEntity<>(ok.getTxt(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return retorno; 
    }
}
