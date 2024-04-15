package matteofurgani.u5w3d1.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import matteofurgani.u5w3d1.entities.Dipendente;
import matteofurgani.u5w3d1.exceptions.BadRequestException;
import matteofurgani.u5w3d1.exceptions.NotFoundException;
import matteofurgani.u5w3d1.payloads.NewDipendenteDTO;
import matteofurgani.u5w3d1.repositories.DipendenteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DipendenteService {

    @Autowired
    private DipendenteDAO dipendenteDAO;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Dipendente save(NewDipendenteDTO body) throws IOException{
        dipendenteDAO.findByEmail(body.email()).ifPresent(
                dipendente -> {
                    throw new BadRequestException("L'email " + body.email() + " è già in uso!");
                });
        Dipendente dipendente = new Dipendente();
        dipendente.setNome(body.nome());
        dipendente.setCognome(body.cognome());
        dipendente.setEmail(body.email());
        dipendente.setPassword(body.password());
        dipendente.setUsername(body.username());
        dipendente.setImmagineProfilo("https://ui-avatars.com/api/?name=" + body.nome().charAt(0) + "+" + body.cognome().charAt(0));
        return dipendenteDAO.save(dipendente);
    }

    public Page<Dipendente> getDipendente(int page, int size, String sort){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return dipendenteDAO.findAll(pageable);
    }

    public Dipendente findById(int id){
        return dipendenteDAO.findById(id).orElseThrow(() -> new NotFoundException(id));

    }

    public Dipendente findByIdAndUpdate(int id, Dipendente body){

        Dipendente found = this.findById(id);
        found.setNome(body.getNome());
        found.setCognome(body.getCognome());
        found.setEmail(body.getEmail());
        found.setUsername(body.getUsername());
        found.setImmagineProfilo(body.getImmagineProfilo());
        return dipendenteDAO.save(found);
    }

    public void findByIDAndDelete(int id) {
        Dipendente found = this.findById(id);
        dipendenteDAO.delete(found);
    }

    public Dipendente uploadImage(int id, MultipartFile file) throws IOException{
        Dipendente found = this.findById(id);
        String imageURL = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setImmagineProfilo(imageURL);
        return dipendenteDAO.save(found);
    }

    public Dipendente findByEmail( String email){
        return dipendenteDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Il Dipendente cono email " + email + " non è stato trovato!"));
    }


}
