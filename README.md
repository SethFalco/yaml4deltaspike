# YAML4DeltaSpike [![Matrix]][matrix-community] [![Discord]][discord-guild] [![Bintray]][bintray-page] [![Build]][gitlab] [![Coverage]][gitlab] [![Donate]][elypia-donate]
## About
This project, as the name entails, provides YAML configuration support for the DeltaSpike
configuration resolver and uses [snakeyaml] to binds the properties to an implementation
of the MapConfigSource.

This is an incredibly small project, so it will rarely receive updates due to the small
scope and code surface.

## Usage
The configuration should work out of the box once you've depended on it via Gradle or Maven;
the implementation strings can be found on the bintray badge above.

It will automatically look for an `application.yml` on the root of the classpath
and load it without any additional configuration.

If you want a custom file name, or load order, you can extend and override the 
`YamlConfigSource` class, and follow the instructions on the [DeltaSpike documentation]:

```java
public class CustomYamlConfigSource extends YamlConfigSource {
    
    public CustomYamlConfigSource() {
        super("custom_application.yml");
    }
}
```

## Open-Source
This project is open-source under the [Apache 2.0]!  
While not legal advice, you can find a [TL;DR] that sums up what
you're allowed and not allowed to do along with any requirements if you want to 
use or derive work from this source code!  

[matrix-community]: https://matrix.to/#/+elypia:matrix.org "Matrix Invite"
[discord-guild]: https://discord.com/invite/hprGMaM "Discord Invite"
[bintray-page]: https://bintray.com/elypia/yaml4deltaspike/yaml4deltaspike/_latestVersion "Bintray Latest Version"
[gitlab]: https://gitlab.com/Elypia/yaml4deltaspike/commits/master "Repository on GitLab"
[elypia-donate]: https://elypia.org/donate "Donate to Elypia"
[snakeyaml]: https://bitbucket.org/asomov/snakeyaml/src/master/ "SnakeYAML on BitBucket"
[DeltaSpike documentation]: https://deltaspike.apache.org/documentation/configuration.html#ProvidingconfigurationusingConfigSources "DeltaSpike Documentation"
[Apache 2.0]: https://www.apache.org/licenses/LICENSE-2.0 "Apache 2.0 License"
[TL;DR]: https://tldrlegal.com/license/apache-license-2.0-(apache-2.0) "TL;DR of Apache 2.0"

[Matrix]: https://img.shields.io/matrix/elypia-general:matrix.org?logo=matrix "Matrix Shield"
[Discord]: https://discord.com/api/guilds/184657525990359041/widget.png "Discord Shield"
[Bintray]: https://api.bintray.com/packages/elypia/yaml4deltaspike/yaml4deltaspike/images/download.svg "Bintray Download Shield"
[Build]: https://gitlab.com/Elypia/yaml4deltaspike/badges/master/pipeline.svg "GitLab Build Shield"
[Coverage]: https://gitlab.com/Elypia/yaml4deltaspike/badges/master/coverage.svg "GitLab Coverage Shield"
[Donate]: https://img.shields.io/badge/Elypia-Donate-blueviolet "Donate Shield"
