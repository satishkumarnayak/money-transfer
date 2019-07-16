package com.instanceofcake.revolut.rest.api;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletInputStream;
import javax.servlet.http.*;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.mockito.Mockito;

import com.instanceofcake.revolut.rest.router.ApiRouterServlet;
import com.instanceofcake.revolut.rest.util.Util;

public class TestMyServlet extends Mockito {

    @Test
    public void testServlet() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);    

        String str = "{\r\n" + 
          		"    \r\n" + 
          		"    \"fromAccountId\":222,\r\n" + 
          		"    \"toAccountId\":222,\r\n" + 
          		"    \"amount\":1\r\n" + 
          		"}";
   //     InputStream targetStream = new ByteArrayInputStream(str.getBytes());
        
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        ServletInputStream servletInputStream=new ServletInputStream(){
            public int read() throws IOException {
              return byteArrayInputStream.read();
            }
          };
     //   InputStream inputStream = IOUtils.toInputStream(str);
		//  when(request.getRequestURI()).thenReturn("/accounts");
		  when(request.getRequestURI()).thenReturn("/transfers");
		  when(request.getInputStream()).thenReturn(servletInputStream);
		    
      
       
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

  //      new ApiRouterServlet().doGet(request, response);
        new ApiRouterServlet().doPost(request, response);

     //   verify(request, atLeast(1)).getParameter("username"); // only if you want to verify username was called...
        writer.flush(); // it may not have been flushed yet...
        System.out.println(stringWriter);
  //      assertTrue(stringWriter.toString().contains("My expected string"));
    }
    
    
}