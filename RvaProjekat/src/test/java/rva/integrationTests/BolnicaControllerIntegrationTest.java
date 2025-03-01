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

import rva.models.Bolnica;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BolnicaControllerIntegrationTest {

	@Autowired
	TestRestTemplate template;
	
	int highestId;
	
	void createHighestId() {
		ResponseEntity<List<Bolnica>> response=template.exchange("/bolnica",HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Bolnica>>() {});
		
		ArrayList<Bolnica> list=(ArrayList<Bolnica>) response.getBody();
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
	void testGetAllBolnica() {
		ResponseEntity<List<Bolnica>> response=template.exchange("/bolnica",HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Bolnica>>() {});
		
		int statusCode=response.getStatusCode().value();
		List<Bolnica> bolnice=response.getBody();
		
		assertEquals(200, statusCode);
		assertTrue(!bolnice.isEmpty());
	}
	
	@Test
	@Order(2)
	void testGetBolnicaById() {
		int id=1;
		
		ResponseEntity<Bolnica> response=template.exchange("/bolnica/id/"+id,HttpMethod.GET,null,
				Bolnica.class);
		
		int statusCode=response.getStatusCode().value();
		
		assertEquals(200,statusCode);
		assertEquals(id,response.getBody().getId());
	}
	
	@Test
	@Order(3)
	void testGetBolnicaByBudzet() {
		double budzet=150000;
		
		ResponseEntity<List<Bolnica>> response=template.exchange("/bolnica/budzet/"+budzet,HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Bolnica>>() {});
		
        int statusCode=response.getStatusCode().value();
        List<Bolnica> bolnice=response.getBody();
		
		assertEquals(200,statusCode);
		assertNotNull(bolnice.get(0));
		for(Bolnica b:bolnice) {
			assertTrue(b.getBudzet()<budzet); 
		}
		
	}
	
	@Test
	@Order(4)
	void testCreateBolnica() {
		Bolnica bolnica=new Bolnica();
		bolnica.setNaziv("Naziv Bolnica post");
		bolnica.setAdresa("zvezdana 3");
		
		HttpEntity<Bolnica> entity=new HttpEntity<Bolnica>(bolnica);
		createHighestId();
		ResponseEntity<Bolnica> response=template.exchange("/bolnica", HttpMethod.POST,entity,Bolnica.class);
		
		int statusCode=response.getStatusCode().value();
		
		assertEquals(201,statusCode);
		assertEquals("/bolnica/id/"+highestId, response.getHeaders().getLocation().getPath());
		assertEquals(bolnica.getNaziv(),response.getBody().getNaziv());
		assertEquals(bolnica.getAdresa(),response.getBody().getAdresa());
	}
	

	@Test
	@Order(5)
	void testUpdateBolnica() {
		Bolnica bolnica=new Bolnica();
		bolnica.setNaziv("Naziv Bolnica put");
		bolnica.setAdresa("put zvezdana 3");
		
		HttpEntity<Bolnica> entity=new HttpEntity<Bolnica>(bolnica);
		getHighestId();
		ResponseEntity<Bolnica> response=template.exchange("/bolnica/id/"+ highestId, HttpMethod.PUT,entity,Bolnica.class);
		
		int statusCode=response.getStatusCode().value();
		
		assertEquals(200,statusCode);
		assertTrue(response.getBody() instanceof Bolnica);
		assertEquals(bolnica.getNaziv(),response.getBody().getNaziv());
		assertEquals(bolnica.getAdresa(),response.getBody().getAdresa());
	}
	
	@Test
	@Order(6)
	void testDeleteBolnica() {
		getHighestId();
		ResponseEntity<String> response= template.exchange("/bolnica/id/"+highestId, HttpMethod.DELETE,null,String.class);
		
		int statusCode =response.getStatusCode().value();
		
		assertEquals(200,statusCode);
		assertTrue(response.getBody().contains("has been successfully deleted."));
	}

}
