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
 * @author seth@elypia.org (Seth Falco)
 */
public final class MapUtils {

    private MapUtils() {
        // Do nothing
    }

    public static <V> Map<String, String> flattenMapProperties(final Map<String, V> input) {
        Map<String, String> result = new HashMap<>();
        flattenMapProperties(input, result, "");
        return result;
    }

    private static void flattenMapProperties(final Map<?, ?> input, final Map<String, String> output, final String prefix) {
        input.forEach((key, value) -> {
            final String k = (prefix.isEmpty() ? prefix : (prefix + '.')) + key;

            if (value instanceof Map) {
                flattenMapProperties((Map)value, output, k);
            } else if (value instanceof Iterable) {
                final StringBuilder builder = new StringBuilder();

                for (final Object o : (Iterable)value) {
                    if (o instanceof Map)
                        flattenMapProperties((Map)o, output, k);
                    else
                        builder.append(o).append(',');
                }

                if (builder.length() > 0)
                    builder.setLength(builder.length() - 1);

                output.put(k, builder.toString());
            } else {
                output.put(k, value.toString());
            }
        });
    }
}
