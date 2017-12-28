package vidias.virtualcloset.exception;

import org.springframework.http.HttpStatus;

public abstract class VirtualClosetException extends RuntimeException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1937381533268112872L;

    public VirtualClosetException() {

    }

    public VirtualClosetException(String message) {
        super(message);
    }
    
    public VirtualClosetException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract HttpStatus getHttpStatus();
}
