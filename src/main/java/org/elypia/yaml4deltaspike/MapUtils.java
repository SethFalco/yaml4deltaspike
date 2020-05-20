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

import java.util.*;

/**
 * Utility to flatten nested maps into a single level
 * key:value pair set of properties.
 *
 * @author seth@elypia.org (Seth Falco)
 * @since 1.0.0
 */
public final class MapUtils {

    private MapUtils() {
        // Do nothing
    }

    public static <V> Map<String, String> flattenMapProperties(final Map<String, V> input) {
        return flattenMapProperties(input, false);
    }

    public static <V> Map<String, String> flattenMapProperties(final Map<String, V> input, final boolean indexed) {
        final Map<String, String> result = new HashMap<>();
        flattenMapProperties(input, result, indexed);
        return result;
    }

    private static <V> void flattenMapProperties(final Map<String, V> input, final Map<String, String> output, final boolean indexed) {
        flattenMapProperties(input, output, indexed, null);
    }

    private static <V> void flattenMapProperties(final Map<String, V> input, final Map<String, String> output, final boolean indexed, final String prefix) {
        input.forEach((key, value) -> {
            final String k = (prefix == null) ? key : (prefix + '.' + key);

            if (value instanceof Map)
                flattenMapProperties((Map)value, output, indexed, k);
            else if (value instanceof Iterable)
                addIterable(value, k, output, indexed);
            else
                output.put(k, (output.containsKey(k)) ? output.get(k) + "," + value : value.toString());
        });
    }

    private static <V> void addIterable(final V value, final String key, final Map<String, String> output, final boolean indexed) {
        final StringJoiner joiner = new StringJoiner(",");
        int index = 0;

        for (final Object o : (Iterable)value) {
            if (o instanceof Map)
                flattenMapProperties((Map)o, output, indexed, (indexed) ? key + "[" + index++ + "]" : key);
            else
                joiner.add(o.toString());
        }

        if (joiner.length() > 0)
            output.put(key, joiner.toString());
    }
}
