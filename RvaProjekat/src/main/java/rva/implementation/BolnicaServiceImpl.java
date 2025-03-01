package rva.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rva.models.Bolnica;
import rva.repository.BolnicaRepository;
import rva.services.BolnicaService;

@Component
public class BolnicaServiceImpl implements BolnicaService {
	
	@Autowired
	private BolnicaRepository repo;

	@Override
	public List<Bolnica> getAll() {
		return repo.findAll();
	}

	@Override
	public boolean existsById(int id) {
		return repo.existsById(id);
	}

	@Override
	public Bolnica create(Bolnica t) {
		return repo.save(t);
	}
	
	

	@Override
	public Optional<Bolnica> update(Bolnica t, int id) {
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
	public List<Bolnica> getBolnicaByBudzet(double budzet) {
		return repo.findByBudzetLessThanOrderByBudzetAsc(budzet);
	}

	@Override
	public Optional<Bolnica> findById(int id) {
		return repo.findById(id);
	}

}
