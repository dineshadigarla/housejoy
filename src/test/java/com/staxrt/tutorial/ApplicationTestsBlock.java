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
		Block block = restTemplate.getForObject(getRootUrl() + "/blocks/1", Block.class);
		System.out.println(block.getBlockName());
		Assert.assertNotNull(block);
	}

	@Test
	public void testCreateBlock() {
		Block block = new Block();
		block.setBlockName("mytri");
	
		ResponseEntity<Floor> postResponse = restTemplate.postForEntity(getRootUrl() + "/floors/1/blocks", block, Floor.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateBlock() {
		int id = 1;
		Block block = restTemplate.getForObject(getRootUrl() + "/blocks/" + id, Block.class);
		block.setBlockName("mytri");

		restTemplate.put(getRootUrl() + "/blocks/" + id, block);

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
	public void testDeleteBlock() {
		int id = 2;
		Floor user = restTemplate.getForObject(getRootUrl() + "/blocks/" + id, Floor.class);
		Assert.assertNotNull(user);

		restTemplate.delete(getRootUrl() + "/blocks/" + id);

		try {
			user = restTemplate.getForObject(getRootUrl() + "/blocks/" + id, Floor.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}


}
