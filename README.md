# Blue Alliance API

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.victorrobotics.bluealliance/blue-alliance-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.victorrobotics.bluealliance/blue-alliance-api)

A Java library binding to access FRC event data from [The Blue Alliance](https://www.thebluealliance.com), using their Read API v3.

## Build
Requires JDK 11 or higher. Uses [Jackson](https://github.com/FasterXML/jackson) for JSON decoding. Build with the included Gradle wrapper:
- MacOS/Linux: `./gradlew build`
- Windows: `./gradlew.bat build`

## Usage
The latest release build can be found on [Maven Central](https://central.sonatype.com/artifact/org.victorrobotics.bluealliance/blue-alliance-api) for inclusion in your project. Alternatively, build it yourself and add it to your classpath.

You will need to obtain your own API key from The Blue Alliance, which can be done from the [Account Dashboard](https://www.thebluealliance.com/account). See their [API Docs](https://www.thebluealliance.com/apidocs) for more details.

To authenticate using your API key, simply set it to an environment variable called `TBA_API_KEY`, and the library will handle the rest.

Download data using a static `endpoint` method in one of the following classes:
- `APIStatus`
- `Award`
- `District`
- `Event`
- `Match`
- `Media`
- `Team`

These methods return an `Endpoint`, which is responsible for caching responses and managing invalidation. These objects only exist as long as a reference is kept by the client (timing may vary based on garbage collection); however, an identical call to the same endpoint will return the same object, if present.

To access an endpoint, you have two options. You can either request the data synchronously using the `refresh` method, or asynchronously using the `refreshAsync` method (which returns a `CompletableFuture`). For example, to get a list of matches played by team 1559 in 2023:

```java
Endpoint<List<Match>> endpoint = Match.endpointForTeam("frc1559", 2023);
Optional<List<Match>> optional = endpoint.refresh();
if (optional.isPresent()) {
    List<Match> matches = optional.get();
    ...
}
```

The server's etags are used to reduce unnecessary network traffic.

## Endpoints
Currently, the following TBA data endpoints are implemented:
- /district/{district_key}/events
- /district/{district_key}/events/keys
- /district/{district_key}/events/simple
- /district/{district_key}/rankings
- /district/{district_key}/teams
- /district/{district_key}/teams/keys
- /district/{district_key}/teams/simple
- /districts/{year}
- /event/{event_key}
- /event/{event_key}/alliances
- /event/{event_key}/awards
- /event/{event_key}/district_points
- /event/{event_key}/matches
- /event/{event_key}/matches/keys
- /event/{event_key}/matches/simple
- /event/{event_key}/matches/timeseries
- /event/{event_key}/oprs
- /event/{event_key}/rankings
- /event/{event_key}/simple
- /event/{event_key}/teams
- /event/{event_key}/teams/keys
- /event/{event_key}/teams/simple
- /event/{event_key}/teams/statuses
- /events/{year}
- /events/{year}/keys
- /events/{year}/simple
- /match/{match_key}
- /match/{match_key}/simple
- /match/{match_key}/zebra_motionworks
- /status
- /team/{team_key}
- /team/{team_key}/awards
- /team/{team_key}/awards/{year}
- /team/{team_key}/districts
- /team/{team_key}/event/{event_key}/awards
- /team/{team_key}/event/{event_key}/matches
- /team/{team_key}/event/{event_key}/matches/keys
- /team/{team_key}/event/{event_key}/matches/simple
- /team/{team_key}/event/{event_key}/status
- /team/{team_key}/events
- /team/{team_key}/events/{year}
- /team/{team_key}/events/{year}/keys
- /team/{team_key}/events/{year}/simple
- /team/{team_key}/events/{year}/statuses
- /team/{team_key}/events/keys
- /team/{team_key}/events/simple
- /team/{team_key}/matches/{year}
- /team/{team_key}/matches/{year}/keys
- /team/{team_key}/matches/{year}/simple
- /team/{team_key}/media/{year}
- /team/{team_key}/media/tag/{media_tag}
- /team/{team_key}/media/tag/{media_tag}/{year}
- /team/{team_key}/robots
- /team/{team_key}/simple
- /team/{team_key}/social_media
- /team/{team_key}/years_participated
- /teams/{page_num}
- /teams/{page_num}/keys
- /teams/{page_num}/simple
- /teams/{year}/{page_num}
- /teams/{year}/{page_num}/keys
- /teams/{year}/{page_num}/simple

Match score breakdowns are currently only supported for 2023-2024. For unsupported years, those fields will be an `UnknownScoreBreakdown`.
