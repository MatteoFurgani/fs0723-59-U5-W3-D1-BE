package matteofurgani.u5w3d1.payloads;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewDipendenteDTO(
        @NotEmpty(message = "L'username non può essere vuoto")
        @Size(min = 3, max = 20, message = "L'username deve avere minimo 3 caratteri ed un massimo di 20")
        String username,
        @NotEmpty(message = "Il nome è obbligatorio")
        @Size(min = 3, max = 15, message = "L'username deve avere minimo 3 caratteri ed un massimo di 15")
        String nome,
        @NotEmpty(message = "Il cognome è obbligatorio")
        @Size(min = 3, max = 15, message = "L'username deve avere minimo 3 caratteri ed un massimo di 15")
        String cognome,
        @NotEmpty(message = "L'email è obbligatoria")
        String email,
        @NotEmpty(message = "La password è obbligatoria")
        @Size(min = 4, max = 16, message= "La password deve essere minimo di 4 caratteri ed un massimom di 16")
        String password) {
}
