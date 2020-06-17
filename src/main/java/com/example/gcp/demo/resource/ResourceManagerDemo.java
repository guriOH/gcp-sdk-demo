package com.example.gcp.demo.resource;

import com.example.gcp.demo.auth.OauthUtils;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.cloudresourcemanager.model.Project;
import java.util.List;

public class ResourceManagerDemo {

  public static void main(String[] args) throws Exception {
    Credential credential = OauthUtils.authorize();
    String accessToken = credential.getAccessToken();


    String projectId = "";
    ResourceManagerSdkClient rmSdkClient = new ResourceManagerSdkClient(credential);

    List<Project> projectList = rmSdkClient.getProjects();

    for(Project project : projectList){
      System.out.println(project.toPrettyString());
    }
  }
}
