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
import rva.models.Odeljenje;
import rva.models.Pacijent;
import rva.services.DijagnozaService;
import rva.services.OdeljenjeService;
import rva.services.PacijentService;
@CrossOrigin
@RestController
public class PacijentController {

	@Autowired
	private PacijentService service;
	
	@Autowired
	private OdeljenjeService odeljenjeService;
	
	@Autowired
	private DijagnozaService dijagnozaService;
	
	
	@GetMapping("/pacijent")
	public List<Pacijent> getAllPacijent(){
		return service.getAll();
	}
	
	@GetMapping("/pacijent/id/{id}")
	public ResponseEntity<?> getPacijentById(@PathVariable int id){
		Optional<Pacijent> pacijent=service.findById(id);
		if(pacijent.isPresent()) {
			return ResponseEntity.ok(pacijent.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID: "+id+" does not exist");
	}
	
	@GetMapping("/pacijent/zdrOsiguranje/{zdrOsiguranje}")
	public ResponseEntity<?> getPacijentByZdrOsiguranje (@PathVariable boolean zdrOsiguranje){
		List<Pacijent> pacijenti=service.getPacijentByZdrOsiguranje(zdrOsiguranje);
		if(pacijenti.isEmpty()) {
			return ResponseEntity.status(404).body("Resource with that zdrOsiguranje "+ zdrOsiguranje+ " does not exist");
		}
		return ResponseEntity.ok(pacijenti);
	}
	
	@GetMapping("/pacijent/odeljenje/{foreignKey}") 
	public ResponseEntity<?> getPacijentiByOdeljenje(@PathVariable int foreignKey){
		Optional<Odeljenje> odeljenje=odeljenjeService.findById(foreignKey);
		if(odeljenje.isPresent()) {
			List<Pacijent> pacijenti=service.findByForeignKey(odeljenje.get());
			if(pacijenti.isEmpty()) {
				return ResponseEntity.status(404).body("Resources with foreign key:" + foreignKey + "do not exist");
			}else {
				return ResponseEntity.ok(pacijenti);
			}
		}
		return ResponseEntity.status(400).body("Invalid foreign key: "+foreignKey);
	}
	
	@GetMapping("/pacijent/dijagnoza/{foreignKey}") 
	public ResponseEntity<?> getPacijentiByDijagnoza(@PathVariable int foreignKey){
		Optional<Dijagnoza> dijagnoza=dijagnozaService.findById(foreignKey);
		if(dijagnoza.isPresent()) {
			List<Pacijent> pacijenti=service.findByForeignKey(dijagnoza.get());
			if(pacijenti.isEmpty()) {
				return ResponseEntity.status(404).body("Resources with foreign key: " + foreignKey + " do not exist");
			}else {
				return ResponseEntity.ok(pacijenti);
			}
		}
		return ResponseEntity.status(400).body("Invalid foreign key: "+foreignKey);
	}
	
	@PostMapping("/pacijent")
	public ResponseEntity<?> createPacijent(@RequestBody Pacijent pacijent){
		if(service.existsById(pacijent.getId())) {
			return ResponseEntity.status(409).body("Resource already exists");
		}
		Pacijent savedPacijent=service.create(pacijent);
		URI uri=URI.create("/pacijent/id/"+ savedPacijent.getId());
		return ResponseEntity.created(uri).body(savedPacijent);
	}
	
	@PutMapping("/pacijent/id/{id}")
	public ResponseEntity<?> updatePacijent(@RequestBody Pacijent pacijent, @PathVariable int id){
		Optional<Pacijent> updatedPacijent = service.update(pacijent, id);
		if(updatedPacijent.isPresent()) {
			return ResponseEntity.ok(updatedPacijent.get());
		}
		return ResponseEntity.status(404).body("Resource with requsted ID: " +id+" coulnd't be updated because it doesn't exist.");
	}
	
	@DeleteMapping("/pacijent/id/{id}")
	public ResponseEntity<?> deletePacijent(@PathVariable int id){
			if(service.existsById(id)) {
				service.delete(id);
				return ResponseEntity.ok("Resource with ID: "+id+ " has been successfully deleted.");
			}
			return ResponseEntity.status(404).body("Resource with ID: "+id+ " couldn't be deleted because it doesn't exist.");
		}
	
}
