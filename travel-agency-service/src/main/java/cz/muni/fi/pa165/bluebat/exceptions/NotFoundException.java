package cz.muni.fi.pa165.bluebat.exceptions;

public class NotFoundException extends IllegalArgumentException {

    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public NotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
