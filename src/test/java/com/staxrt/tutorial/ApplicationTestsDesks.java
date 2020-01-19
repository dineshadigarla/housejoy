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

import com.staxrt.tutorial.model.Block;
import com.staxrt.tutorial.model.Desks;
import com.staxrt.tutorial.model.Floor;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTestsDesks {
	
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
	public void testGetAllDesks() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/desks",
				HttpMethod.GET, entity, String.class);

		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testGetDeskById() {
		Desks desk = restTemplate.getForObject(getRootUrl() + "/desks/1", Desks.class);
		System.out.println(desk.getDeskName());
		Assert.assertNotNull(desk);
	}

	@Test
	public void testCreateDesk() {
		Desks desk = new Desks();
		desk.setDeskName("mytri");
	
		ResponseEntity<Desks> postResponse = restTemplate.postForEntity(getRootUrl() + "/blocks/1/desks", desk, Desks.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateDesk() {
		int id = 1;
		Desks desk = restTemplate.getForObject(getRootUrl() + "/desks/" + id, Desks.class);
		desk.setDeskName("wakanda");

		restTemplate.put(getRootUrl() + "/desks/" + id, desk);

		Block updatedBlock = restTemplate.getForObject(getRootUrl() + "/blocks/" + id, Block.class);
		Assert.assertNotNull(updatedBlock);
	}
	
	@Test
	public void testFloorsByBuildingId() {
		int id=1;
		Floor user = restTemplate.getForObject(getRootUrl() + "buildings/"+id+"/floors/", Floor.class);
		System.out.println(user.getFloorName());
		Assert.assertNotNull(user);
	}

	@Test
	public void testDeleteDesk() {
		int id = 2;
		Desks desk = restTemplate.getForObject(getRootUrl() + "/desks/" + id, Desks.class);
		Assert.assertNotNull(desk);

		restTemplate.delete(getRootUrl() + "/desks/" + id);

		try {
			desk = restTemplate.getForObject(getRootUrl() + "/desks/" + id, Desks.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

	@Test
	public void testGetNonAssociatedDesks() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/desks/nonassociations",
				HttpMethod.GET, entity, String.class);

		System.out.println(response.getBody());
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	public void testGetAssociatedDesks() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/desks/associations",
				HttpMethod.GET, entity, String.class);

		System.out.println(response.getBody());
		Assert.assertNotNull(response.getBody());
	}

}
