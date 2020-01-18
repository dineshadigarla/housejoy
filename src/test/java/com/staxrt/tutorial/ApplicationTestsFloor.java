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

import com.staxrt.tutorial.model.Floor;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTestsFloor {
	
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
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
		Floor user = restTemplate.getForObject(getRootUrl() + "/floor/1", Floor.class);
		System.out.println(user.getFloorName());
		Assert.assertNotNull(user);
	}

	@Test
	public void testCreateFloor() {
		Floor user = new Floor();
		user.setFloorName("mytri");
	
		ResponseEntity<Floor> postResponse = restTemplate.postForEntity(getRootUrl() + "/buildings/1", user, Floor.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateFloor() {
		int id = 1;
		Floor user = restTemplate.getForObject(getRootUrl() + "/floors/" + id, Floor.class);
		user.setFloorName("mytri");

		restTemplate.put(getRootUrl() + "/buildings/" + id, user);

		Floor updatedFloor = restTemplate.getForObject(getRootUrl() + "/buildings/" + id, Floor.class);
		Assert.assertNotNull(updatedFloor);
	}
	
	@Test
	public void testFloorsByBuildingId() {
		int id=1;
		Floor user = restTemplate.getForObject(getRootUrl() + "buildings/"+id+"/floors/", Floor.class);
		System.out.println(user.getFloorName());
		Assert.assertNotNull(user);
	}

	@Test
	public void testDeleteFloor() {
		int id = 2;
		Floor user = restTemplate.getForObject(getRootUrl() + "/floor/" + id, Floor.class);
		Assert.assertNotNull(user);

		restTemplate.delete(getRootUrl() + "/floor/" + id);

		try {
			user = restTemplate.getForObject(getRootUrl() + "/floors/" + id, Floor.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}


}