package org.elypia.yaml4deltaspike;

import org.apache.deltaspike.core.impl.config.MapConfigSource;
import org.slf4j.*;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;
import java.util.function.Function;

/**
 * Since the {@link MapConfigSource} insists that we call super, and Java
 * requires super be the first call of a method; we use this {@link Function}
 * to help as get around that limitation to perform more functions
 * while calling super.
 *
 * @author seth@elypia.org (Seth Falco)
 * @since 1.2.0
 */
public class YamlInputStreamFunction implements Function<InputStream, Map<String, String>> {

    /** Logging with slf4j. */
    private static final Logger logger = LoggerFactory.getLogger(YamlInputStreamFunction.class);

    /**
     * @param inputStream The input stream to read the YAML configuration from.
     * @return A nested map representing all YAML properties.
     */
    @Override
    public Map<String, String> apply(InputStream inputStream) {
        if (inputStream != null)
            return new Yaml().load(inputStream);

        logger.warn("Using {}, but the stream was null.", YamlConfigSource.class);
        return Map.of();
    }
}
