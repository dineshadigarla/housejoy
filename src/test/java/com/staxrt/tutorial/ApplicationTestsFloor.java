package com.staxrt.tutorial;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.staxrt.tutorial.model.Floor;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTestsFloor {
	
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port+"/api/v1";
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllFloors() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/floors",
				HttpMethod.GET, entity, String.class);

		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testGetFloorById() {
		Floor user = restTemplate.getForObject(getRootUrl() + "/floor/"+ApplicationTestConstants.floorId, Floor.class);
		System.out.println(user.getFloorName());
		Assert.assertNotNull(user);
	}

	@Test
	public void testCreateFloor() {
		Floor user = new Floor();
		user.setFloorName("mytri");
	
		ResponseEntity<Floor> postResponse = restTemplate.postForEntity(getRootUrl() + "/buildings/"+ApplicationTestConstants.buildingId+"/floors", user, Floor.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateFloor() {
		Floor user = restTemplate.getForObject(getRootUrl() + "/floors/" + ApplicationTestConstants.floorId, Floor.class);
		user.setFloorName("mytri");

		restTemplate.put(getRootUrl() + "/buildings/" + ApplicationTestConstants.floorId, user);

		Floor updatedFloor = restTemplate.getForObject(getRootUrl() + "/buildings/" + ApplicationTestConstants.floorId, Floor.class);
		Assert.assertNotNull(updatedFloor);
	}
	
	@Test
	public void testFloorsByBuildingId() {
		
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> user = restTemplate.exchange(getRootUrl() + "/buildings/"+ApplicationTestConstants.buildingId+"/floors/",HttpMethod.GET, entity, String.class);
		System.out.println(user);
		Assert.assertNotNull(user);
	}

	@Test
	public void testDeleteFloor() {
		Floor user = restTemplate.getForObject(getRootUrl() + "/floor/" + ApplicationTestConstants.floorId, Floor.class);
		Assert.assertNotNull(user);

		restTemplate.delete(getRootUrl() + "/floor/" + ApplicationTestConstants.floorId);

		try {
			user = restTemplate.getForObject(getRootUrl() + "/floors/" + ApplicationTestConstants.floorId, Floor.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
	
	@Test
	public void testGetNonAssociatedDesks() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/floors/"+ApplicationTestConstants.floorId+"/nonassociations",
				HttpMethod.GET, entity, String.class);

		System.out.println(response.getBody());
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	public void testGetAssociatedDesks() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/floors/"+ApplicationTestConstants.floorId+"/associations",
				HttpMethod.GET, entity, String.class);

		System.out.println(response.getBody());
		Assert.assertNotNull(response.getBody());
	}



}
