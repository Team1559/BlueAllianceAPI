package org.victorrobotics.bluealliance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TestDistrictRanking {
  @Test
  void testConstructor() {
    District.Ranking.EventPoints eventPointsEntry =
        new District.Ranking.EventPoints(true, 100, 65, 20, 10, "event key", 5);
    assertTrue(eventPointsEntry.isDistrictChampionship);
    assertEquals(100, eventPointsEntry.totalPoints);
    assertEquals(65, eventPointsEntry.alliancePoints);
    assertEquals(20, eventPointsEntry.eliminationPoints);
    assertEquals(10, eventPointsEntry.awardPoints);
    assertEquals("event key", eventPointsEntry.eventKey);
    assertEquals(5, eventPointsEntry.qualificationPoints);

    List<District.Ranking.EventPoints> eventPoints = List.of(eventPointsEntry);

    District.Ranking ranking = new District.Ranking("team key", 1, 2, 3, eventPoints);
    assertEquals("team key", ranking.teamKey);
    assertEquals(1, ranking.rank);
    assertEquals(2, ranking.rookieBonus);
    assertEquals(3, ranking.pointTotal);

    assertEquals(eventPoints.size(), ranking.eventPoints.size());
    assertSame(eventPoints.get(0), ranking.eventPoints.get(0));
  }

  @Test
  void testEndpointForKey() {
    // 134 entries
    // First in list:
    /* { "event_points": [ { "alliance_points": 16, "award_points": 5,
     * "district_cmp": false, "elim_points": 30, "event_key": "2016vahay",
     * "qual_points": 16, "total": 67 }, { "alliance_points": 16,
     * "award_points": 5, "district_cmp": false, "elim_points": 30, "event_key":
     * "2016mdbet", "qual_points": 22, "total": 73 }, { "alliance_points": 48,
     * "award_points": 15, "district_cmp": true, "elim_points": 90, "event_key":
     * "2016chcmp", "qual_points": 66, "total": 219 } ], "point_total": 359,
     * "rank": 1, "rookie_bonus": 0, "team_key": "frc1418" } */

    Endpoint<List<District.Ranking>> endpoint = District.Ranking.endpointForDistrict("2016chs");
    assertNotNull(endpoint);

    List<District.Ranking> districts = endpoint.refresh()
                                               .get();
    assertNotNull(districts);
    assertEquals(134, districts.size());

    District.Ranking ranking = districts.get(0);
    assertNotNull(ranking);
    assertEquals(359, ranking.pointTotal);
    assertEquals(1, ranking.rank);
    assertEquals(0, ranking.rookieBonus);
    assertEquals("frc1418", ranking.teamKey);

    assertNotNull(ranking.eventPoints);
    assertEquals(3, ranking.eventPoints.size());

    District.Ranking.EventPoints eventPoints = ranking.eventPoints.get(0);
    assertNotNull(eventPoints);
    assertEquals(16, eventPoints.alliancePoints);
    assertEquals(5, eventPoints.awardPoints);
    assertFalse(eventPoints.isDistrictChampionship);
    assertEquals(30, eventPoints.eliminationPoints);
    assertEquals("2016vahay", eventPoints.eventKey);
    assertEquals(16, eventPoints.qualificationPoints);
    assertEquals(67, eventPoints.totalPoints);
  }
}
