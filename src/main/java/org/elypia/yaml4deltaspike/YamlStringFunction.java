package org.elypia.yaml4deltaspike;

import org.apache.deltaspike.core.impl.config.MapConfigSource;
import org.slf4j.*;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;
import java.util.function.Function;

/**
 * Since the {@link MapConfigSource} insists that we call super, and Java
 * requires super be the first call of a method; we use this {@link Function}
 * to help as get around that limitation to perform more methods
 * while calling super.
 *
 * @author seth@elypia.org (Seth Falco)
 * @since 1.2.0
 */
public class YamlStringFunction implements Function<String, Map<String, Object>> {

    /** Logging with slf4j. */
    private static final Logger logger = LoggerFactory.getLogger(YamlStringFunction.class);

    /**
     * @param configPath The path to the configuration file.
     * @return A nested map representing all YAML properties.
     */
    @Override
    public Map<String, Object> apply(String configPath) {
        try (InputStream inputStream = YamlConfigSource.class.getClassLoader().getResourceAsStream(configPath)) {
            if (inputStream != null)
                return new Yaml().load(inputStream);
        } catch (IOException ex) {
            logger.error("This hopefully should never produce any IOExceptions.", ex);
        }

        logger.warn("Using {}, but `{}` was not found on the classpath.", YamlConfigSource.class, configPath);
        return Map.of();
    }
}
