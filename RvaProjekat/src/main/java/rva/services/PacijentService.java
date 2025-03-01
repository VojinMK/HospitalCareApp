package rva.services;

import java.util.List;

import org.springframework.stereotype.Service;

import rva.models.Dijagnoza;
import rva.models.Odeljenje;
import rva.models.Pacijent;

@Service
public interface PacijentService extends CrudService<Pacijent> {

    List<Pacijent> getPacijentByZdrOsiguranje(boolean zdrOsiguranje);
	
	List<Pacijent> findByForeignKey(Odeljenje odeljenje);
	List<Pacijent> findByForeignKey(Dijagnoza dijagnoza);
}
