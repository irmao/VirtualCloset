package vidias.virtualcloset.exception;

import org.springframework.http.HttpStatus;

public class DuplicatedItemException extends VirtualClosetException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1577391212988485109L;

    public DuplicatedItemException() {
    }
    
    public DuplicatedItemException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
