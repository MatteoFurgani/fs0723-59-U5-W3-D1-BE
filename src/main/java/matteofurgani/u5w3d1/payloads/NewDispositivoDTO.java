package matteofurgani.u5w3d1.payloads;

import jakarta.validation.constraints.NotEmpty;

public record NewDispositivoDTO(
        @NotEmpty(message = "Il tipo di dispostivo è obbligatorio")
        String tipoDispositivo,
        @NotEmpty(message = "Lo stato è obbligatorio")
        String stato) {

}
