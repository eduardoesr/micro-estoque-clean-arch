package br.com.microservice.estoque.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public final class EstoqueProdutoError {

    // Exception para cliente não encontrado (HTTP 404)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static final class EstoqueProdutoNotFoundException extends RuntimeException {
        public EstoqueProdutoNotFoundException(String message) {
            super(message);
        }
    }

    // Exception para argumentos inválidos (HTTP 400)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static final class EstoqueProdutoIllegalArgumentException extends IllegalArgumentException {
        public EstoqueProdutoIllegalArgumentException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.CONFLICT) // HTTP 409
    public static final class EstoqueProdutoAlreadyExistsException extends RuntimeException {
        public EstoqueProdutoAlreadyExistsException(String message) {
            super(message);
        }
    }
}