package ro.chris.schlechta.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Primary
@Configuration
public class ObjectMapperConfiguration extends ObjectMapper {

//	private static final long serialVersionUID = -7907677829023852078L;

	public ObjectMapperConfiguration() {
        setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

}