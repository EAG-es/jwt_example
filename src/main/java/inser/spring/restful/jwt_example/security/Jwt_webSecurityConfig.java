package inser.spring.restful.jwt_example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static inser.spring.restful.jwt_example.security.Jwt_utils.k_matcher_jwt_user;
import static inser.spring.restful.jwt_example.security.Jwt_utils.k_matcher_public;

/**
 *
 * @author emilio
 */
@Configuration
@EnableWebSecurity
class Jwt_webSecurityConfig {
    @Autowired
    public Jwt_utils k_webSecurity_properties;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SecurityFilterChain retorno;
        retorno = http.csrf(csrf -> csrf.disable())
          .addFilterAfter(new Jwt_authorizationFilter(k_webSecurity_properties), UsernamePasswordAuthenticationFilter.class)
          .authorizeHttpRequests(request -> {
              request.requestMatchers(HttpMethod.POST
                , k_matcher_jwt_user)
                .permitAll()
                .requestMatchers(k_matcher_public)
                .permitAll()
                .anyRequest()
                .authenticated();              
          })
          .build();
        return retorno;
    }
}