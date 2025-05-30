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

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Utility to flatten nested maps into a single level key:value pair set of
 * properties.
 *
 * @author seth@falco.fun (Seth Falco)
 * @since 1.0.0
 */
public final class MapUtils {

    /**
     * Don't construct this class, you should only be using the <code>public
     * static</code> methods available.
     */
    private MapUtils() {
        // Do nothing
    }

    /**
     * Calls {@link #flattenMapProperties(Map, boolean)} with <code>indexed
     * </code> set to false.
     *
     * @param input {@link Map} of properties that may contain nested Maps.
     * @param <V> Type(s) of value(s) the {@link Map} contains.
     * @return
     *     {@link Map} that contains all properties accessible by their fully
     *     qualified names.
     * @see #flattenMapProperties(Map, boolean)
     */
    public static <V> Map<String, String> flattenMapProperties(final Map<String, V> input) {
        return flattenMapProperties(input, false);
    }

    /**
     * Converts a {@link Map} of objects to a flattened {@link Map} of
     * {@link String} values.
     *
     * <p>For example, with the given input:</p>
     *
     * <pre><code>
     * Map&lt;String, Object&gt; application = Map.of(
     *     "name", "My App",
     *     "prefixes", List.of("&gt;", "$")
     * );
     *
     * Map&lt;String, Object&gt; map = Map.of("application", application);
     * Map&lt;String, String&gt; result = MapUtils.flattenMapProperties(map);
     * </code></pre>
     *
     * <p>Will result in the following properties, assuming <code>indexed</code> is
     * <code>false</code>:</p>
     *
     * <pre><code>
     * application.name=My App
     * application.prefixes=&gt;,$
     * </code></pre>
     *
     * <p>If <code>indexed</code> is <code>true</code>, the result would be:</p>
     *
     * <pre><code>
     * application.name=My App
     * application.prefixes[0]=&gt;
     * application.prefixes[1]=$
     * </code></pre>
     *
     * @param input {@link Map} of properties that may contain nested Maps.
     * @param indexed
     *     If arrays should be converted to a multiple indexed properties,
     *     appended with [i], or a single comma separated list of values.
     * @param <V> Type(s) of value(s) the {@link Map} contains.
     * @return
     *     {@link Map} that contains all properties accessible by their fully
     *     qualified names.
     */
    public static <V> Map<String, String> flattenMapProperties(final Map<String, V> input, final boolean indexed) {
        final Map<String, String> result = new HashMap<>();
        flattenMapProperties(input, result, indexed);
        return result;
    }

    /**
     * Calls {@link #flattenMapProperties(Map, Map, boolean, String)} with parameter
     * <code>prefix</code> as <code>null</code>, since when we begin flattening the map,
     * there is no prefix by default.
     *
     * @param input {@link Map} of properties that may contain nested Maps.
     * @param output {@link Map} that all properties are written to.
     * @param indexed
     *     If arrays should be converted to a multiple indexed properties,
     *     appended with [i], or a single comma separated list of values.
     * @param <V> Type(s) of value(s) the {@link Map} contains.
     * @see #flattenMapProperties(Map, Map, boolean, String)
     */
    private static <V> void flattenMapProperties(
        final Map<String, V> input,
        final Map<String, String> output,
        final boolean indexed
    ) {
        flattenMapProperties(input, output, indexed, null);
    }

    /**
     * @param input {@link Map} of properties that may contain nested Maps.
     * @param output {@link Map} that all properties are written to.
     * @param indexed
     *     If arrays should be converted to a multiple indexed properties,
     *     appended with [i], or a single comma separated list of values.
     * @param prefix
     *     The partial property name to prefix to any found properties on this
     *     level.
     * @param <V> Type(s) of value(s) the {@link Map} contains.
     */
    private static <V> void flattenMapProperties(
        final Map<String, V> input,
        final Map<String, String> output,
        final boolean indexed,
        final String prefix
    ) {
        input.forEach((key, value) -> {
            if (value == null) {
                return;
            }

            final String k = (prefix == null) ? key : (prefix + '.' + key);

            if (value instanceof Map) {
                flattenMapProperties((Map) value, output, indexed, k);
            } else if (value instanceof Iterable) {
                addIterable((Iterable) value, k, output, indexed);
            } else {
                output.put(k, (output.containsKey(k)) ? output.get(k) + "," + value : value.toString());
            }
        });
    }

    /**
     * @param value Values that needs to be flattened.
     * @param key Property name for this value.
     * @param output {@link Map} that all properties are written to.
     * @param indexed
     *     If arrays should be converted to a multiple indexed properties,
     *     appended with [i], or a single comma separated list of values.
     * @param <V> Type(s) of value(s) the {@link Map} contains.
     */
    private static <V> void addIterable(
        final Iterable<V> value,
        final String key,
        final Map<String, String> output,
        final boolean indexed
    ) {
        final StringJoiner joiner = new StringJoiner(",");
        int index = 0;

        for (final Object o : value) {
            if (o instanceof Map) {
                flattenMapProperties((Map) o, output, indexed, (indexed) ? key + "[" + index++ + "]" : key);
            } else {
                joiner.add(o.toString());
            }
        }

        if (joiner.length() > 0) {
            output.put(key, joiner.toString());
        }
    }
}
