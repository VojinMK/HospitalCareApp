package rva.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rva.models.Bolnica;
import rva.models.Dijagnoza;
import rva.repository.BolnicaRepository;
import rva.repository.DijagnozaRepository;
import rva.services.DijagnozaService;

@Component
public class DijagnozaServiceImpl implements DijagnozaService {

	@Autowired
	private DijagnozaRepository repo;
	
	@Override
	public List<Dijagnoza> getAll() {
		return repo.findAll();
	}

	@Override
	public boolean existsById(int id) {
		return repo.existsById(id);
	}

	@Override
	public Dijagnoza create(Dijagnoza t) {
		return repo.save(t);
	}

	@Override
	public Optional<Dijagnoza> update(Dijagnoza t, int id) {
		if(existsById(id)) {
			t.setId(id);
			return Optional.of(repo.save(t));
		}
		return Optional.empty();
	}

	@Override
	public void delete(int id) {
		repo.deleteById(id);
	}

	@Override
	public List<Dijagnoza> getDijagnozaByOznaka(String oznaka) {
		return repo.findByOznakaContainingIgnoreCase(oznaka);
	}
	
	@Override
	public Optional<Dijagnoza> findById(int id) {
		return repo.findById(id);
	}

}
