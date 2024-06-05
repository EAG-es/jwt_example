package inser.spring.restful.jwt_example.component;

import innui.Bases;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

/**
 *
 * @author emilio
 */
@Component
public class ApplicationComponent extends Bases {
    // Properties file for translactions
    public static String k_in_route;
    static {
        String paquete_tex = ApplicationComponent.class.getPackage().getName();
        paquete_tex = paquete_tex.replace(".", File.separator);
        k_in_route = "in/" + paquete_tex + "/in";
    }
    @Autowired
    public ApplicationArguments applicationArguments;
    
}
