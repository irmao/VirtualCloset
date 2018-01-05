package vidias.virtualcloset.exception;

import org.springframework.http.HttpStatus;

public class InvalidEntityException extends VirtualClosetException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -843645434571093378L;

    public InvalidEntityException() {
    }
    
    public InvalidEntityException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
