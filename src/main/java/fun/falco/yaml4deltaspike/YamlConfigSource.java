/*
 * Copyright 2020-2025 Seth Falco and YAML4DeltaSpike Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fun.falco.yaml4deltaspike;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

import org.apache.deltaspike.core.impl.config.MapConfigSource;

/**
 * You can create a configuration with a certain name by just extending this
 * class, calling super() with the new configuration name you want.
 *
 * <pre><code>public class CustomYamlConfigSource extends YamlConfigSource {
 *
 *     public CustomYamlConfigSource() {
 *         super("custom_application.yml");
 *     }
 * }
 * </code></pre>
 *
 * <p>This will seek out my_application.yml, instead of application.yml.</p>
 *
 * @author seth@falco.fun (Seth Falco)
 * @since 1.0.0
 */
public class YamlConfigSource extends MapConfigSource {

    /**
     * Default configuration name, this is already available to use if this is
     * on your classpath.
     */
    private static final String DEFAULT_FILE_PATH = "application.yml";

    /**
     * Configuration file/stream name that the {@link YamlConfigSource} is for.
     */
    private final String configName;

    /**
     * @see #isIndexed()
     */
    private final boolean indexed;

    /**
     * Construct the {@link YamlConfigSource} with {@link #DEFAULT_FILE_PATH}.
     */
    public YamlConfigSource() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Calls {@link #YamlConfigSource(String, boolean)} with the parameter
     * <code>indexed</code> as false.
     *
     * @param configPath
     *     File path relative to the classpath of the configuration.
     * @throws NullPointerException If configPath is null.
     * @see #YamlConfigSource(String, boolean)
     */
    public YamlConfigSource(String configPath) {
        this(configPath, false);
    }

    /**
     * @param configPath File relative to the classpath of the configuration.
     * @param indexed If this configuration should used indexed keys, or lists.
     * @throws NullPointerException If configPath is null.
     */
    public YamlConfigSource(String configPath, boolean indexed) {
        this(new YamlStringFunction().apply(configPath), configPath, indexed);
    }

    /**
     * @param inputStream Input stream to read the configuration from.
     */
    public YamlConfigSource(InputStream inputStream) {
        this(inputStream, false);
    }

    /**
     * @param inputStream Input stream to read the configuration from.
     * @param indexed If this configuration should used indexed keys, or lists.
     */
    public YamlConfigSource(InputStream inputStream, boolean indexed) {
        this(inputStream, "input-stream", indexed);
    }

    /**
     * @param inputStream Input stream to read the configuration from.
     * @param configName
     *     File path relative to the classpath of the configuration.
     * @param indexed If this configuration should used indexed keys, or lists.
     * @throws NullPointerException If configName is null.
     */
    public YamlConfigSource(InputStream inputStream, String configName, boolean indexed) {
        this(new YamlInputStreamFunction().apply(inputStream), configName, indexed);
    }

    /**
     * @param map
     *     {@link Map}, which may include nested {@link Map}s, of configuration
     *     properties.
     * @param configName
     *     File path relative to the classpath of the configuration.
     * @param indexed If this configuration should used indexed keys, or lists.
     */
    private YamlConfigSource(Map<String, Object> map, String configName, boolean indexed) {
        super(MapUtils.flattenMapProperties(map, indexed));
        this.configName = Objects.requireNonNull(configName);
        this.indexed = indexed;
    }

    @Override
    public String getConfigName() {
        return "yaml " + configName;
    }

    /**
     * If to return indexed key/value pairs, or a single key/value pair with
     * comma separated values.
     *
     * <p>If <code>indexed</code> is true, when we get arrays of objects such
     * as:</p>
     *
     * <pre><code>
     * messages:
     *   - source: one
     *     target: two
     *   - source: three
     *     target: four
     * </code></pre>
     *
     * <p>It will return:</p>
     *
     * <pre><code>
     * messages[0].source=one
     * messages[0].target=two
     * messages[1].source=three
     * messages[2].target=four
     * </code></pre>
     *
     * <p>While if <code>indexed</code> is false (default), it would return:</p>
     *
     * <pre><code>
     * messages.source=one,three
     * messages.target=two,four
     * </code></pre>
     *
     * @return
     *     If this {@link org.apache.deltaspike.core.spi.config.ConfigSource}
     *     uses indexed arrays, or comma separated values.
     */
    public boolean isIndexed() {
        return indexed;
    }
}
