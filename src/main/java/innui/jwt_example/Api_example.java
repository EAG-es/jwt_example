package innui.jwt_example;

import innui.Bases;
import static innui.jwt_example.Jwt_user.k_password_tex;
import static innui.jwt_example.Jwt_user.k_token_tex;
import static innui.jwt_example.Jwt_user.k_user_tex;
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
public class Api_example extends Bases {
    // Properties file for translactions
    public static String k_in_route;
    static {
        String paquete_tex = Api_example.class.getPackage().getName();
        paquete_tex = paquete_tex.replace(".", File.separator);
        k_in_route = "in/" + paquete_tex + "/in";
    }

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
            String user;
            user = (String) in_map.get(k_user_tex);
            if (user == null) {
                ok.setTxt(Tr.in(in, "Missing attribute: ") + k_user_tex);
            }
            String password;
            password = (String) in_map.get(k_password_tex);
            if (password == null) {
                ok.setTxt(ok.getTxt(), Tr.in(in, "Missing attribute: ") + k_password_tex);
            }
            String token;
            token = (String) in_map.get(k_token_tex);
            if (token == null) {
                ok.setTxt(ok.getTxt(), Tr.in(in, "Missing attribute: ") + k_token_tex);
            }
            if (ok.is == false) { return false; }
            out_map.putAll(in_map);
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return ok.is; 
    }
}
