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

import com.staxrt.tutorial.model.Employee;
import com.staxrt.tutorial.model.Floor;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTestsEmployee {
	
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
	public void testGetAllEmployees() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/employees",
				HttpMethod.GET, entity, String.class);

		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testGetEmployeeByDeskId() {
		int id = 7;
		
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<Employee> response = restTemplate.exchange(getRootUrl() + "/desks/"+id+"/employees",HttpMethod.GET, entity, Employee.class);
		System.out.println(response.getBody());
		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testCreateEmployee() {
		Employee user = new Employee();
		user.setEmployeeName("dinesh");
	
		ResponseEntity<Employee> postResponse = restTemplate.postForEntity(getRootUrl() + "/desk/7/employees", user, Employee.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}


	@Test
	public void testDeleteEmployee() {
		int id = 2;
		Employee user = restTemplate.getForObject(getRootUrl() + "/employee/" + id, Employee.class);
		Assert.assertNotNull(user);

		restTemplate.delete(getRootUrl() + "/floor/" + id);

		try {
			user = restTemplate.getForObject(getRootUrl() + "/floors/" + id, Employee.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

	@Test
	public void testGetNonAssociatedEmployees() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/employees/nonassociations",
				HttpMethod.GET, entity, String.class);

		System.out.println(response.getBody());
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	public void testGetAssociatedEmployees() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/employees/associations",
				HttpMethod.GET, entity, String.class);

		System.out.println(response.getBody());
		Assert.assertNotNull(response.getBody());
	}

}
