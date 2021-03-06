package no.niths.application.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public final class ObjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 344699966872664622L;

    public ObjectNotFoundException() {
        super("Object not found");
    }

    public ObjectNotFoundException(
            final String message,
            final Throwable cause) {
        super(message, cause);
    }
    public ObjectNotFoundException(final String message) {
        super(message);
    }
    public ObjectNotFoundException(final Throwable cause) {
        super(cause);
    }

}