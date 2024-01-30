package ec.edu.unemi.unimart.services.exceptions;

public class ConflictException extends RuntimeException {
    private static final String DESCRIPTION = "Conflict Exception";

    public ConflictException(MessageException messageException) {
        super(DESCRIPTION + ". " + messageException);
    }
}
