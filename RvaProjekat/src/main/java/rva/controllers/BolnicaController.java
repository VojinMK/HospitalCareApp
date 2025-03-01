package rva.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rva.models.Bolnica;
import rva.services.BolnicaService;
@CrossOrigin
@RestController
public class BolnicaController {

	@Autowired
	private BolnicaService service;
	
	@GetMapping("/bolnica")
	//@RequestMapping(method=RequestMethod.GET, path="/bolnica")
	public List<Bolnica> getAllBolnica(){
		return service.getAll();
	}
	
	@GetMapping("/bolnica/id/{id}")
	public ResponseEntity<?> getBolnicaById(@PathVariable int id){
		Optional<Bolnica> bolnica=service.findById(id);
		if(bolnica.isPresent()) {
			return ResponseEntity.ok(bolnica.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID: "+id+" does not exist");
	}
	
	@GetMapping("/bolnica/budzet/{budzet}")
	public ResponseEntity<?> getBolnicaByBudzet (@PathVariable double budzet){
		List<Bolnica> bolnice=service.getBolnicaByBudzet(budzet);
		if(bolnice.isEmpty()) {
			return ResponseEntity.status(404).body("Resource with budzet less than "+ budzet+ "do not exist");
		}
		return ResponseEntity.ok(bolnice);
	}
	
	@PostMapping("/bolnica")
	public ResponseEntity<?> createBolnica(@RequestBody Bolnica bolnica){
		if(service.existsById(bolnica.getId())) {
			return ResponseEntity.status(409).body("Resource already exists");
		}
		Bolnica savedBolnica=service.create(bolnica);
		URI uri=URI.create("/bolnica/id/"+ savedBolnica.getId());
		return ResponseEntity.created(uri).body(savedBolnica);
	}
	
	@PutMapping("/bolnica/id/{id}")
	public ResponseEntity<?> updateBolnica(@RequestBody Bolnica bolnica, @PathVariable int id){
		Optional<Bolnica> updatedBolnica = service.update(bolnica, id);
		if(updatedBolnica.isPresent()) {
			return ResponseEntity.ok(updatedBolnica.get());
		}
		return ResponseEntity.status(404).body("Resource with requsted ID: " +id+"coulnd't be updated because it doesn't exist.");
	}
	
	@DeleteMapping("/bolnica/id/{id}")
	public ResponseEntity<?> deleteBolnica(@PathVariable int id){
			if(service.existsById(id)) {
				service.delete(id);
				return ResponseEntity.ok("Resource with ID: "+id+ " has been successfully deleted.");
			}
			return ResponseEntity.status(404).body("Resource with ID: "+id+ " couldn't be deleted because it doesn't exist.");
		}
	
	
}
