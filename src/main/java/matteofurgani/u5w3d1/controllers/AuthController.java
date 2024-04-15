package matteofurgani.u5w3d1.controllers;

import matteofurgani.u5w3d1.exceptions.BadRequestException;
import matteofurgani.u5w3d1.payloads.DipendenteLoginDTO;
import matteofurgani.u5w3d1.payloads.DipendenteLoginRespDTO;
import matteofurgani.u5w3d1.payloads.NewDipendenteDTO;
import matteofurgani.u5w3d1.payloads.NewDipendenteRespDTO;
import matteofurgani.u5w3d1.services.AuthService;
import matteofurgani.u5w3d1.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("/login")
    public DipendenteLoginRespDTO login(@RequestBody DipendenteLoginDTO payload){

        return new DipendenteLoginRespDTO(this.authService.authenticateDipendenteAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewDipendenteRespDTO saveUser(@RequestBody @Validated NewDipendenteDTO body, BindingResult validation) throws IOException {

        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return new NewDipendenteRespDTO(this.dipendenteService.save(body).getId());
    }

}
