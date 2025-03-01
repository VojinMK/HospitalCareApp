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


import rva.models.Pacijent;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PacijentControllerIntegrationTests {

	@Autowired
	TestRestTemplate template;
	
	int highestId;
	
	void createHighestId() {
		ResponseEntity<List<Pacijent>> response=template.exchange("/pacijent",HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Pacijent>>() {});
		
		ArrayList<Pacijent> list=(ArrayList<Pacijent>) response.getBody();
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
	void testGetAllPacijent() {
		ResponseEntity<List<Pacijent>> response=template.exchange("/pacijent",HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Pacijent>>() {});
		
		int statusCode=response.getStatusCode().value();
		List<Pacijent> pacijenti=response.getBody();
		
		assertEquals(200, statusCode);
		assertTrue(!pacijenti.isEmpty());
	}
	
	@Test
	@Order(2)
	void testGetPacijentById() {
		int id=1;
		
		ResponseEntity<Pacijent> response=template.exchange("/pacijent/id/"+id,HttpMethod.GET,null,
				Pacijent.class);
		
		int statusCode=response.getStatusCode().value();
		
		assertEquals(200,statusCode);
		assertEquals(id,response.getBody().getId());
	}
	
	@Test
	@Order(3)
	void testGetPacijentByZdrOsiguranje() {
		boolean zdrOsiguranje=true; 
		
		ResponseEntity<List<Pacijent>> response=template.exchange("/pacijent/zdrOsiguranje/"+zdrOsiguranje,HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Pacijent>>() {});
		
        int statusCode=response.getStatusCode().value();
        List<Pacijent> pacijenti=response.getBody();
		
		assertEquals(200,statusCode);
		assertNotNull(pacijenti.get(0));
		for(Pacijent p:pacijenti) {
			assertTrue(p.isZdrOsiguranje());
		}
		
	}
	
	@Test
	@Order(4)
	void testCreatePacijent() {
		Pacijent pacijent=new Pacijent();
		pacijent.setIme("ime pacijenta post");
		pacijent.setPrezime("najjace prezime");
		
		HttpEntity<Pacijent> entity=new HttpEntity<Pacijent>(pacijent);
		createHighestId();
		ResponseEntity<Pacijent> response=template.exchange("/pacijent", HttpMethod.POST,entity,Pacijent.class);
		
		int statusCode=response.getStatusCode().value();
		
		assertEquals(201,statusCode);
		assertEquals("/pacijent/id/"+highestId, response.getHeaders().getLocation().getPath());
		assertEquals(pacijent.getIme(),response.getBody().getIme());
		assertEquals(pacijent.getPrezime(),response.getBody().getPrezime());
	}
	
	@Test
	@Order(5)
	void testUpdatePacijent() {
		Pacijent pacijent=new Pacijent();
		pacijent.setIme("ime pacijenta post");
		pacijent.setPrezime("najjace prezime");
		
		
		HttpEntity<Pacijent> entity=new HttpEntity<Pacijent>(pacijent);
		getHighestId();
		ResponseEntity<Pacijent> response=template.exchange("/pacijent/id/"+ highestId, HttpMethod.PUT,entity,Pacijent.class);
		
		int statusCode=response.getStatusCode().value();
		
		assertEquals(200,statusCode);
		assertTrue(response.getBody() instanceof Pacijent);
		assertEquals(pacijent.getIme(),response.getBody().getIme());
		assertEquals(pacijent.getPrezime(),response.getBody().getPrezime());
	}
	
	@Test
	@Order(6)
	void testDeletePacijent() {
		getHighestId();
		ResponseEntity<String> response= template.exchange("/pacijent/id/"+highestId, HttpMethod.DELETE,null,String.class);
		
		int statusCode =response.getStatusCode().value();
		
		assertEquals(200,statusCode);
		assertTrue(response.getBody().contains("has been successfully deleted."));
	}
	
	@Test
	@Order(7)
	void testGetPacijentiByOdeljenje() {
        int foreignKey=1; 
        
        ResponseEntity<List<Pacijent>> response=template.exchange("/pacijent/odeljenje/"+foreignKey,HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Pacijent>>() {});
        int statusCode=response.getStatusCode().value();
		
		assertEquals(200,statusCode);
        List<Pacijent> pacijenti=response.getBody();
        
		assertNotNull(pacijenti.get(0));
		for(Pacijent p:pacijenti) {
			assertEquals(foreignKey,p.getDijagnoza().getId()); 
		}
	}
	
	@Test
	@Order(8)
	void testGetPacijentiByDijagnoza() {
        int foreignKey=1; 
        
        ResponseEntity<List<Pacijent>> response=template.exchange("/pacijent/dijagnoza/"+foreignKey,HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Pacijent>>() {});
        int statusCode=response.getStatusCode().value();
		
		assertEquals(200,statusCode);
        List<Pacijent> pacijenti=response.getBody();
        
		assertNotNull(pacijenti.get(0));
		for(Pacijent p:pacijenti) {
			assertEquals(foreignKey,p.getDijagnoza().getId());
		}
	}

}
