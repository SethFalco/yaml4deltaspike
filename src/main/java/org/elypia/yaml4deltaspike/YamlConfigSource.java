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
 * @author seth@elypia.org (Seth Falco)
 */
public class YamlConfigSource extends MapConfigSource {

    private static final String CONFIG_NAME = "application.yml";

    public YamlConfigSource() {
        super(MapUtils.flattenMapProperties(new Yaml().load(YamlConfigSource.class.getClassLoader().getResourceAsStream(CONFIG_NAME))));
    }

    @Override
    public String getConfigName() {
        return CONFIG_NAME;
    }
}
