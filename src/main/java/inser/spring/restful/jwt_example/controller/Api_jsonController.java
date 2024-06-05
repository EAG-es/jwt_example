package inser.spring.restful.jwt_example.controller;

import innui.modelos.configurations.Initials;
import inser.spring.restful.jwt_example.component.SessionsComponent;
import innui.modelos.errors.Oks;
import innui.jwt_example.Api_json;
import innui.jwt_example.Jsons;
import inser.spring.restful.jwt_example.component.Api_jsonComponent;
import inser.spring.restful.jwt_example.component.JsonsComponent;
import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static inser.spring.restful.jwt_example.controller.K_names.k_last_map_tex;
import inser.spring.restful.jwt_example.security.Jwt_utils;
import java.util.LinkedHashMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 *
 * @author emilio
 */
// @PropertySource("classpath:application") // application.properties or application.yaml
@RestController
@RequestMapping("/api")
public class Api_jsonController {
    // Properties file for translactions
    public static String k_in_route;
    static {
        String paquete_tex = Api_jsonController.class.getPackage().getName();
        paquete_tex = paquete_tex.replace(".", File.separator);
        k_in_route = "in/" + paquete_tex + "/in";
    }
    @Value(value = "${spring.application.name}")
    public String application_name;    
    public SessionsComponent sessionComponent;
    public Api_json api_json;
    public Jsons json;
    public Initials initial;
    public Jwt_utils jwt_util;
    // Injection point
    public Api_jsonController(Api_jsonComponent api_jsonComponent
       , JsonsComponent jsonsComponent
       , SessionsComponent _sessionComponent
       , Initials application_initial
       , Jwt_utils _jwt_util) {
        this.sessionComponent = _sessionComponent;
        api_json = api_jsonComponent;
        json = jsonsComponent;
        initial = application_initial;
        jwt_util = _jwt_util;
    }

    @PostMapping(
      path = "/json"
      // , consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
    )
    public ResponseEntity<String> process_form(@RequestHeader MultiValueMap<String, String> headers
      , @RequestParam String json_tex) throws Exception { // @RequestParam MultiValueMap<String,String> params_map) 
        ResponseEntity<String> retorno = null;
        Oks ok = new Oks();
        do {
            try {
                LinkedHashMap<String, Object> in_map, jwt_uset_map, out_map;
                out_map = new LinkedHashMap<>();
                in_map = json.convert_to_map(json_tex, ok);
                String jwt_util_k_header = headers.getFirst(jwt_util.k_header);
                jwt_uset_map = json.convert_to_map(jwt_util_k_header, ok);
                if (ok.is == false) { break; }
                in_map.putAll(jwt_uset_map);
                api_json.process(in_map, out_map, ok);
                if (ok.is == false) { break; }
                sessionComponent.httpSession.setAttribute(k_last_map_tex, in_map);
                String json_out_tex = json.objectMapper.writeValueAsString(out_map);
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
    @GetMapping("/json/test")
    public ResponseEntity<String> process_form() throws Exception {
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
