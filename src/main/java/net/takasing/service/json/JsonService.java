package net.takasing.service.json;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author takasing
 */
@Service
public class JsonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonService.class);

    private ObjectMapper mapper = new ObjectMapper();

    public String serialize(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            LOGGER.error("", e);
            return null;
        }
    }
    public <T> T deserialize(String value, Class<T> clazz) {
        try {
            return mapper.readValue(value, clazz);
        } catch (Exception e) {
            LOGGER.error("", e);
            return null;
        }
    }
}
