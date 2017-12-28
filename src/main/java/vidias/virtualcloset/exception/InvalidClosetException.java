package vidias.virtualcloset.exception;

import org.springframework.http.HttpStatus;

public class InvalidClosetException extends VirtualClosetException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -843645434571093378L;

    public InvalidClosetException() {
    }
    
    public InvalidClosetException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
