package org.victorrobotics.bluealliance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class TestDistrict {
  @Test
  void testConstructor() {
    District district = new District("abbreviation", "display name", "district key", 2023);
    assertEquals("abbreviation", district.abbreviation);
    assertEquals("display name", district.displayName);
    assertEquals("district key", district.key);
    assertEquals(2023, district.year);
  }

  @Test
  void testEndpointForYear() {
    // 11 districts
    // First in list:
    /* { "abbreviation": "chs", "display_name": "FIRST Chesapeake", "key":
     * "2023chs", "year": 2023 } */

    Endpoint<List<District>> endpoint = District.endpointForYear(2023);
    assertNotNull(endpoint);

    List<District> districts = endpoint.refresh().get();
    assertNotNull(districts);
    assertEquals(11, districts.size());

    District district = districts.get(0);
    assertNotNull(district);
    assertEquals("chs", district.abbreviation);
    assertEquals("FIRST Chesapeake", district.displayName);
    assertEquals("2023chs", district.key);
    assertEquals(2023, district.year);
  }

  @Test
  void testEndpointForTeam() {
    // 9 districts
    // First in list:
    /* { "abbreviation": "chs", "display_name": "FIRST Chesapeake", "key":
     * "2016chs", "year": 2016 } */

    Endpoint<List<District>> endpoint = District.endpointForTeam("frc1086");
    assertNotNull(endpoint);

    List<District> districts = endpoint.refresh().get();
    assertNotNull(districts);
    assertEquals(9, districts.size());

    District district = districts.get(0);
    assertNotNull(district);
    assertEquals("chs", district.abbreviation);
    assertEquals("FIRST Chesapeake", district.displayName);
    assertEquals("2016chs", district.key);
    assertEquals(2016, district.year);
  }
}
