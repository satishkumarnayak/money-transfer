package com.instanceofcake.revolut.rest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.nio.charset.StandardCharsets;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dalesbred.Database;
import org.dalesbred.annotation.SQL;
import org.junit.Before;

import com.instanceofcake.revolut.rest.database.DB;

import com.instanceofcake.revolut.rest.router.ApiRouterServlet;
import com.instanceofcake.revolut.rest.util.Util;

public class BaseApiTest {

	@Before
	public void setUp() throws Exception {
		DB.db = Database.forUrlAndCredentials("jdbc:hsqldb:mem:memdb;sql.syntax_mys=true;sql.enforce_size=false", "SA","");

		String[] instructions = Util.readInputStream(BaseApiTest.class.getResourceAsStream("/test-db-setup.sql")) .split(";");
		for (@SQL String instruction : instructions) {
			DB.db.update(instruction);
		}

	}

	public String apiServletRouter(String Uri, String httpMethod, String jsonBodyString) {

		StringWriter stringWriter = new StringWriter();

		try {
			HttpServletRequest request = mock(HttpServletRequest.class);
			HttpServletResponse response = mock(HttpServletResponse.class);

			when(request.getRequestURI()).thenReturn(Uri);

			PrintWriter writer = new PrintWriter(stringWriter);
			when(response.getWriter()).thenReturn(writer);
			if (httpMethod.equals("GET")) {
				new ApiRouterServlet().doGet(request, response);
			} else if (httpMethod.equals("POST")) {
				ServletInputStream servletInputStream = toServletInputStream(jsonBodyString);
				when(request.getInputStream()).thenReturn(servletInputStream);
				new ApiRouterServlet().doPost(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(stringWriter);
		return stringWriter.toString();
	}

	private ServletInputStream toServletInputStream(String jsonBodyString) {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				jsonBodyString.getBytes(StandardCharsets.UTF_8));
		ServletInputStream servletInputStream = new ServletInputStream() {
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}
		};
		return servletInputStream;
	}

}
