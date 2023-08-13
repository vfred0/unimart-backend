package ec.edu.unemi.unimart.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageException {

    USER_NOT_FOUND("Usuario no encontrado"),
    ARTICLE_NOT_FOUND("Artículo no encontrado"),
    PROPOSER_ARTICLE_NOT_FOUND("Artículo proponente no encontrado"),
    RECEIVER_ARTICLE_NOT_FOUND("Artículo receptor no encontrado"),
    RATING_NOT_FOUND("Calificación no encontrada"),
    EXCHANGE_NOT_FOUND("Intercambio no encontrado");

    private final String message;
}
