package com.example.gcp.demo.resource;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.cloudresourcemanager.CloudResourceManager;
import com.google.api.services.cloudresourcemanager.CloudResourceManager.Projects;
import com.google.api.services.cloudresourcemanager.model.ListProjectsResponse;
import com.google.api.services.cloudresourcemanager.model.Project;
import java.io.IOException;
import java.util.List;

public class ResourceManagerSdkClient {

  private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  /** Global instance of the HTTP transport. */
  private HttpTransport httpTransport;

  /**
   * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
   * globally shared instance across your application.
   */
  private FileDataStoreFactory dataStoreFactory;

  private CloudResourceManager resourceManager;
  public ResourceManagerSdkClient(Credential credential) {
    try {
      httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    }catch (Exception e){

    }
    resourceManager = new CloudResourceManager.Builder( httpTransport, JSON_FACTORY, null)
        .setHttpRequestInitializer(credential).build();
  }

  public List<Project> getProjects() throws IOException {
    return resourceManager.projects().list().execute().getProjects();
  }


}
