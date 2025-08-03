package br.com.isiflix.salutar.security;


import java.io.IOException;
import java.security.Key;
import java.util.Collections;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import br.com.isiflix.salutar.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;


import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

public class TokenUtil {

    /*Definição dos parâmetros de configuração do Token
     *Duração
     *Emissor
     *Chave
    */

    public static final long SEGUNDOS = 1000;
    public static final long MINUTOS = 60 * SEGUNDOS;
    public static final long HORAS = 60 * MINUTOS;
    public static final long DIAS = 24 * HORAS;
    public static final long EXPIRATION = 5 * DIAS;

    public static final String ISSUER = "*IsiFLIX*";

    public static final String SECRET_KEY = "0123456789012345678901234567890123";

    public static final String PREFIX = "Bearer";
    

    public static SalutarToken enconde(Usuario usuario) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        String jws = Jwts.builder().setSubject(usuario.getLogin())
                                   .setIssuer(ISSUER)
                                   .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                                   .signWith(key)
                                   .compact();
        return new SalutarToken(PREFIX +" "+ jws);
    }

    public static Authentication decode(HttpServletRequest request) throws ServletException, IOException{
		String token = request.getHeader("Authorization");
		token = token.replace(PREFIX, "");  // simplesmente retiro a palavar BEARER
		Jws<Claims> claims;
		claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build()
					.parseClaimsJws(token);
		
		String subject = claims.getBody().getSubject();
		String issuer  = claims.getBody().getIssuer();
		Date   exp     = claims.getBody().getExpiration();
		if (isValid(subject, issuer, exp)) {
			return new UsernamePasswordAuthenticationToken(subject, null, Collections.emptyList());
		}
		return null;
	}
    public static boolean isValid(String subject, String issuer, Date expiration) {
        return subject != null && subject.length() != 0 && issuer.equals(issuer) && expiration.after(new Date(System.currentTimeMillis()));
    }

}
