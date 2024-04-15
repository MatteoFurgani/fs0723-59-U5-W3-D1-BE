package matteofurgani.u5w3d1.repositories;



import matteofurgani.u5w3d1.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DipendenteDAO extends JpaRepository<Dipendente, Integer> {
    Optional<Dipendente> findByEmail(String email);
}


