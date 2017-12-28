package vidias.virtualcloset.exception;

import org.springframework.http.HttpStatus;

public class RandomGeneratorException extends VirtualClosetException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8617028973050822616L;

    public RandomGeneratorException() {
    }

    public RandomGeneratorException(String message) {
        super(message);
    }

    public RandomGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
