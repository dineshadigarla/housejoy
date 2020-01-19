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
		Building building = restTemplate.getForObject(getRootUrl() + "/buildings/"+ApplicationTestConstants.buildingId, Building.class);
		System.out.println(building.getBuildingName());
		Assert.assertNotNull(building);
	}

	@Test
	public void testCreateBuilding() {
		Building building = new Building();
		building.setBuildingName("mytri");
		building.setArea("asgard");
	
		ResponseEntity<Building> postResponse = restTemplate.postForEntity(getRootUrl() + "/buildings", building, Building.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateBuilding() {
		Building user = restTemplate.getForObject(getRootUrl() + "/buildings/" + ApplicationTestConstants.buildingId, Building.class);
		user.setBuildingName("mytri");
		user.setArea("asgard");

		restTemplate.put(getRootUrl() + "/buildings/" + ApplicationTestConstants.buildingId, user);

		Building updatedUser = restTemplate.getForObject(getRootUrl() + "/buildings/" + ApplicationTestConstants.buildingId, Building.class);
		Assert.assertNotNull(updatedUser);
	}

	@Test
	public void testDeletePost() {
		int id = 2;
		Building user = restTemplate.getForObject(getRootUrl() + "/buildings/" + ApplicationTestConstants.buildingId, Building.class);
		Assert.assertNotNull(user);

		restTemplate.delete(getRootUrl() + "/buildings/" + ApplicationTestConstants.buildingId);

		try {
			user = restTemplate.getForObject(getRootUrl() + "/buildings/" + ApplicationTestConstants.buildingId, Building.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

	@Test
	public void testAssociatedDesks() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/buildings/"+ApplicationTestConstants.buildingId+"/associations",
				HttpMethod.GET, entity, String.class);
		System.out.println("Associated Blocks "+response.getBody());
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	public void testNonAssociatedDesks() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/buildings/"+ApplicationTestConstants.buildingId+"/nonassociations",
				HttpMethod.GET, entity, String.class);
		System.out.println("Non Associated Blocks are "+response.getBody());
		Assert.assertNotNull(response.getBody());
	}
}
