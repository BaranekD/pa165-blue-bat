package cz.muni.fi.pa165.bluebat.exception;

import lombok.Getter;

@Getter
public class ErrorResource {

    private final String code;
    private final String message;

    public ErrorResource(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResource{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
