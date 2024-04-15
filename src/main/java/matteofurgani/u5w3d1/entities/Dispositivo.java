package matteofurgani.u5w3d1.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "dispositivi")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tipoDispositivo;
    private String stato;

    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;

    public Dispositivo(String tipoDispositivo, String stato) {
        this.tipoDispositivo = tipoDispositivo;
        this.stato = stato;
    }

}
