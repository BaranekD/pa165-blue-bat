package cz.muni.fi.pa165.bluebat.exceptions;

import org.springframework.dao.DataAccessException;

public class WrongDataAccessException extends DataAccessException {

    public WrongDataAccessException(String errorMessage) {
        super(errorMessage);
    }

    public WrongDataAccessException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
