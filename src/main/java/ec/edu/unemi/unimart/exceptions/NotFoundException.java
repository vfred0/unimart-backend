package ec.edu.unemi.unimart.exceptions;

public class NotFoundException extends RuntimeException {

    private static final String DESCRIPTION = "Bad Request Exception (400)";

    public static NotFoundException throwBecauseOf(MessageException messageException) {
        throw new NotFoundException(messageException);
    }

    private NotFoundException(MessageException messageException) {
        super(DESCRIPTION + ". " + messageException.getMessage());
    }

}