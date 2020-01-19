package com.staxrt.tutorial;

import com.staxrt.tutorial.model.Building;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

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
	public void testGetAllBuildings() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/buildings",
				HttpMethod.GET, entity, String.class);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testGetBuildingById() {
		Building user = restTemplate.getForObject(getRootUrl() + "/buildings/1", Building.class);
		System.out.println(user.getBuildingName());
		Assert.assertNotNull(user);
	}

	@Test
	public void testCreateBuilding() {
		Building user = new Building();
		user.setBuildingName("mytri");
		user.setArea("asgard");
	
		ResponseEntity<Building> postResponse = restTemplate.postForEntity(getRootUrl() + "/buildings", user, Building.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateBuilding() {
		int id = 1;
		Building user = restTemplate.getForObject(getRootUrl() + "/buildings/" + id, Building.class);
		user.setBuildingName("mytri");
		user.setArea("asgard");

		restTemplate.put(getRootUrl() + "/buildings/" + id, user);

		Building updatedUser = restTemplate.getForObject(getRootUrl() + "/buildings/" + id, Building.class);
		Assert.assertNotNull(updatedUser);
	}

	@Test
	public void testDeletePost() {
		int id = 2;
		Building user = restTemplate.getForObject(getRootUrl() + "/buildings/" + id, Building.class);
		Assert.assertNotNull(user);

		restTemplate.delete(getRootUrl() + "/buildings/" + id);

		try {
			user = restTemplate.getForObject(getRootUrl() + "/buildings/" + id, Building.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

}
