<div align="center">

# YAML4DeltaSpike
[![Matrix]][matrix-community] [![Discord]][discord-guild] [![Maven Central]][maven-page] [![Docs]][documentation] [![Build]][gitlab] [![Coverage]][gitlab] [![Donate]][elypia-donate]
</div>

## About
This project, as the name entails, provides YAML configuration support for the DeltaSpike
configuration mechanism and uses [snakeyaml] to bind the properties to an implementation
of the `MapConfigSource`.

The [Gradle]/[Maven] import strings can be found at the maven-central badge above!

## Usage
The configuration should work out of the box once you've depended on it via Gradle or Maven.

It will automatically look for an `application.yml` on the root of the classpath
and load it without any additional configuration.

If you want a custom file name, or load order, you can extend and override the 
`YamlConfigSource` class, and follow the instructions on the [DeltaSpike documentation]:

```java
public class CustomYamlConfigSource extends YamlConfigSource {
    
    public CustomYamlConfigSource() {
        super("custom_application.yml", false);
    }
}
```

### Examples
There are two modes for the `YamlConfigSource`, non-indexed (default) and indexed.
We'll discuss them both using the following YAML as an example.

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

## Open-Source
This project is open-source under the [Apache 2.0]!  
While not legal advice, you can find a [TL;DR] that sums up what
you're allowed and not allowed to do along with any requirements if you want to 
use or derive work from this source code!  

[matrix-community]: https://matrix.to/#/+elypia:matrix.org "Matrix Invite"
[discord-guild]: https://discord.com/invite/hprGMaM "Discord Invite"
[maven-page]: https://search.maven.org/search?q=g:org.elypia.yaml4deltaspike "Maven Central"
[documentation]: https://elypia.gitlab.io/yaml4deltaspike "Project Documentation"
[gitlab]: https://gitlab.com/Elypia/yaml4deltaspike/commits/master "Repository on GitLab"
[elypia-donate]: https://elypia.org/donate "Donate to Elypia"
[Gradle]: https://gradle.org/ "Depend via Gradle"
[Maven]: https://maven.apache.org/ "Depend via Maven"
[snakeyaml]: https://bitbucket.org/asomov/snakeyaml/src/master/ "SnakeYAML on BitBucket"
[DeltaSpike documentation]: https://deltaspike.apache.org/documentation/configuration.html#ProvidingconfigurationusingConfigSources "DeltaSpike Documentation"
[Apache 2.0]: https://www.apache.org/licenses/LICENSE-2.0 "Apache 2.0 License"
[TL;DR]: https://tldrlegal.com/license/apache-license-2.0-(apache-2.0) "TL;DR of Apache 2.0"

[Matrix]: https://img.shields.io/matrix/elypia:matrix.org?logo=matrix "Matrix Shield"
[Discord]: https://discord.com/api/guilds/184657525990359041/widget.png "Discord Shield"
[Maven Central]: https://img.shields.io/maven-central/v/org.elypia.yaml4deltaspike/yaml4deltaspike "Download Shield"
[Docs]: https://img.shields.io/badge/docs-yaml4deltaspike-blue.svg "Documentation Shield"
[Build]: https://gitlab.com/Elypia/yaml4deltaspike/badges/master/pipeline.svg "GitLab Build Shield"
[Coverage]: https://gitlab.com/Elypia/yaml4deltaspike/badges/master/coverage.svg "GitLab Coverage Shield"
[Donate]: https://img.shields.io/badge/donate-elypia-blueviolet "Donate Shield"
