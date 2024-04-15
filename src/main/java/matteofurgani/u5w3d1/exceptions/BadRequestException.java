package matteofurgani.u5w3d1.exceptions;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class BadRequestException extends RuntimeException{

    private List<ObjectError> errorList;

    public BadRequestException(String messege) {
        super(messege);
    }

    public BadRequestException(List<ObjectError> errorList) {
        super("Errori nell'inserimento");
        this.errorList = errorList;
    }
}
