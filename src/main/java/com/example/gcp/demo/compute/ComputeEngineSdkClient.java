package com.example.gcp.demo.compute;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.compute.Compute;
import com.google.api.services.compute.Compute.Projects;
import com.google.api.services.compute.model.ZoneList;
import java.io.IOException;

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



}
