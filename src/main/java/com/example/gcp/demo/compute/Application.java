package com.example.gcp.demo.compute;

import com.example.gcp.demo.oauth.OauthUtils;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.compute.Compute.Projects;
import com.google.api.services.compute.model.Zone;
import com.google.api.services.compute.model.ZoneList;
import java.util.List;

public class Application {

  public static void main(String[] args) throws Exception {

    Credential credential = OauthUtils.authorize();
    String accessToken = credential.getAccessToken();

    String projectId = "MY PROJECT ID";
    ComputeEngineSdkClient ceClient = new ComputeEngineSdkClient(credential);

    ZoneList zoneList = ceClient.getAvailableZones(projectId);
    List<Zone> zones = zoneList.getItems();
    for(Zone zone : zones){
      System.out.println(zone.toString());
    }

  }
}
