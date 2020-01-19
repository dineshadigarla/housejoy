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
		Desks desk = restTemplate.getForObject(getRootUrl() + "/desks/"+ApplicationTestConstants.deskId, Desks.class);
		System.out.println(desk.getDeskName());
		Assert.assertNotNull(desk);
	}

	@Test
	public void testCreateDesk() {
		Desks desk = new Desks();
		desk.setDeskName("mytri");
	
		ResponseEntity<Desks> postResponse = restTemplate.postForEntity(getRootUrl() + "/blocks/"+ApplicationTestConstants.blockId+"/desks", desk, Desks.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateDesk() {
		Desks desk = restTemplate.getForObject(getRootUrl() + "/desks/" + ApplicationTestConstants.deskId, Desks.class);
		desk.setDeskName("wakanda");

		restTemplate.put(getRootUrl() + "/desks/" + ApplicationTestConstants.deskId, desk);

		Desks updatedDesk = restTemplate.getForObject(getRootUrl() + "/desks/" + ApplicationTestConstants.deskId, Desks.class);
		Assert.assertNotNull(updatedDesk);
	}
	
	@Test
	public void testDesksByBlockId() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> user = restTemplate.exchange(getRootUrl() + "/blocks/"+ApplicationTestConstants.blockId+"/desks/", HttpMethod.GET, entity,String.class);
		System.out.println(user);
		Assert.assertNotNull(user);
	}

	@Test
	public void testDeleteDesk() {
		Desks desk = restTemplate.getForObject(getRootUrl() + "/desks/" + ApplicationTestConstants.deskId, Desks.class);
		Assert.assertNotNull(desk);

		restTemplate.delete(getRootUrl() + "/desks/" + ApplicationTestConstants.deskId);

		try {
			desk = restTemplate.getForObject(getRootUrl() + "/desks/" + ApplicationTestConstants.deskId, Desks.class);
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
