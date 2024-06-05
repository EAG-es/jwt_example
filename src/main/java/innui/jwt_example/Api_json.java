package innui.jwt_example;

import innui.Bases;
import innui.modelos.configurations.ResourceBundles;
import innui.modelos.errors.Oks;
import innui.modelos.internacionalization.Tr;
import java.io.File;
import java.util.Map;
import java.util.ResourceBundle;

/**
 *
 * @author emilio
 */
public class Api_json extends Bases {
    // Properties file for translactions
    public static String k_in_route;
    static {
        String paquete_tex = Api_json.class.getPackage().getName();
        paquete_tex = paquete_tex.replace(".", File.separator);
        k_in_route = "in/" + paquete_tex + "/in";
    }
    public static String k_login = "login";
    public static String k_password = "password";
    public static String k_locale = "locale";

    /**
     * Processes ans Authorize request
     * @param in_map
     * @param out_map
     * @param ok
     * @param extras_array
     * @return
     * @throws Exception 
     */
    public boolean process(Map<String, Object> in_map, Map<String, Object> out_map, Oks ok, Object ... extras_array) throws Exception {
        if (ok.is == false) { return false; }
        ResourceBundle in;
        in = ResourceBundles.getBundle(k_in_route);
        try {
            String login;
            login = (String) in_map.get(k_login);
            if (login == null) {
                ok.setTxt(Tr.in(in, "Missing attribute: ") + k_login);
            }
            String password;
            password = (String) in_map.get(k_password);
            if (password == null) {
                ok.setTxt(ok.getTxt(), Tr.in(in, "Missing attribute: ") + k_password);
            }
            String locale;
            locale = (String) in_map.get(k_locale);
            if (locale == null) {
                ok.setTxt(ok.getTxt(), Tr.in(in, "Missing attribute: ") + k_locale);
            }
            if (ok.is == false) { return false; }
            out_map.putAll(in_map);
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.is; 
    }
}
