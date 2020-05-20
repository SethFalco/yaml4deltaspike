/*
 * Copyright 2020-2020 Elypia CIC
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

package org.elypia.yaml4deltaspike;

import org.apache.deltaspike.core.impl.config.MapConfigSource;
import org.yaml.snakeyaml.Yaml;

/**
 * You can create a configuration with a certain name by just
 * extending this class, calling super() with the new configuration
 * name you want.
 *
 * <code>
 *     public class CustomYamlConfigSource extends YamlConfigSource {
 *
 *         public CustomYamlConfigSource() {
 *             super("custom_application.yml");
 *         }
 *     }
 * </code>
 *
 * This will seek out my_application.yml, instead of application.yml.
 *
 * @author seth@elypia.org (Seth Falco)
 * @since 1.0.0
 */
public class YamlConfigSource extends MapConfigSource {

    /** The default configuration name, this is already available to use if this is on your classpath. */
    private static final String DEFAULT_CONFIG_NAME = "application.yml";

    /** The configuration file name that the {@link YamlConfigSource} is for. */
    private final String configName;

    /** Construct the {@link YamlConfigSource} with {@link #DEFAULT_CONFIG_NAME}. */
    public YamlConfigSource() {
        this(DEFAULT_CONFIG_NAME, false);
    }

    public YamlConfigSource(String configName, boolean indexed) {
        super(MapUtils.flattenMapProperties(new Yaml().load(YamlConfigSource.class.getClassLoader().getResourceAsStream(configName)), indexed));
        this.configName = configName;
    }

    @Override
    public String getConfigName() {
        return "yaml-file " + configName;
    }
}
