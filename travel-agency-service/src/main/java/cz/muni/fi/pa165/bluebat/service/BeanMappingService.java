package cz.muni.fi.pa165.bluebat.service;

import com.github.dozermapper.core.Mapper;

import java.util.Collection;
import java.util.List;

public interface BeanMappingService {

    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    <T> T mapTo(Object u, Class<T> mapToClass);
    Mapper getMapper();
}
