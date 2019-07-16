package com.instanceofcake.revolut.rest.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import com.instanceofcake.revolut.rest.util.Util;

public class WebappTest {

  private Server server;

  @Before
  public void setUp() throws Exception {
    server = new Server(8080);
    server.setStopAtShutdown(true);
    WebAppContext webAppContext = new WebAppContext();
    webAppContext.setContextPath("/");
    webAppContext.setResourceBase("src/main/webapp");
    webAppContext.setClassLoader(getClass().getClassLoader());
    server.addHandler(webAppContext);
    server.start();
  }

  @After
  public void tearDown() throws Exception {
    server.stop();
  }

  @Test
  public void testWebappDeploy() {
    HttpURLConnection connection = null;
    try {
      URL url = new URL("http://localhost:8080/transfers/221");
      
      connection = (HttpURLConnection) url.openConnection();
      if (connection.getResponseCode() != 200) {
        throw new RuntimeException("Failed! HTTP Error Code: " + connection.getResponseCode());
      }
      BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
      String str;
      StringBuilder strB = new StringBuilder();
      while ((str = br.readLine()) != null) {
        System.out.println(str);
        strB.append(str);
        
      }
  //    System.out.println(strB.toString());
      System.out.println(Util.GSON.toJson(strB.toString()));
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }

}