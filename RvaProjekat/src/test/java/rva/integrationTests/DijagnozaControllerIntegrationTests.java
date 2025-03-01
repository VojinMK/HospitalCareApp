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
import rva.models.Dijagnoza;


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DijagnozaControllerIntegrationTests {

	@Autowired
	TestRestTemplate template;
	
	int highestId;
	
	void createHighestId() {
		ResponseEntity<List<Dijagnoza>> response=template.exchange("/dijagnoza",HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Dijagnoza>>() {});
		
		ArrayList<Dijagnoza> list=(ArrayList<Dijagnoza>) response.getBody();
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
	void testGetAllDijagnoza() {
		ResponseEntity<List<Dijagnoza>> response=template.exchange("/dijagnoza",HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Dijagnoza>>() {});
		
		int statusCode=response.getStatusCode().value();
		List<Dijagnoza> dijagnoze=response.getBody();
		
		assertEquals(200, statusCode);
		assertTrue(!dijagnoze.isEmpty());
	}
	
	@Test
	@Order(2)
	void testGetDijagnozaById() {
		int id=1;
		
		ResponseEntity<Dijagnoza> response=template.exchange("/dijagnoza/id/"+id,HttpMethod.GET,null,
				Dijagnoza.class);
		
		int statusCode=response.getStatusCode().value();
		
		assertEquals(200,statusCode);
		assertEquals(id,response.getBody().getId());
	}
	
	@Test
	@Order(3)
	void testGetDijagnozaByOznaka() {
		String oznaka="GR"; 
		
		ResponseEntity<List<Dijagnoza>> response=template.exchange("/dijagnoza/oznaka/"+oznaka,HttpMethod.GET,null,
				new ParameterizedTypeReference<List<Dijagnoza>>() {});
		
        int statusCode=response.getStatusCode().value();
        List<Dijagnoza> dijagnoze=response.getBody();
		
		assertEquals(200,statusCode);
		assertNotNull(dijagnoze.get(0));
		for(Dijagnoza d:dijagnoze) {
			assertTrue(d.getOznaka().startsWith(oznaka)); 
		}
		
	}
	
	@Test
	@Order(4)
	void testCreateDijagnoza() {
		Dijagnoza dijagnoza=new Dijagnoza();
		dijagnoza.setNaziv("Naziv dijagnoza post");
		dijagnoza.setOpis("najjca dijagnoza");
		
		HttpEntity<Dijagnoza> entity=new HttpEntity<Dijagnoza>(dijagnoza);
		createHighestId();
		ResponseEntity<Dijagnoza> response=template.exchange("/dijagnoza", HttpMethod.POST,entity,Dijagnoza.class);
		
		int statusCode=response.getStatusCode().value();
		
		assertEquals(201,statusCode);
		assertEquals("/dijagnoza/id/"+highestId, response.getHeaders().getLocation().getPath());
		assertEquals(dijagnoza.getNaziv(),response.getBody().getNaziv());
		assertEquals(dijagnoza.getOpis(),response.getBody().getOpis());
	}
	

	@Test
	@Order(5)
	void testUpdateDijagnoza() {
		Dijagnoza dijagnoza=new Dijagnoza();
		dijagnoza.setNaziv("Naziv dijagnoza post");
		dijagnoza.setOpis("najjca dijagnoza");
		
		
		HttpEntity<Dijagnoza> entity=new HttpEntity<Dijagnoza>(dijagnoza);
		getHighestId();
		ResponseEntity<Dijagnoza> response=template.exchange("/dijagnoza/id/"+ highestId, HttpMethod.PUT,entity,Dijagnoza.class);
		
		int statusCode=response.getStatusCode().value();
		
		assertEquals(200,statusCode);
		assertTrue(response.getBody() instanceof Dijagnoza);
		assertEquals(dijagnoza.getNaziv(),response.getBody().getNaziv());
		assertEquals(dijagnoza.getOznaka(),response.getBody().getOznaka());
	}
	
	@Test
	@Order(6)
	void testDeleteDijagnoza() {
		getHighestId();
		ResponseEntity<String> response= template.exchange("/dijagnoza/id/"+highestId, HttpMethod.DELETE,null,String.class);
		
		int statusCode =response.getStatusCode().value();
		
		assertEquals(200,statusCode);
		assertTrue(response.getBody().contains("has been successfully deleted."));
	}


}
