package com.example.gcp.demo.compute;

import com.example.gcp.demo.auth.OauthUtils;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.compute.model.Disk;
import com.google.api.services.compute.model.DiskList;
import com.google.api.services.compute.model.Instance;
import com.google.api.services.compute.model.InstanceList;
import com.google.api.services.compute.model.Operation;
import com.google.api.services.compute.model.Zone;
import com.google.api.services.compute.model.ZoneList;
import java.util.List;

public class ComputeEngineDemo {

  public static void main(String[] args) throws Exception {

    Credential credential = OauthUtils.authorize();
    String accessToken = credential.getAccessToken();


    String projectId = "";
    String zone = "asia-northeast3-c";
    ComputeEngineSdkClient ceClient = new ComputeEngineSdkClient(credential);

//    ZoneList zoneList = ceClient.getAvailableZones(projectId);
//    List<Zone> zones = zoneList.getItems();
//    for(Zone zone : zones){
//      System.out.println(zone.toString());
//    }

//    DiskList diskList = ceClient.getDisks(projectId,zone);
//    for(Disk disk : diskList.getItems()){
//      System.out.println(disk.toString());
//    }

//    Operation operation = ceClient.createInstance(projectId,zone);
//
//    System.out.println(
//        operation.toPrettyString()
//    );

//    InstanceList instanceList = ceClient.getInstances(projectId,zone);
//    for(Instance instance : instanceList.getItems()){
//      System.out.println(instance.toPrettyString());
//    }



  }
}
