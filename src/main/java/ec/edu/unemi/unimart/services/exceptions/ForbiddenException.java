package ec.edu.unemi.unimart.services.exceptions;

public class ForbiddenException extends RuntimeException {
    private static final String DESCRIPTION = "Forbidden Exception";

    public ForbiddenException(MessageException messageException) {
        super(DESCRIPTION + ". " + messageException);
    }

}
