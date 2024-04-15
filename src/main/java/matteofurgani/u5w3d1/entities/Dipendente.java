package matteofurgani.u5w3d1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "dipendenti")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Dipendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String immagineProfilo;

    @OneToMany(mappedBy = "dipendente")
    @JsonIgnore
    private List<Dispositivo> dispositivi;

    public Dipendente(String username, String nome, String cognome, String email, String password, String immagineProfilo) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.immagineProfilo = immagineProfilo;
    }

}
