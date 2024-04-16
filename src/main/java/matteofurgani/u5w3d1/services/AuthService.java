package matteofurgani.u5w3d1.services;

import matteofurgani.u5w3d1.entities.Dipendente;
import matteofurgani.u5w3d1.exceptions.UnauthorizedException;
import matteofurgani.u5w3d1.payloads.DipendenteLoginDTO;
import matteofurgani.u5w3d1.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateDipendenteAndGenerateToken(DipendenteLoginDTO payload){

        Dipendente dipendente = this.dipendenteService.findByEmail(payload.email());

        if(bcrypt.matches(payload.password(), dipendente.getPassword())) {
            return jwtTools.createToken(dipendente);
        } else {
            throw new UnauthorizedException("Credenziali non valide");
        }
    }
}
