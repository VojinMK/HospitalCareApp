package rva.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rva.models.Dijagnoza;
import rva.models.Odeljenje;
import rva.models.Pacijent;
import rva.repository.PacijentRepository;
import rva.services.PacijentService;

@Component
public class PacijentServiceImpl implements PacijentService {

	@Autowired
	private PacijentRepository repo;
	
	@Override
	public List<Pacijent> getAll() {
		return repo.findAll();
	}

	@Override
	public boolean existsById(int id) {
		return repo.existsById(id);
	}

	@Override
	public Pacijent create(Pacijent t) {
		return repo.save(t);
	}

	@Override
	public Optional<Pacijent> update(Pacijent t, int id) {
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
	public List<Pacijent> getPacijentByZdrOsiguranje(boolean zdrOsiguranje) {
		return repo.findByZdrOsiguranjeEquals(zdrOsiguranje);
	}

	@Override
	public List<Pacijent> findByForeignKey(Odeljenje odeljenje) {
		return repo.findByOdeljenje(odeljenje);
	}

	@Override
	public List<Pacijent> findByForeignKey(Dijagnoza dijagnoza) {
		return repo.findByDijagnoza(dijagnoza);
	}

	@Override
	public Optional<Pacijent> findById(int id) {
		return repo.findById(id);
	}

}
