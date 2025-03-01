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
import org.springframework.web.bind.annotation.RestController;

import rva.models.Bolnica;
import rva.models.Odeljenje;
import rva.services.BolnicaService;
import rva.services.OdeljenjeService;
@CrossOrigin
@RestController
public class OdeljenjeController {

	@Autowired
	private OdeljenjeService service;
	
	@Autowired
	private BolnicaService bolnicaService;
	
	@GetMapping("/odeljenje")
	public List<Odeljenje> getAllOdeljenje(){
		return service.getAll();
	}
	
	@GetMapping("/odeljenje/id/{id}")
	public ResponseEntity<?> getOdeljenjeById(@PathVariable int id){
		Optional<Odeljenje> odeljenje=service.findById(id);
		if(odeljenje.isPresent()) {
			return ResponseEntity.ok(odeljenje.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID: "+id+" does not exist");
	}
	
	@GetMapping("/odeljenje/naziv/{naziv}")
	public ResponseEntity<?> getOdeljenjeByNaziv (@PathVariable String naziv){
		List<Odeljenje> odeljenja=service.getOdeljenjeByNaziv(naziv);
		if(odeljenja.isEmpty()) {
			return ResponseEntity.status(404).body("Resource with that naziv "+ naziv+ " does not exist");
		}
		return ResponseEntity.ok(odeljenja);
	}
	
	@PostMapping("/odeljenje")
	public ResponseEntity<?> createOdeljenje(@RequestBody Odeljenje odeljenje){
		if(service.existsById(odeljenje.getId())) {
			return ResponseEntity.status(409).body("Resource already exists");
		}
		Odeljenje savedOdeljenje=service.create(odeljenje);
		URI uri=URI.create("/odeljenje/id/"+ savedOdeljenje.getId());
		return ResponseEntity.created(uri).body(savedOdeljenje);
	}
	
	@PutMapping("/odeljenje/id/{id}")
	public ResponseEntity<?> updateOdeljenje(@RequestBody Odeljenje odeljenje, @PathVariable int id){
		Optional<Odeljenje> updatedOdeljenje = service.update(odeljenje, id);
		if(updatedOdeljenje.isPresent()) {
			return ResponseEntity.ok(updatedOdeljenje.get());
		}
		return ResponseEntity.status(404).body("Resource with requsted ID: " +id+" coulnd't be updated because it doesn't exist.");
	}
	
	@DeleteMapping("/odeljenje/id/{id}")
	public ResponseEntity<?> deleteOdeljenje(@PathVariable int id){
			if(service.existsById(id)) {
				service.delete(id);
				return ResponseEntity.ok("Resource with ID: "+id+ " has been successfully deleted.");
			}
			return ResponseEntity.status(404).body("Resource with ID: "+id+ " couldn't be deleted because it doesn't exist.");
		}
	
	@GetMapping("/odeljenje/bolnica/{foreignKey}") 
	public ResponseEntity<?> getOdeljenjeByBolnica(@PathVariable int foreignKey){
		Optional<Bolnica> bolnica=bolnicaService.findById(foreignKey);
		if(bolnica.isPresent()) {
			List<Odeljenje> odeljenja=service.findByForeignKey(bolnica.get());
			if(odeljenja.isEmpty()) {
				return ResponseEntity.status(404).body("Resources with foreign key: " + foreignKey + " do not exist");
			}else {
				return ResponseEntity.ok(odeljenja);
			}
		}
		return ResponseEntity.status(400).body("Invalid foreign key: "+foreignKey);
	}
}
