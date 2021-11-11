package com.esiee.careandpark.parking.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import com.esiee.careandpark.parking.service.ParkingServiceForClient;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class ParkingControllerTest {
    
	@Autowired
    private MockMvc mockMvc;

	@Autowired
    private ParkingServiceForClient parkingService;


    @Test
	void contextLoads() {

	}
	
    @Test
    public void testGetParking() throws Exception {
        mockMvc.perform(get("/parkings"))
            .andExpect(status().isOk());
	}
	
	@Test
    public void testPostParkingCreate() throws Exception {
        mockMvc.perform(post("/parkings")
		.contentType(MediaType.APPLICATION_JSON)
        .content("{\"nom\": \"Parking1\", \"adresse\":\"addresse1\"}"))
		.andExpect(status().isCreated())
		//.andExpect(header().exists("Location"))
		//.andExpect(header().string("Location", Matchers.containsString("/parking/")));
		//.andExpect(jsonPath("$.Id").isNotEmpty())
		;
        
	}
	
	@Test
    public void testPostParkingHeader() throws Exception {
        

		MvcResult result = mockMvc.perform(post("/parkings")
		.contentType(MediaType.APPLICATION_JSON)
        .content("{\"nom\": \"Parking2\", \"adresse\":\"addresse2\"}")).andReturn();
	

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		/**
		System.out.println(response.getHeader(HttpHeaders.LOCATION));

		assertEquals("http://localhost/parkings/2",
				response.getHeader(HttpHeaders.LOCATION));
		*/
		}
		
}
