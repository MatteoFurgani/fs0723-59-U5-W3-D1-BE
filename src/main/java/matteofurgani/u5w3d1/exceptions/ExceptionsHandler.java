package matteofurgani.u5w3d1.exceptions;


import matteofurgani.u5w3d1.payloads.errors.ErrorsRespDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsRespDTO handleBadRequestException(BadRequestException ex){
        List<String> errors = new ArrayList<>();
        if(ex.getErrorList() != null)
            errors = ex.getErrorList().stream().map(error -> error.getDefaultMessage()).toList();
        return new ErrorsRespDTO(ex.getMessage(), new Date(), errors);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsRespDTO handleNotFoundException(NotFoundException ex) {
       return new ErrorsRespDTO(ex.getMessage(), new Date(), null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsRespDTO handleGenericException(Exception ex){
        ex.printStackTrace();
        return new ErrorsRespDTO("Problema dovuto dal server. Risolveremo il prima possibile!",new Date(), null);
    }


}
