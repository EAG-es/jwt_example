package inser.spring.restful.jwt_example.component;

import innui.Bases;
import innui.modelos.errors.Oks;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.lang.reflect.Constructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author emilio
 */
@Component
public class SessionsComponent extends Bases {
    // Properties file for translactions
    public static String k_in_route;
    static {
        String paquete_tex = SessionsComponent.class.getPackage().getName();
        paquete_tex = paquete_tex.replace(".", File.separator);
        k_in_route = "in/" + paquete_tex + "/in";
    }
    @Autowired
    public HttpSession httpSession;
    
    public <T extends Object> T get_or_create_named_object(String name, Class _class, Oks ok, Object ... extra_array) throws Exception {
        if (ok.is == false) { return null; }
//        ResourceBundle in = null;
//        in = ResourceBundles.getBundle(k_in_route);
        T retorno = null;
        try {
            retorno = (T) httpSession.getAttribute(name);
            if (retorno == null) {
                Constructor constructor = _class.getConstructor();
                retorno = (T) constructor.newInstance();
                httpSession.setAttribute(name, retorno);
            }
        } catch (Exception e) {
            ok.setTxt(e);
        }
        return retorno;
    }
}
