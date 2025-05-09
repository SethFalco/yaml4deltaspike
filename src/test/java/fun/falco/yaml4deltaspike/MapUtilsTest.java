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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * @author seth@falco.fun (Seth Falco)
 */
public class MapUtilsTest {

    /**
     * Test that we flatten map keys correctly.
     *
     * <pre><code>
     * application:
     *   name: Amazing Application
     * database:
     *   host: 127.0.0.1
     *   username: seth
     *   password: notseth
     * </code></pre>
     */
    @Test
    public void testFlattenMap() {
        Map<String, String> application = Map.of("name", "Amazing Application");

        Map<String, String> database = Map.of(
            "host", "127.0.0.1",
            "username", "seth",
            "password", "notseth"
        );

        Map<String, Object> map = Map.of(
            "application", application,
            "database", database
        );

        Map<String, String> result = MapUtils.flattenMapProperties(map);

        assertAll("Assert the correct number of values are found, and that all values are correct.",
            () -> assertEquals(4, result.size()),
            () -> assertEquals("Amazing Application", result.get("application.name")),
            () -> assertEquals("127.0.0.1", result.get("database.host")),
            () -> assertEquals("seth", result.get("database.username")),
            () -> assertEquals("notseth", result.get("database.password"))
        );
    }

    /**
     * Test that we flatten nested map keys correctly.
     *
     * <pre><code>
     * application:
     *   name: Another Amazing App
     *   database:
     *     host: localhost
     *     username: seth
     *     password: password
     * </code></pre>
     */
    @Test
    public void testFlattenNestedMap() {
        Map<String, String> database = Map.of(
            "host", "localhost",
            "username", "seth",
            "password", "password12345"
        );

        Map<String, Object> application = Map.of(
            "name", "Another Amazing App",
            "database", database
        );

        Map<String, Object> map = Map.of("application", application);
        Map<String, String> result = MapUtils.flattenMapProperties(map);

        assertAll("Assert that we can handle nested maps correctly.",
            () -> assertEquals(4, result.size()),
            () -> assertEquals("Another Amazing App", result.get("application.name")),
            () -> assertEquals("localhost", result.get("application.database.host")),
            () -> assertEquals("seth", result.get("application.database.username")),
            () -> assertEquals("password12345", result.get("application.database.password"))
        );
    }

    /**
     * Test that we flatten nested map keys with lists correctly.
     *
     * <pre><code>
     * application:
     *   name: Another Amazing App
     *   prefixes:
     *     - >
     *     - $
     * </code></pre>
     */
    @Test
    public void testFlattenWithList() {
        Map<String, Object> application = Map.of(
            "name", "Yet Another Amazing App",
            "prefixes", List.of(">", "$")
        );

        Map<String, Object> map = Map.of("application", application);
        Map<String, String> result = MapUtils.flattenMapProperties(map);

        assertAll("Assert that we can handle lists or array correctly.",
            () -> assertEquals(2, result.size()),
            () -> assertEquals("Yet Another Amazing App", result.get("application.name")),
            () -> assertEquals(">,$", result.get("application.prefixes"))
        );
    }

    /**
     * Test that we flatten nested map keys with lists of objects correctly.
     *
     * <pre><code>
     * application:
     *   name: How Does An App Get This Awesome
     *   messages:
     *     - source: one
     *       target: two
     *     - source: three
     *       target: four
     * </code></pre>
     */
    @Test
    public void testFlattenWithObjectArrayLists() {
        Map<String, Object> message1 = Map.of(
            "source", "one",
            "target", "two"
        );

        Map<String, Object> message2 = Map.of(
            "source", "three",
            "target", "four"
        );

        Map<String, Object> application = Map.of(
            "name", "How Does An App Get This Awesome",
            "messages", List.of(message1, message2)
        );

        Map<String, Object> map = Map.of("application", application);
        Map<String, String> result = MapUtils.flattenMapProperties(map);

        assertAll("Assert that we can handle lists of maps with list items.",
            () -> assertEquals(3, result.size()),
            () -> assertEquals("How Does An App Get This Awesome", result.get("application.name")),
            () -> assertEquals("one,three", result.get("application.messages.source")),
            () -> assertEquals("two,four", result.get("application.messages.target"))
        );
    }

    /**
     * Test that we flatten nested map keys with lists of objects correctly
     * where indexed is true.
     *
     * <pre><code>
     * application:
     *   name: How Does An App Get This Awesome
     *   messages:
     *     - source: one
     *       target: two
     *     - source: three
     *       target: four
     * </code></pre>
     */
    @Test
    public void testFlattenWithObjectArrayIndexed() {
        Map<String, Object> message1 = Map.of(
            "source", "one",
            "target", "two"
        );

        Map<String, Object> message2 = Map.of(
            "source", "three",
            "target", "four"
        );

        Map<String, Object> application = Map.of(
            "name", "How Does An App Get This Awesome",
            "messages", List.of(message1, message2)
        );

        Map<String, Object> map = Map.of("application", application);
        Map<String, String> result = MapUtils.flattenMapProperties(map, true);

        assertAll("Assert that we can handle lists of maps with list items.",
            () -> assertEquals(5, result.size()),
            () -> assertEquals("How Does An App Get This Awesome", result.get("application.name")),
            () -> assertEquals("one", result.get("application.messages[0].source")),
            () -> assertEquals("two", result.get("application.messages[0].target")),
            () -> assertEquals("three", result.get("application.messages[1].source")),
            () -> assertEquals("four", result.get("application.messages[1].target"))
        );
    }

    /**
     * Ensure we don't crash if a property is null.
     *
     * <pre><code>
     * application:
     *   name: Another Amazing App
     *   prefixes:
     * </code></pre>
     */
    @Test
    public void testWithNull() {
        Map<String, Object> application = new HashMap<>();
        application.put("name", "Yet Another Amazing App");
        application.put("prefixes", null);

        Map<String, Object> map = Map.of("application", application);
        Map<String, String> result = MapUtils.flattenMapProperties(map);

        assertAll("Assert that we can handle lists or array correctly.",
            () -> assertEquals(1, result.size()),
            () -> assertEquals("Yet Another Amazing App", result.get("application.name"))
        );
    }
}
