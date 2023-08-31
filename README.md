# YAML4DeltaSpike

[![Maven Central](https://img.shields.io/maven-central/v/org.elypia.yaml4deltaspike/yaml4deltaspike)](https://central.sonatype.com/search?q=g%3Aorg.elypia.yaml4deltaspike&smo=true)
[![Docs](https://img.shields.io/badge/docs-yaml4deltaspike-blue.svg)](https://elypia.gitlab.io/yaml4deltaspike)
[![Build](https://gitlab.com/Elypia/yaml4deltaspike/badges/master/pipeline.svg)](https://gitlab.com/Elypia/yaml4deltaspike/commits/master)
[![Coverage](https://gitlab.com/Elypia/yaml4deltaspike/badges/master/coverage.svg)](https://gitlab.com/Elypia/yaml4deltaspike/commits/master)

## About

This project provides YAML configuration support for the DeltaSpike configuration mechanism, and uses [snakeyaml](https://bitbucket.org/snakeyaml/snakeyaml/) to bind the properties to an implementation of the `MapConfigSource`.

The [Gradle](https://gradle.org/)/[Maven](https://maven.apache.org/) import strings can be found on Maven Central, linked above!

## Usage

The configuration should work out of the box once you've depended on it via Gradle or Maven.

It will automatically look for an `application.yml` on the root of the classpath and load it without any additional configuration.

If you want a custom file name, or load order, you can extend and override the `YamlConfigSource` class, and follow the instructions on the [DeltaSpike documentation](https://deltaspike.apache.org/documentation/configuration.html#ProvidingconfigurationusingConfigSources ):

```java
public class CustomYamlConfigSource extends YamlConfigSource {

    public CustomYamlConfigSource() {
        super("custom_application.yml", false);
    }
}
```

### Examples

There are two modes for the `YamlConfigSource`, non-indexed (default) and indexed.

```yaml
application:
  name: YAML4DeltaSpike
  messages:
    - source: source0
      target: target0
    - source: source1
      target: target1
```

#### Non-Indexed (Default)

The non-indexed mode will convert the array of nested objects to a series of lists.

```properties
application.name=YAML4DeltaSpike
application.messages.source=source0,source1
application.messages.target=target0,target1
```

#### Indexed

The indexed mode will convert the array of nested objects to the property key
but with an index added, similarly to that of an array.

```properties
application.name=YAML4DeltaSpike
application.messages[0].source=source0
application.messages[0].target=target0
application.messages[1].source=source1
application.messages[1].target=target1
```
