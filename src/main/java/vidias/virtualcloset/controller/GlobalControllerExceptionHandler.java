package vidias.virtualcloset.controller;

import static java.util.Arrays.asList;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import vidias.virtualcloset.exception.VirtualClosetException;
import vidias.virtualcloset.helper.Constants;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(DataIntegrityViolationException ex, WebRequest request) {
        return new ResponseEntity<Object>(asList(Constants.DUPPLICATED_MESSAGE), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ VirtualClosetException.class })
    public ResponseEntity<Object> handleVirtualClosetException(VirtualClosetException ex, WebRequest request) {
        return new ResponseEntity<Object>(asList(ex.getMessage()), ex.getHttpStatus());
    }
}
