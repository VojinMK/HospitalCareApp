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

import rva.models.Dijagnoza;
import rva.services.DijagnozaService;
@CrossOrigin
@RestController
public class DijagnozaController {
	
	@Autowired
	private DijagnozaService service;
	
	@GetMapping("/dijagnoza")
	public List<Dijagnoza> getAllDijagnoza(){
		return service.getAll();
	}
	
	@GetMapping("/dijagnoza/id/{id}")
	public ResponseEntity<?> getDijagnozaById(@PathVariable int id){
		Optional<Dijagnoza> dijagnoza=service.findById(id);
		if(dijagnoza.isPresent()) {
			return ResponseEntity.ok(dijagnoza.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID: "+id+" does not exist");
	}
	
	@GetMapping("/dijagnoza/oznaka/{oznaka}")
	public ResponseEntity<?> getDijagnozaByOznaka (@PathVariable String oznaka){
		List<Dijagnoza> dijagnoze=service.getDijagnozaByOznaka(oznaka);
		if(dijagnoze.isEmpty()) {
			return ResponseEntity.status(404).body("Resource with that oznaka "+ oznaka+ " does not exist");
		}
		return ResponseEntity.ok(dijagnoze);
	}
	
	@PostMapping("/dijagnoza")
	public ResponseEntity<?> createDijagnoza(@RequestBody Dijagnoza dijagnoza){
		if(service.existsById(dijagnoza.getId())) {
			return ResponseEntity.status(409).body("Resource already exists");
		}
		Dijagnoza savedDijagnoza=service.create(dijagnoza);
		URI uri=URI.create("/dijagnoza/id/"+ savedDijagnoza.getId());
		return ResponseEntity.created(uri).body(savedDijagnoza);
	}
	
	@PutMapping("/dijagnoza/id/{id}")
	public ResponseEntity<?> updateDijagnoza(@RequestBody Dijagnoza dijagnoza, @PathVariable int id){
		Optional<Dijagnoza> updatedDijagnoza = service.update(dijagnoza, id);
		if(updatedDijagnoza.isPresent()) {
			return ResponseEntity.ok(updatedDijagnoza.get());
		}
		return ResponseEntity.status(404).body("Resource with requsted ID: " +id+" coulnd't be updated because it doesn't exist.");
	}
	
	@DeleteMapping("/dijagnoza/id/{id}")
	public ResponseEntity<?> deleteDijagnoza(@PathVariable int id){
			if(service.existsById(id)) {
				service.delete(id);
				return ResponseEntity.ok("Resource with ID: "+id+ " has been successfully deleted.");
			}
			return ResponseEntity.status(404).body("Resource with ID: "+id+ " couldn't be deleted because it doesn't exist.");
		}

}
