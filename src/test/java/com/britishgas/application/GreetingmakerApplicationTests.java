package com.britishgas.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.io.IOException;


import javax.servlet.ServletException;

import org.apache.catalina.connector.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.britishgas.configuration.SecurityFilter;
import com.britishgas.controller.GreetingController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GreetingmakerApplicationTests {

    private MockMvc mockMvc;

    @InjectMocks
    private GreetingController controller;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(controller).build();
    }

	@Test
	public void contextLoads() {
	}

	@Test
    public void testGreetingRettunsHelloWorld() throws Exception  {
        mockMvc.perform(get("/greetings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.greetingText").value("Hello World"));
    }

    @Test
    public void testInternalGreetingReturnsHelloManagement() throws Exception  {
        mockMvc.perform(get("/internal-greetings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.greetingText").value("Hello Management"));
    }

    @Test
    public void testInternalGreetingFailsOnPort80() throws IOException, ServletException {
        SecurityFilter securityFilter = new SecurityFilter();

        MockFilterChain mockFilterChain = new MockFilterChain();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        mockHttpServletRequest.setRequestURI("/internal-greeting");
        mockHttpServletRequest.setLocalPort(80);
        securityFilter.doFilter(mockHttpServletRequest,mockHttpServletResponse,mockFilterChain);
        assertThat (mockHttpServletResponse.getStatus()).isEqualTo(Response.SC_FORBIDDEN);
    }

    @Test
    public void testInternalGreetingWorksOnPort8080() throws IOException, ServletException {
        SecurityFilter securityFilter = new SecurityFilter();

        MockFilterChain mockFilterChain = new MockFilterChain();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        mockHttpServletRequest.setRequestURI("/internal-greeting");
        mockHttpServletRequest.setLocalPort(8080);
        securityFilter.doFilter(mockHttpServletRequest,mockHttpServletResponse,mockFilterChain);
        assertThat (mockHttpServletResponse.getStatus()).isEqualTo(Response.SC_OK);
    }

    @Test
    public void testInternalGreetingFailureOnPort8080() throws IOException, ServletException {
        SecurityFilter securityFilter = new SecurityFilter();

        MockFilterChain mockFilterChain = new MockFilterChain();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        mockHttpServletRequest.setRequestURI("/greeting");
        mockHttpServletRequest.setLocalPort(8080);
        securityFilter.doFilter(mockHttpServletRequest,mockHttpServletResponse,mockFilterChain);
        assertThat (mockHttpServletResponse.getStatus()).isEqualTo(Response.SC_FORBIDDEN);
    }

    @Test
    public void testInternalGreetingSucceedsOnPort80() throws IOException, ServletException {
        SecurityFilter securityFilter = new SecurityFilter();

        MockFilterChain mockFilterChain = new MockFilterChain();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        mockHttpServletRequest.setRequestURI("/greeting");
        mockHttpServletRequest.setLocalPort(80);
        securityFilter.doFilter(mockHttpServletRequest,mockHttpServletResponse,mockFilterChain);
        assertThat (mockHttpServletResponse.getStatus()).isEqualTo(Response.SC_OK);
    }
}