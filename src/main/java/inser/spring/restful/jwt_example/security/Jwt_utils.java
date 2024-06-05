package inser.spring.restful.jwt_example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author emilio
 */
@Component
public class Jwt_utils {
    public static final String k_mapping_jwt_user = "/jwt_user";
    public static final String k_mapping_public = "/public";   
    public static final String k_user_tex = "user";
    public static final String k_password_tex = "password";
    
    @Value(value = "${jwt.header}")
    public String k_header;
    @Value(value = "${jwt.prefix}")
    public String k_prefix;
    @Value(value = "${jwt.secretKey}")
    public String k_secretKey;
    @Value(value = "${jwt.role_user}")
    public String k_role_user;
    @Value(value = "${jwt.id}")
    public String k_id;
    @Value(value = "${jwt.claim.authorities}")
    public String k_claim_authorities;
    @Value(value = "${jwt.expiration_ms_to_add}")
    public String k_expiration_ms_to_add;

    public static String arrange_key(String key) {
        String string_256 = new String(new char[256]);
        string_256 = key 
                + string_256.substring(key.length());
        return string_256;
    }

    public static String get_jwt_token(String text, Jwt_utils jwt_util) {
        List<GrantedAuthority> grantedAuthorities_list = AuthorityUtils
                .commaSeparatedStringToAuthorityList(jwt_util.k_role_user);
        String secret_key_arranged;
        secret_key_arranged = arrange_key(jwt_util.k_secretKey);
        SecretKey secret = Keys.hmacShaKeyFor(secret_key_arranged.getBytes(StandardCharsets.UTF_8)); // Keys.password(string_256.toCharArray());
        Stream<GrantedAuthority> grantedAuthorities_stream = grantedAuthorities_list.stream();
        long current_time_ms = System.currentTimeMillis();
        long expiration_time_ms = current_time_ms + Long.parseLong(jwt_util.k_expiration_ms_to_add);
        String token = Jwts
          .builder()
          .id(jwt_util.k_id)
          .subject(text)
          .claim("authorities"
            , grantedAuthorities_stream.map(GrantedAuthority::getAuthority)
              .collect(Collectors.toList()))
          .issuedAt(new Date(current_time_ms))
          .expiration(new Date(expiration_time_ms))
          .signWith(secret).compact();
//        .secretKey.getBytes()).compact();

        return jwt_util.k_prefix + token;
    }
    
    public static Claims get_claims(String jwt_token, Jwt_utils jwt_util) {
        Claims retorno = null;
        jwt_token = jwt_token.replace(jwt_util.k_prefix, "");
//	retorno = Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
        String secret_key_arranged;
        secret_key_arranged = arrange_key(jwt_util.k_secretKey);
        SecretKey secret = Keys.hmacShaKeyFor(secret_key_arranged.getBytes(StandardCharsets.UTF_8));
        JwtParser jwtParser = Jwts.parser()
          .verifyWith(secret)
          .build();
        retorno = jwtParser.parseSignedClaims(jwt_token).getPayload();
        return retorno;
    }


}
