package com.example.gcp.demo.oauth;

import com.example.gcp.demo.ComputeEngineSample;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.compute.ComputeScopes;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class OauthUtils {

  private static java.io.File DATA_STORE_DIR =
      new java.io.File(System.getProperty("user.home"), ".store/compute_engine_sample");

  /**
   * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
   * globally shared instance across your application.
   */
  private static FileDataStoreFactory dataStoreFactory;

  /** Global instance of the HTTP transport. */
  private static HttpTransport httpTransport;

  /** Global instance of the JSON factory. */
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  /** OAuth 2.0 scopes */
  private static final List<String> SCOPES = Arrays.asList(ComputeScopes.COMPUTE_READONLY);


  public OauthUtils() {
  }

  /** Authorizes the installed application to access user's protected data. */
  public static Credential authorize() throws Exception {
    try {
      httpTransport = GoogleNetHttpTransport.newTrustedTransport();
      dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
    }catch (Exception e){

    }
    // initialize client secrets object
    GoogleClientSecrets clientSecrets;
    // load client secrets
    clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(
        ComputeEngineSample.class.getResourceAsStream("/client_secrets.json")));
    if (clientSecrets.getDetails().getClientId().startsWith("Enter")
        || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
      System.out.println("Enter Client ID and Secret from https://code.google.com/apis/console/ "
          + "into compute-engine-cmdline-sample/src/main/resources/client_secrets.json");
      System.exit(1);
    }

    System.out.println(clientSecrets.getWeb().toString());

    List<String> SCOPES = Arrays.asList(ComputeScopes.COMPUTE_READONLY);

    // set up authorization code flow
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        httpTransport, JSON_FACTORY, clientSecrets, SCOPES).setDataStoreFactory(dataStoreFactory)
        .build();

    LocalServerReceiver receiver = new LocalServerReceiver.Builder()
        .setHost("localhost")
        .setPort(8080)
        .build();

    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
  }
}
