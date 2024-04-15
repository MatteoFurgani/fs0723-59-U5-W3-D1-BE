package matteofurgani.u5w3d1.repositories;


import matteofurgani.u5w3d1.entities.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DispositivoDAO extends JpaRepository<Dispositivo, Integer> {

    List<Dispositivo> findByTipoDispositivo(Dispositivo tipoDispositivo);
}
