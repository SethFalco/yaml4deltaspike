package fun.falco.yaml4deltaspike;

import java.io.InputStream;
import java.util.Map;
import java.util.function.Function;

import org.apache.deltaspike.core.impl.config.MapConfigSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

/**
 * Since the {@link MapConfigSource} insists that we call super, and Java
 * requires super be the first call of a method; we use this {@link Function} to
 * help as get around that limitation to perform more methods while calling
 * super.
 *
 * @author seth@falco.fun (Seth Falco)
 * @since 1.2.0
 */
public class YamlInputStreamFunction implements Function<InputStream, Map<String, Object>> {

    private static final Logger logger = LoggerFactory.getLogger(YamlInputStreamFunction.class);

    /**
     * @param inputStream Input stream to read the YAML configuration from.
     * @return Nested map representing all YAML properties.
     */
    @Override
    public Map<String, Object> apply(InputStream inputStream) {
        if (inputStream != null) {
            return new Yaml().load(inputStream);
        }

        logger.warn("Using {}, but the stream was null.", YamlConfigSource.class);
        return Map.of();
    }
}
