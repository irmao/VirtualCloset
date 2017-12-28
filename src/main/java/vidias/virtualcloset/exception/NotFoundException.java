package vidias.virtualcloset.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends VirtualClosetException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6079426367036621343L;

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
