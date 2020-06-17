package com.example.gcp.demo.auth;

import com.google.api.services.cloudresourcemanager.CloudResourceManagerScopes;
import com.google.api.services.compute.ComputeScopes;
import java.util.HashSet;
import java.util.Set;

public class GcpScopes {

  public static String[] getAllScopes(){
    Set<String> scopes = new HashSet<>();
    scopes.addAll(ComputeScopes.all());
    scopes.addAll(CloudResourceManagerScopes.all());
    return scopes.stream().toArray(String[]::new);
  }
}
