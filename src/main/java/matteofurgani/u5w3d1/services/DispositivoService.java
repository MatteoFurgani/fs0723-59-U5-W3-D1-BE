package matteofurgani.u5w3d1.services;


import matteofurgani.u5w3d1.entities.Dipendente;
import matteofurgani.u5w3d1.entities.Dispositivo;
import matteofurgani.u5w3d1.exceptions.BadRequestException;
import matteofurgani.u5w3d1.exceptions.NotFoundException;
import matteofurgani.u5w3d1.payloads.NewDispositivoDTO;
import matteofurgani.u5w3d1.repositories.DispositivoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;


@Service
public class DispositivoService {

    @Autowired
    private DispositivoDAO dispositivoDAO;

    @Autowired
    private DipendenteService dipendenteService;

    public Dispositivo save(NewDispositivoDTO body) throws IOException {
       Dispositivo dispostivo = new Dispositivo(body.stato(), body.tipoDispositivo());
        dispostivo.setStato(body.stato());
        dispostivo.setTipoDispositivo(body.tipoDispositivo());
        return dispositivoDAO.save(dispostivo);

    }

    public Page<Dispositivo> getDispositivo(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return dispositivoDAO.findAll(pageable);
    }

    public Dispositivo findById(int id) {
        return dispositivoDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Dispositivo findByIdAndUpdate(int id, Dispositivo body) {
        Dispositivo found = this.findById(id);
        found.setTipoDispositivo(body.getTipoDispositivo());
        found.setStato(body.getStato());
        return dispositivoDAO.save(found);
    }

    public void findByIdAndDelete(int id) {
        Dispositivo found = this.findById(id);
        dispositivoDAO.delete(found);
    }

    public Dispositivo findByIdAndAddDipendente(int dispositivoId, int dipendenteId) throws IOException {
        Dispositivo found = this.findById(dispositivoId);
        Dipendente dipendente = dipendenteService.findById(dipendenteId);
        if(Objects.equals(found.getStato(), "dismesso") || Objects.equals(found.getStato(), "in manutenzione")){
            throw new BadRequestException("Il dispositivo è in manutenzione o dismesso, quindi non può essere assegnato a un dipendente");
        }
        if(Objects.equals(found.getStato(), "disponibile")){
            found.setStato("assegnato");
            found.setDipendente(dipendente);
        }


        return dispositivoDAO.save(found);
    }


}
