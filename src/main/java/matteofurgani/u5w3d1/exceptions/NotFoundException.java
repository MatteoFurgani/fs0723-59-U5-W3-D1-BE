package matteofurgani.u5w3d1.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(int id) {
        super("L'utente con l'id n." + id + " non è stato trovato!");
    }
}
