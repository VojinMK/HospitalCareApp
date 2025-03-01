package rva.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rva.models.Bolnica;
import rva.models.Odeljenje;
import rva.repository.OdeljenjeRepository;
import rva.services.OdeljenjeService;

@Component
public class OdeljenjeServiceImpl implements OdeljenjeService {

	@Autowired
	private OdeljenjeRepository repo;
	
	@Override
	public List<Odeljenje> getAll() {
		return repo.findAll();
	}

	@Override
	public boolean existsById(int id) {
		return repo.existsById(id);
	}

	@Override
	public Odeljenje create(Odeljenje t) {
		return repo.save(t);
	}

	@Override
	public Optional<Odeljenje> update(Odeljenje t, int id) {
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
	public List<Odeljenje> getOdeljenjeByNaziv(String naziv) {
		return repo.findByNazivContainingIgnoreCase(naziv);
	}

	@Override
	public List<Odeljenje> findByForeignKey(Bolnica bolnica) {
		return repo.findByBolnica(bolnica);
	}

	@Override
	public Optional<Odeljenje> findById(int id) {
		return repo.findById(id);
	}

}
