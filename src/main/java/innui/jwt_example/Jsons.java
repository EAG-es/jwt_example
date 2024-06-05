package innui.jwt_example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import innui.Bases;
import innui.modelos.configurations.ResourceBundles;
import innui.modelos.errors.Oks;
import innui.modelos.internacionalization.Tr;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

/**
 *
 * @author emilio
 */
public class Jsons extends Bases {
    // Properties file for translactions
    public static String k_in_route;
    static {
        String paquete_tex = Jsons.class.getPackage().getName();
        paquete_tex = paquete_tex.replace(".", File.separator);
        k_in_route = "in/" + paquete_tex + "/in";
    }
    public ObjectMapper objectMapper = null;

    public boolean _default_builder(Oks ok, Object ... extras_array) throws Exception {
        objectMapper = JsonMapper.builder()
                .build();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ (+S'ms')");
        objectMapper.setDateFormat(dateFormat);
        return ok.is;
    }
    /**
     * Converts JSON to a LinkedHashMap with String keys and values of type LinkedHashMap, ArrayList, String, or Basic types wrapped
     * @param json_tex
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public LinkedHashMap<String,Object> convert_to_map(String json_tex, Oks ok, Object ... extras_array) throws Exception {
        if (ok.is == false) { return null; }
        LinkedHashMap<String, Object> retorno = null;
//        ResourceBundle in;
//        in = ResourceBundles.getBundle(k_in_route);
        try {            
            if (objectMapper == null) {
                _default_builder(ok);
                if (ok.is == false) { return null; }
            }
            retorno = objectMapper.readValue(json_tex, new TypeReference<LinkedHashMap<String,Object>>() {});
        } catch (Exception e) {
            ok.setTxt(e);            
        }
        return retorno;
    }
    /**
     * Converts to String
     * @param json_map
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public String convert_to_string(HashMap<String,Object> json_map, Oks ok, Object ... extras_array) throws Exception {
        if (ok.is == false) { return null; }
        String retorno = null;
//        ResourceBundle in;
//        in = ResourceBundles.getBundle(k_in_route);
        try {            
            if (objectMapper == null) {
                _default_builder(ok);
                if (ok.is == false) { return null; }
            }
            retorno = objectMapper.writeValueAsString(json_map);
        } catch (Exception e) {
            ok.setTxt(e);            
        }
        return retorno;
    }
    /**
     * Converts to String
     * @param json_map
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public String convert_to_string(LinkedHashMap<String,Object> json_map, Oks ok, Object ... extras_array) throws Exception {
        if (ok.is == false) { return null; }
        String retorno = null;
//        ResourceBundle in;
//        in = ResourceBundles.getBundle(k_in_route);
        try {            
            if (objectMapper == null) {
                _default_builder(ok);
                if (ok.is == false) { return null; }
            }
            retorno = objectMapper.writeValueAsString(json_map);
        } catch (Exception e) {
            ok.setTxt(e);            
        }
        return retorno;
    }
    /**
     * Obtain a compound key 
     * @param json_to_map
     * @param compound_key
     * @param separator Normally: "\\."
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public Object access_key(LinkedHashMap<String,Object> json_to_map, String compound_key, String separator, Oks ok, Object ... extras_array) throws Exception {
        if (ok.is == false) { return null; }
        Object retorno = null;
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_route);
        try {
            String [] keys_array;
            Object object;
            keys_array = compound_key.split(separator);
            if (keys_array.length > 0) {
                object = json_to_map;
                for (var key: keys_array) {
                    if (object instanceof LinkedHashMap) {
                        object = ((LinkedHashMap) object).get(key);
                    } else if (object instanceof ArrayList) {
                        object = ((ArrayList) object).get(Integer.parseInt(key));
                    } else {
                        ok.setTxt(Tr.in(in, "Object found not accessible by key or index"));
                        object = null;
                        break;
                    }
                }
                retorno = object;
            } else {
                ok.setTxt(Tr.in(in, "No key or index found"));
            }
        } catch (Exception e) {
            ok.setTxt(e);            
        }
        return retorno;
    }
    
}
