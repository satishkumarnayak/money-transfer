package com.instanceofcake.revolut.rest.api;

import static org.junit.Assert.*;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class JUnitBeforeAfterClassJettyTest {
	private static Server server;
	private static URI serverUri;
	private HttpURLConnection http;

	@BeforeClass
	public static void startJetty() throws Exception {
		// Create Server
		server = new Server();
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(0); // auto-bind to available port
		server.addConnector(connector);

		ServletContextHandler context = new ServletContextHandler();
		ServletHolder defaultServ = new ServletHolder("default", DefaultServlet.class);
		defaultServ.setInitParameter("resourceBase", System.getProperty("user.dir"));
		defaultServ.setInitParameter("dirAllowed", "true");
		context.addServlet(defaultServ, "/");
		server.setHandler(context);

		// Start Server
		server.start();

		// Determine Base URI for Server
		String host = connector.getHost();
		if (host == null) {
			host = "localhost";
		}
		int port = connector.getLocalPort();
		serverUri = new URI(String.format("http://%s:%d/", host, port));
	}

	@AfterClass
	public static void stopJetty() {
		try {
			server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGet() throws Exception {
		http = (HttpURLConnection) serverUri.resolve("/accounts").toURL().openConnection();
		http.connect();
		assertEquals(200, http.getResponseCode());

		assertEquals("OK", http.getResponseMessage());
	}
}