package matteofurgani.u5w3d1.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import matteofurgani.u5w3d1.entities.Dipendente;
import matteofurgani.u5w3d1.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {

    @Value("${jwt.secret}")
    private String secret;

    public String createToken(Dipendente dipendete){
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis())) //data di emissione
                .expiration(new Date(System.currentTimeMillis()
                        + 1000 * 60 * 60 * 24 * 7)) //data di scadenza
                .subject(String.valueOf(dipendete.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
    }

    public void verifyToken(String token){
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token);
        } catch (Exception ex){
            throw new UnauthorizedException("Token non valido");
        }
    }
}
