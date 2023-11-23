package org.victorrobotics.bluealliance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class TestAward {
  @Test
  void testConstructors() {
    Award.Recipient recipient = new Award.Recipient("frc1559", "person");
    assertEquals("frc1559", recipient.teamKey);
    assertEquals("person", recipient.awardee);

    Award.Recipient recipient2 = new Award.Recipient("frc1559", null);
    assertNull(recipient2.awardee);

    List<Award.Recipient> recipients = List.of(recipient, recipient2);

    Award award = new Award("award name", Award.Type.WINNER, "event key", recipients, 2023);
    assertEquals("event key", award.eventKey);
    assertSame(Award.Type.WINNER, award.type);
    assertEquals("event key", award.eventKey);
    assertEquals(2023, award.year);

    assertNotNull(award.recipients);
    assertEquals(recipients.size(), award.recipients.size());
    assertSame(recipients.get(0), award.recipients.get(0));
  }

  @Test
  void testEndpointForEvent() {
    // 22 awards
    // 17th in list:
    /* { "award_type": 4, "event_key": "2023paca", "name":
     * "FIRST Dean's List Finalist Award", "recipient_list": [ { "awardee":
     * "Grace Goslin", "team_key": "frc3504" }, { "awardee": "Ian Orsag",
     * "team_key": "frc4467" } ], "year": 2023 } */

    Endpoint<List<Award>> endpoint = Award.endpointForEvent("2023paca");
    assertNotNull(endpoint);

    List<Award> awards = endpoint.refresh()
                                 .get();
    assertNotNull(awards);
    assertEquals(22, awards.size());

    Award award = awards.get(16);
    assertNotNull(award);
    assertSame(Award.Type.DEANS_LIST, award.type);
    assertEquals("2023paca", award.eventKey);
    assertEquals("FIRST Dean's List Finalist Award", award.name);
    assertEquals(2023, award.year);
    assertNotNull(award.recipients);
    assertEquals(2, award.recipients.size());

    Award.Recipient recipient = award.recipients.get(0);
    assertNotNull(recipient);
    assertEquals("Grace Goslin", recipient.awardee);
    assertEquals("frc3504", recipient.teamKey);
  }

  @Test
  void testEndpointForTeam() {
    // 16 awards
    // 14th in list:
    /* { "award_type": 4, "event_key": "2018nyro", "name":
     * "FIRST Dean's List Finalist Award", "recipient_list": [ { "awardee":
     * "Karenna Thomas", "team_key": "frc191" }, { "awardee": "Alek Aherns",
     * "team_key": "frc1559" } ], "year": 2018 } */

    Endpoint<List<Award>> endpoint = Award.endpointForTeam("frc1559");
    assertNotNull(endpoint);

    List<Award> awards = endpoint.refresh()
                                 .get();
    assertNotNull(awards);
    assertEquals(16, awards.size());

    Award award = awards.get(13);
    assertNotNull(award);
    assertSame(Award.Type.DEANS_LIST, award.type);
    assertEquals("2018nyro", award.eventKey);
    assertEquals("FIRST Dean's List Finalist Award", award.name);
    assertEquals(2018, award.year);
    assertNotNull(award.recipients);
    assertEquals(2, award.recipients.size());

    Award.Recipient recipient = award.recipients.get(1);
    assertNotNull(recipient);
    assertEquals("Alek Aherns", recipient.awardee);
    assertEquals("frc1559", recipient.teamKey);
  }

  @Test
  void testEndpointForTeamDuringYear() {
    // 1 award
    /* { "award_type": 16, "event_key": "2023nyro", "name":
     * "Industrial Design Award sponsored by General Motors", "recipient_list":
     * [ { "awardee": null, "team_key": "frc1559" } ], "year": 2023 } */

    Endpoint<List<Award>> endpoint = Award.endpointForTeam("frc1559", 2023);
    assertNotNull(endpoint);

    List<Award> awards = endpoint.refresh()
                                 .get();
    assertNotNull(awards);
    assertEquals(1, awards.size());

    Award award = awards.get(0);
    assertNotNull(award);
    assertSame(Award.Type.INDUSTRIAL_DESIGN, award.type);
    assertEquals("2023nyro", award.eventKey);
    assertEquals("Industrial Design Award sponsored by General Motors", award.name);
    assertEquals(2023, award.year);
    assertNotNull(award.recipients);
    assertEquals(1, award.recipients.size());

    Award.Recipient recipient = award.recipients.get(0);
    assertNotNull(recipient);
    assertNull(recipient.awardee);
    assertEquals("frc1559", recipient.teamKey);
  }

  @Test
  void testEndpointForTeamAtEvent() {
    // 2 awards
    /* { "award_type": 14, "event_key": "2005roc", "name":
     * "Highest Rookie Seed", "recipient_list": [ { "awardee": null, "team_key":
     * "frc1559" } ], "year": 2005 } */

    Endpoint<List<Award>> endpoint = Award.endpointForTeam("frc1559", "2005roc");
    assertNotNull(endpoint);

    List<Award> awards = endpoint.refresh()
                                 .get();
    assertNotNull(awards);
    assertEquals(2, awards.size());

    Award award = awards.get(0);
    assertNotNull(award);
    assertSame(Award.Type.HIGHEST_ROOKIE_SEED, award.type);
    assertEquals("2005roc", award.eventKey);
    assertEquals("Highest Rookie Seed", award.name);
    assertEquals(2005, award.year);
    assertNotNull(award.recipients);
    assertEquals(1, award.recipients.size());

    Award.Recipient recipient = award.recipients.get(0);
    assertNotNull(recipient);
    assertNull(recipient.awardee);
    assertEquals("frc1559", recipient.teamKey);
  }
}
