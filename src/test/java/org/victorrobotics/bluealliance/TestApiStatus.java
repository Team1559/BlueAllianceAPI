package org.victorrobotics.bluealliance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class TestApiStatus {
  @Test
  void testConstructors() {
    ApiStatus.AppVersion iosVersion = new ApiStatus.AppVersion(-1, -1);
    assertEquals(-1, iosVersion.minimum);
    assertEquals(-1, iosVersion.latest);

    ApiStatus.AppVersion androidVersion = new ApiStatus.AppVersion(5000000, 6000199);
    assertEquals(5000000, androidVersion.minimum);
    assertEquals(6000199, androidVersion.latest);

    ApiStatus status =
        new ApiStatus(2023, 2024, false, List.of("event key"), iosVersion, androidVersion);
    assertEquals(2023, status.currentSeason);
    assertEquals(2024, status.maxSeason);
    assertFalse(status.isDatafeedDown);
    assertEquals(List.of("event key"), status.downEvents);
    assertSame(iosVersion, status.iosVersion);
    assertSame(androidVersion, status.androidVersion);
  }

  @Test
  void testEndpoint() {
    Endpoint<ApiStatus> endpoint = ApiStatus.endpoint();
    assertNotNull(endpoint);

    ApiStatus status = endpoint.refresh().get();
    assertNotEquals(0, status.currentSeason);
    assertNotEquals(0, status.maxSeason);
    assertNotNull(status.downEvents);
    assertNotNull(status.iosVersion);
    assertNotNull(status.androidVersion);

    assertNotEquals(0, status.iosVersion.minimum);
    assertNotEquals(0, status.iosVersion.latest);
    assertNotEquals(0, status.androidVersion.minimum);
    assertNotEquals(0, status.androidVersion.latest);
  }
}
