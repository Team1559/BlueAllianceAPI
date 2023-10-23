package org.victorrobotics.tba;

import java.net.URI;

public class TBA_StatusEndpoint extends TBA_Endpoint<TBA_Status> {
  private static final URI STATUS_URI = URI.create(TBA_API_URL + "/status");

  public TBA_StatusEndpoint() {
    super(STATUS_URI, TBA_Status.class);
  }
}
