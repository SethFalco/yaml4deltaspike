/*
 * Copyright 2020-2020 Elypia CIC and Contributors
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

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author seth@elypia.org (Seth Falco)
 */
public class YamlConfigSourceTest {

    /**
     * YAML4DeltaSpike already looks for a certain file by default.
     * This file is not provided by the library however.
     *
     * It should <strong>not</strong> throw an error in the case this
     * file does not exist.
     */
    @Test
    public void exceptionNotThrownOnDefaultConfiguration() {
        assertDoesNotThrow(() -> new YamlConfigSource());
    }

    @Test
    public void testThatInputStreamWorks() throws IOException {
        String yaml =
            "application:\n"    +
            "  name: Testing";

        try (InputStream stream = new ByteArrayInputStream(yaml.getBytes(StandardCharsets.UTF_8))) {
            YamlConfigSource config = new YamlConfigSource(stream);

            assertAll("Test a simple configuration",
                () -> assertEquals("yaml input-stream", config.getConfigName()),
                () -> assertFalse(config.isIndexed()),
                () -> assertEquals("Testing", config.getPropertyValue("application.name"))
            );
        }
    }

    @Test
    public void testInputStreamWithCustomName() throws IOException {
        String yaml =
            "application:\n"    +
            "  name: Testing";

        try (InputStream stream = new ByteArrayInputStream(yaml.getBytes(StandardCharsets.UTF_8))) {
            YamlConfigSource config = new YamlConfigSource(stream, "custom-stream", true);

            assertAll("Test custom config name and setting the indexed parameter",
                () -> assertEquals("yaml custom-stream", config.getConfigName()),
                () -> assertTrue(config.isIndexed()),
                () -> assertEquals("Testing", config.getPropertyValue("application.name"))
            );
        }
    }

    @Test
    public void testWithNullInputStream() {
        assertDoesNotThrow(() -> new YamlConfigSource((InputStream)null));
    }
}
