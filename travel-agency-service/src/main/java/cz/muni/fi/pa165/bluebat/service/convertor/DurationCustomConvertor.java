package cz.muni.fi.pa165.bluebat.service.convertor;

import com.github.dozermapper.core.CustomConverter;
import com.github.dozermapper.core.MappingException;

import java.time.Duration;

public class DurationCustomConvertor implements CustomConverter {
    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }

        if (sourceFieldValue instanceof Duration) {
            Duration source = (Duration) sourceFieldValue;
            //existingDestinationFieldValue = Duration.ZERO.plus(source);
            return Duration.ZERO.plus(source);
        } else {
            throw new MappingException("Converter TestCustomConverter "
                    + "used incorrectly. Arguments passed in were:"
                    + existingDestinationFieldValue
                    + " and "
                    + sourceFieldValue);
        }
    }
}
