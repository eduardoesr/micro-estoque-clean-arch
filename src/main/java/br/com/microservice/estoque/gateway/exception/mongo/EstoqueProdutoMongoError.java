package br.com.microservice.estoque.gateway.exception.mongo;

import br.com.microservice.estoque.gateway.exception.GatewayException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public final class EstoqueProdutoMongoError {

    private final static String PREFIX_MONGO = "mongo_db_gateway:";

    // Erro quando o estoque de produto não é encontrado (HTTP 404)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static final class EstoqueProdutoNotFoundException extends RuntimeException implements GatewayException {
        public EstoqueProdutoNotFoundException(String message) {
            super(PREFIX_MONGO + message);
        }
    }

    // Erro quando um argumento inválido é passado (HTTP 400)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static final class EstoqueProdutoInvalidArgumentException extends IllegalArgumentException implements GatewayException {
        public EstoqueProdutoInvalidArgumentException(String message) {
            super(PREFIX_MONGO + message);
        }
    }

    // Erro genérico de persistência (ex: falha no MongoDB) (HTTP 500)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static final class EstoqueProdutoPersistenceException extends RuntimeException implements GatewayException {
        public EstoqueProdutoPersistenceException(String message, Throwable cause) {
            super(PREFIX_MONGO + message, cause);
        }
    }

    // Erro quando há conflito (ex: CPF duplicado) (HTTP 409)
    @ResponseStatus(HttpStatus.CONFLICT)
    public static final class EstoqueProdutoConflictException extends RuntimeException implements GatewayException {
        public EstoqueProdutoConflictException(String message) {
            super(PREFIX_MONGO + message);
        }
    }
}