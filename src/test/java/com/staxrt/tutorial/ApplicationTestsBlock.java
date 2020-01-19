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

import com.staxrt.tutorial.model.Block;
import com.staxrt.tutorial.model.Floor;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTestsBlock {
	
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
	public void testGetAllBlocks() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/blocks",
				HttpMethod.GET, entity, String.class);

		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testGetBlockById() {
		Block block = restTemplate.getForObject(getRootUrl() + "/blocks/"+ApplicationTestConstants.blockId, Block.class);
		System.out.println(block.getBlockName());
		Assert.assertNotNull(block);
	}

	@Test
	public void testCreateBlock() {
		Block block = new Block();
		block.setBlockName("mytri");
	
		ResponseEntity<Floor> postResponse = restTemplate.postForEntity(getRootUrl() + "floors/"+ApplicationTestConstants.floorId+"/blocks", block, Floor.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateBlock() {
		int id = 1;
		Block block = restTemplate.getForObject(getRootUrl() + "/blocks/" + ApplicationTestConstants.blockId, Block.class);
		block.setBlockName("mytri");

		restTemplate.put(getRootUrl() + "/blocks/" + ApplicationTestConstants.blockId, block);

		Block updatedBlock = restTemplate.getForObject(getRootUrl() + "/blocks/" + ApplicationTestConstants.blockId, Block.class);
		Assert.assertNotNull(updatedBlock);
	}
	
	@Test
	public void testBlocksByFloorId() {
		Block user = restTemplate.getForObject(getRootUrl() + "floors/"+ApplicationTestConstants.floorId+"/blocks/", Block.class);
		System.out.println(user.getBlockName());
		Assert.assertNotNull(user);
	}

	@Test
	public void testDeleteBlock() {
		Block user = restTemplate.getForObject(getRootUrl() + "/blocks/" + ApplicationTestConstants.blockId, Block.class);
		Assert.assertNotNull(user);

		restTemplate.delete(getRootUrl() + "/blocks/" + ApplicationTestConstants.blockId);

		try {
			user = restTemplate.getForObject(getRootUrl() + "/blocks/" + ApplicationTestConstants.blockId, Block.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
	
	@Test
	public void testGetAssignedDesksForBlocks() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/blocks/"+ApplicationTestConstants.blockId+"/associations",
				HttpMethod.GET, entity, String.class);
        System.out.println(response.getBody());
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	public void testGetUnAssignedDesksForBlocks() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/blocks/"+ApplicationTestConstants.blockId+"/nonassociations",
				HttpMethod.GET, entity, String.class);
        System.out.println(response.getBody());
		Assert.assertNotNull(response.getBody());
	}


}

