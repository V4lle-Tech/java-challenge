// Excepciones custom para lógica de negocio

// Para recursos no encontrados
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, Long id) {
        super(String.format("%s with id %d not found", resource, id));
    }
}

// Para recursos duplicados
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String field, String value) {
        super(String.format("%s '%s' already exists", field, value));
    }
}

// Para operaciones inválidas
public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(String message) {
        super(message);
    }

    public InvalidOperationException(String operation, String reason) {
        super(String.format("Cannot %s: %s", operation, reason));
    }
}

// Para validaciones de negocio
public class BusinessValidationException extends RuntimeException {
    public BusinessValidationException(String message) {
        super(message);
    }

    public BusinessValidationException(String field, String violation) {
        super(String.format("Validation failed for %s: %s", field, violation));
    }
}

// Para errores externos (APIs, servicios)
public class ExternalServiceException extends RuntimeException {
    private final int statusCode;

    public ExternalServiceException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ExternalServiceException(String service, String error, int statusCode) {
        super(String.format("%s service error: %s", service, error));
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}