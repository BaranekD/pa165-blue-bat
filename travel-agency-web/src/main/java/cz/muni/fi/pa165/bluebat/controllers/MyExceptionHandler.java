package cz.muni.fi.pa165.bluebat.controllers;
import cz.muni.fi.pa165.bluebat.exception.ErrorResource;
import cz.muni.fi.pa165.bluebat.exceptions.NotFoundException;
import cz.muni.fi.pa165.bluebat.exceptions.WrongDataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Converts exceptions to REST responses.
 *
 * @author Tomáš Hampl <469137@mail.muni.cz>
 */
@ControllerAdvice
public class MyExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    @ResponseBody
    protected ResponseEntity<ErrorResource> handleProblem(Exception e) {

        ErrorResource error = new ErrorResource(e.getClass().getSimpleName(), e.getMessage());

        HttpStatus httpStatus;
        if (e instanceof NotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (e instanceof IllegalArgumentException || e instanceof WrongDataAccessException) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        log.debug("handleProblem({}(\"{}\")) httpStatus={}", e.getClass().getName(), e.getMessage(),httpStatus);
        return new ResponseEntity<>(error, httpStatus);
    }
}
