package com.example.gcp.demo.compute;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.compute.Compute;
import com.google.api.services.compute.model.AttachedDisk;
import com.google.api.services.compute.model.DiskList;
import com.google.api.services.compute.model.Instance;
import com.google.api.services.compute.model.InstanceList;
import com.google.api.services.compute.model.NetworkInterface;
import com.google.api.services.compute.model.Operation;
import com.google.api.services.compute.model.ZoneList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComputeEngineSdkClient {
  private
  java.io.File DATA_STORE_DIR =
      new java.io.File(System.getProperty("user.home"), ".store/compute_engine_sample");

  private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  /** Global instance of the HTTP transport. */
  private HttpTransport httpTransport;

  /**
   * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
   * globally shared instance across your application.
   */
  private FileDataStoreFactory dataStoreFactory;

  private final String APPLICATION_NAME = "gcp_sdk_demo";

  private Compute compute;
  public ComputeEngineSdkClient(Credential credential) {
    try {
      httpTransport = GoogleNetHttpTransport.newTrustedTransport();
      dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
    }catch (Exception e){

    }
    compute = new Compute.Builder(
        httpTransport, JSON_FACTORY, null).setApplicationName(APPLICATION_NAME)
        .setHttpRequestInitializer(credential).build();
  }

  public ZoneList getAvailableZones(String projectId) throws IOException {
    return compute.zones().list(projectId).execute();
  }

  public DiskList getDisks(String projectId, String zone) throws IOException{
    return compute.disks().list(projectId,zone).execute();
  }

  public Operation createInstance(String projectId, String zone) throws IOException {

    List<AttachedDisk> attachedDiskList = new ArrayList<>();
    AttachedDisk disk = new AttachedDisk();
    disk.setBoot(true);
    disk.setSource("projects/project-id/zones/znoe/disks/diskname");
    attachedDiskList.add(disk);

    List<NetworkInterface> netList = new ArrayList<>();
    NetworkInterface net = new NetworkInterface();
    net.setNetwork("global/networks/default");
    netList.add(net);

    Instance instance = new Instance();
    instance.setName("sample-server");
    instance.setMachineType("zones/zone/machineTypes/n1-standard-1");
    instance.setDisks(attachedDiskList);
    instance.setNetworkInterfaces(netList);


    return compute.instances().insert(projectId,zone,instance).execute();
  }

  public InstanceList getInstances(String projectId, String zone) throws IOException {
    return compute.instances().list(projectId, zone).execute();
  }

  public Instance getInstance(String projectId, String zone, String instanceId) throws IOException {
    return compute.instances().get(projectId, zone, instanceId).execute();
  }

  public void deleteInstanceAll(String projectId, String zone)throws IOException {
    InstanceList instanceList = this.getInstances(projectId, zone);

    for(Instance instance : instanceList.getItems()){
      Operation op = this.deleteInstance(projectId,zone,instance.getId().toString());
      System.out.println(op.toPrettyString());
    }

  }

  public Operation deleteInstance(String projectId, String zone, String instanceId)throws IOException {
    return compute.instances().delete(projectId,zone,instanceId).execute();
  }

  public Operation instanceStart(String projectId, String zone, String instanceId)throws IOException {
    return compute.instances().start(projectId, zone, instanceId).execute();
  }

  public Operation instanceStop(String projectId, String zone, String instanceId)throws IOException {
    return compute.instances().stop(projectId, zone, instanceId).execute();
  }



}
