package cz.muni.fi.pa165.bluebat.utils;

import cz.muni.fi.pa165.bluebat.exceptions.NotFoundException;

public class Validator {

    public static void NotNull(Object object, String argumentName) {
        if (object == null) {
            throw new IllegalArgumentException(argumentName + " can not be null");
        }
    }

    public static void Found(Object object, String argumentName) {
        if (object == null) {
            throw new NotFoundException(argumentName + " has not been found");
        }
    }

    public static void Positive(Long number, String argumentName) {
        NotNull(number, argumentName);
        if (number <= 0) {
            throw new IllegalArgumentException(argumentName + " was '" + number + "', but must be greater than 0");
        }
    }
}
