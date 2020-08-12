package com.mastercard.demo.connectedCities.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class ConnectedCitiesControllerTests {
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testConnectedCitiesFlow() throws Exception {
		MvcResult firstResult = mockMvc
				.perform(get("/connected").queryParam("origin", "Boston").queryParam("destination", "Newark"))
				.andExpect(status().isOk()).andReturn();
		assertEquals("yes", firstResult.getResponse().getContentAsString());

		MvcResult secondResult = mockMvc
				.perform(get("/connected").queryParam("origin", "Boston").queryParam("destination", "Philadelphia"))
				.andExpect(status().isOk()).andReturn();
		assertEquals("yes", secondResult.getResponse().getContentAsString());
	}

	@Test
	public void testNotConnectedCitiesFlow() throws Exception {
		MvcResult result = mockMvc
				.perform(get("/connected").queryParam("origin", "Philadelphia").queryParam("destination", "Albany"))
				.andExpect(status().isOk()).andReturn();
		assertEquals("no", result.getResponse().getContentAsString());
	}
	
	@Test
	public void testWithInvalidCities() throws Exception {
		MvcResult result = mockMvc
				.perform(get("/connected").queryParam("origin", "Philadelphia").queryParam("destination", "Chicago"))
				.andExpect(status().isOk()).andReturn();
		assertEquals("no", result.getResponse().getContentAsString());
	}


}
