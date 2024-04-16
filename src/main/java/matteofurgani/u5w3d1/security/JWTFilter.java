package matteofurgani.u5w3d1.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import matteofurgani.u5w3d1.entities.Dipendente;
import matteofurgani.u5w3d1.exceptions.UnauthorizedException;
import matteofurgani.u5w3d1.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;



@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private DipendenteService dipendenteService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer")) throw new UnauthorizedException("Inserire il token");

        String accessToken = authHeader.substring(7);

        jwtTools.verifyToken(accessToken);

        String id =  jwtTools.extractIdFromToken(accessToken);
        Dipendente currentDipendente = this.dipendenteService.findById(Integer.parseInt(id));;

        Authentication authentication = new UsernamePasswordAuthenticationToken(currentDipendente, null, currentDipendente.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);


    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
