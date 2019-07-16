package com.instanceofcake.revolut.rest.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.instanceofcake.revolut.rest.BaseApiTest;
import com.instanceofcake.revolut.rest.util.Response;
import com.instanceofcake.revolut.rest.util.Util;

public class ApiRouterServletTest extends BaseApiTest {
	
	@Test
	public void testInvalidUri() {

		String Uri;
		String httpMethod;
		String jsonBodyString;

		Uri = "/invaliduri";
		httpMethod = "GET";
		jsonBodyString = "";
		String jsonString = apiServletRouter(Uri, httpMethod, jsonBodyString);
		Response actualResponse = Util.GSON.fromJson(jsonString, Response.class);

		assertNotNull(actualResponse);

		assertEquals(405, actualResponse.getStatusCode());
		assertEquals("Method Not Allowed", actualResponse.getMessage());
		assertEquals("Invalid Resource Requested", actualResponse.getDescription());
	}

}
