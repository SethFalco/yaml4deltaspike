# YAML4DeltaSpike

[![](https://img.shields.io/maven-central/v/org.elypia.yaml4deltaspike/yaml4deltaspike)](https://search.maven.org/artifact/org.elypia.yaml4deltaspike/yaml4deltaspike) [![](https://gitlab.com/SethFalco/yaml4deltaspike/badges/main/pipeline.svg)](https://gitlab.com/SethFalco/yaml4deltaspike) [![](https://gitlab.com/SethFalco/yaml4deltaspike/badges/master/coverage.svg)](https://gitlab.com/SethFalco/yaml4deltaspike/commits/master)

## Deprecation Notice

**This project is no longer maintained, as I'm working on migrating YAML support upstream into [DeltaSpike](https://github.com/apache/deltaspike).**

## About

Provides YAML configuration support for the DeltaSpike configuration mechanism, and uses [snakeyaml](https://bitbucket.org/snakeyaml/snakeyaml/) to bind the properties to an implementation of the `MapConfigSource`.

## Getting Started

### Import

Visit [YAML4DeltaSpike on Maven Central](https://search.maven.org/artifact/org.elypia.yaml4deltaspike/yaml4deltaspike), and follow the instructions for your build system of choice to add YAML4DeltaSpike to your project.

### Usage

The configuration should work out of the box once you've added the dependency to your project.

YAML4Deltaspike automatically looks for an `application.yml` in the root of the classpath and loads it without any additional configuration.

If you want a custom file name, or load order, you can extend and override the `YamlConfigSource` class, and follow the instructions on the [DeltaSpike documentation](https://deltaspike.apache.org/documentation/configuration.html#ProvidingconfigurationusingConfigSources):

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

Non-indexed mode will convert arrays of nested objects to a series of lists.

```properties
application.name=YAML4DeltaSpike
application.messages.source=source0,source1
application.messages.target=target0,target1
```

#### Indexed

Indexed mode will convert arrays of nested objects to the property key, but with a 0-indexed suffix, similarly to that of an array-access in Java.

```properties
application.name=YAML4DeltaSpike
application.messages[0].source=source0
application.messages[0].target=target0
application.messages[1].source=source1
application.messages[1].target=target1
```
