package rva.integrationTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import rva.models.Odeljenje;


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OdeljenjeControllerIntegrationTests {

	@Autowired
	TestRestTemplate template;
	
	int highestId;
	
	void createHighestId() {
		ResponseEntity<List<Odeljenje>> response=template.exchange("/odeljenje",HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Odeljenje>>() {});
		
		ArrayList<Odeljenje> list=(ArrayList<Odeljenje>) response.getBody();
		for(int i=0;i<list.size();i++) {
			if(highestId<=list.get(i).getId()) {
				highestId=list.get(i).getId()+1;
			}
		}
	}
	
	void getHighestId() {
		createHighestId();
		highestId--;
		
	}
	
	@Test
	@Order(1)
	void testGetAllOdeljenje() {
		ResponseEntity<List<Odeljenje>> response=template.exchange("/odeljenje",HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Odeljenje>>() {});
		
		int statusCode=response.getStatusCode().value();
		List<Odeljenje> odeljenja=response.getBody();
		
		assertEquals(200, statusCode);
		assertTrue(!odeljenja.isEmpty());
	}
	
	@Test
	@Order(2)
	void testGetOdeljenjeById() {
		int id=1;
		
		ResponseEntity<Odeljenje> response=template.exchange("/odeljenje/id/"+id,HttpMethod.GET,null,
				Odeljenje.class);
		
		int statusCode=response.getStatusCode().value();
		
		assertEquals(200,statusCode);
		assertEquals(id,response.getBody().getId());
	}
	
	@Test
	@Order(3)
	void testGetOdeljenjeByNaziv() {
		String naziv="Interno"; 
		
		ResponseEntity<List<Odeljenje>> response=template.exchange("/odeljenje/naziv/"+naziv,HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Odeljenje>>() {});
		
        int statusCode=response.getStatusCode().value();
        List<Odeljenje> odeljenja=response.getBody();
		
		assertEquals(200,statusCode);
		assertNotNull(odeljenja.get(0));
		for(Odeljenje o:odeljenja) {
			assertTrue(o.getNaziv().startsWith(naziv));
		}
		
	}
	
	@Test
	@Order(4)
	void testCreateOdeljenje() {
		Odeljenje odeljenje=new Odeljenje();
		odeljenje.setNaziv("Naziv odeljenje post");
		odeljenje.setLokacija("najjaca lokacija");
		
		HttpEntity<Odeljenje> entity=new HttpEntity<Odeljenje>(odeljenje);
		createHighestId();
		ResponseEntity<Odeljenje> response=template.exchange("/odeljenje", HttpMethod.POST,entity,Odeljenje.class);
		
		int statusCode=response.getStatusCode().value();
		
		assertEquals(201,statusCode);
		assertEquals("/odeljenje/id/"+highestId, response.getHeaders().getLocation().getPath());
		assertEquals(odeljenje.getNaziv(),response.getBody().getNaziv());
		assertEquals(odeljenje.getLokacija(),response.getBody().getLokacija());
	}
	
	@Test
	@Order(5)
	void testUpdateOdeljenje() {
		Odeljenje odeljenje=new Odeljenje();
		odeljenje.setNaziv("Naziv odeljenje put");
		odeljenje.setLokacija("najjaca lokacija");
		
		
		HttpEntity<Odeljenje> entity=new HttpEntity<Odeljenje>(odeljenje);
		getHighestId();
		ResponseEntity<Odeljenje> response=template.exchange("/odeljenje/id/"+ highestId, HttpMethod.PUT,entity,Odeljenje.class);
		
		int statusCode=response.getStatusCode().value();
		
		assertEquals(200,statusCode);
		assertTrue(response.getBody() instanceof Odeljenje);
		assertEquals(odeljenje.getNaziv(),response.getBody().getNaziv());
		assertEquals(odeljenje.getLokacija(),response.getBody().getLokacija());
	}
	
	@Test
	@Order(6)
	void testDeleteOdeljenje() {
		getHighestId();
		ResponseEntity<String> response= template.exchange("/odeljenje/id/"+highestId, HttpMethod.DELETE,null,String.class);
		
		int statusCode =response.getStatusCode().value();
		
		assertEquals(200,statusCode);
		assertTrue(response.getBody().contains("has been successfully deleted."));
	}
	
	@Test
	@Order(7)
	void testGetOdeljenjeByBolnica() {
        int foreignKey=1;
        
        ResponseEntity<List<Odeljenje>> response=template.exchange("/odeljenje/bolnica/"+foreignKey,HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Odeljenje>>() {});
        int statusCode=response.getStatusCode().value();
		
		assertEquals(200,statusCode);
        List<Odeljenje> odeljenja=response.getBody();
        
		assertNotNull(odeljenja.get(0));
		for(Odeljenje o:odeljenja) {
			assertEquals(foreignKey,o.getBolnica().getId());
		}
	}
}
