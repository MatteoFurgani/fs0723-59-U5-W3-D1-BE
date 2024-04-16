package matteofurgani.u5w3d1.controllers;


import matteofurgani.u5w3d1.entities.Dipendente;
import matteofurgani.u5w3d1.exceptions.BadRequestException;
import matteofurgani.u5w3d1.payloads.NewDipendenteDTO;
import matteofurgani.u5w3d1.payloads.NewDipendenteRespDTO;
import matteofurgani.u5w3d1.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;

    // POST http://localhost:3001/dipendenti (+ req.body)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewDipendenteRespDTO saveDipendente(@RequestBody @Validated NewDipendenteDTO body, BindingResult validation) throws Exception{
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }

        Dipendente dipendente = dipendenteService.save(body);
        return new NewDipendenteRespDTO(dipendente.getId());
    }

    // GET http://localhost:3001/dipendenti
    @GetMapping
    public Page<Dipendente> getDipendenti(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sort) {
        return dipendenteService.getDipendente(page, size, sort);
    }

    // GET http://localhost:3001/dipendenti/{id}

    @GetMapping("/{dipendentiId}")
    public Dipendente findById(@PathVariable int dipendentiId) {
        return dipendenteService.findById(dipendentiId);
    }

    // PUT http://localhost:3001/dipendenti/{id} (+ req.body)

    @PutMapping("/{dipendentiId}")
    @PreAuthorize("hasAuthority('ADIM')")
    public Dipendente findAndUpdate(@PathVariable int dipendentiId, @RequestBody Dipendente body){
        return dipendenteService.findByIdAndUpdate(dipendentiId, body);
    }

    // DELETE http://localhost:3001/dipendenti/{id}

    @DeleteMapping("/{dipendenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> findByIdAndDelete(@PathVariable int dipendenteId) {

        dipendenteService.findByIDAndDelete(dipendenteId);
        return ResponseEntity.ok("Il dipendente è stato cancellato con successo");
    }

    // UPDATE IMAGGINE PROFILO

    @PostMapping("/{dipendentiId}/immagine-profilo")
    public Dipendente updateimmagineProfilo(@RequestParam("avatar")MultipartFile file, @PathVariable int dipendentiId) {
        try {
            return dipendenteService.uploadImage(dipendentiId, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
