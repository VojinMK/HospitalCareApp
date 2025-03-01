package rva.services;

import java.util.List;

import org.springframework.stereotype.Service;

import rva.models.Bolnica;

@Service
public interface BolnicaService extends CrudService<Bolnica> {
  
	List<Bolnica> getBolnicaByBudzet(double budzet);
}
