package cz.muni.fi.pa165.bluebat;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceTravelAgencyApplicationContext.class)
public class ServiceConfiguration {
    @Bean
    public Mapper dozer() {
        return DozerBeanMapperBuilder.create()
                .withMappingFiles("dozerBeanMapping.xml")
                .build();
    }
}
