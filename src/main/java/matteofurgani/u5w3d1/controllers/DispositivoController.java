package matteofurgani.u5w3d1.controllers;



import matteofurgani.u5w3d1.entities.Dispositivo;
import matteofurgani.u5w3d1.exceptions.BadRequestException;
import matteofurgani.u5w3d1.payloads.NewDipendenteRespDTO;
import matteofurgani.u5w3d1.payloads.NewDispositivoDTO;
import matteofurgani.u5w3d1.services.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("dispositivi")
public class DispositivoController {

    @Autowired
    private DispositivoService dispositivoService;

    // POST http://localhost:3001/dispositivi (+ req.body)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewDipendenteRespDTO save(@RequestBody @Validated NewDispositivoDTO body, BindingResult validation) throws Exception{
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        Dispositivo dispositivo = dispositivoService.save(body);
        return new NewDipendenteRespDTO(dispositivo.getId());
    }

    // GET http://localhost:3001/dispositivi

    @GetMapping
    public Page<Dispositivo> getDispositivi(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "id") String sort){
        return dispositivoService.getDispositivo(page, size, sort);
    }

    // GET http://localhost:3001/dispositivi/{id}

    @GetMapping("/{dispositivoId}")
    public Dispositivo findById(@PathVariable int dispositivoId) {
        return dispositivoService.findById(dispositivoId);
    }

    // PUT http://localhost:3001/dispositivi/{id}

    @PutMapping("/{dispositivoId}")
    public Dispositivo findByIdAndUpdate(@PathVariable int dispositivoId, @RequestBody Dispositivo body) {
        return dispositivoService.findByIdAndUpdate(dispositivoId, body);
    }

    // DELETE http://localhost:3001/dispositivi/{id}

    @DeleteMapping("/{dispositivoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable int dispositivoId) {
        dispositivoService.findByIdAndDelete(dispositivoId);
    }


    // GET http://localhost:3001/dispositivi/{id}/dipendenti


    @PostMapping("/{dispositivoId}/{dipendentiId}")
    public Dispositivo findByIdAndDipendenti(@PathVariable int dispositivoId, @PathVariable int dipendentiId) throws IOException {
        Dispositivo dispositivo = dispositivoService.findById(dispositivoId);
        return dispositivoService.findByIdAndAddDipendente(dispositivoId, dipendentiId);
    }


}
