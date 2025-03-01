package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rva.models.Bolnica;

@Repository
public interface BolnicaRepository extends JpaRepository<Bolnica, Integer> {

	List<Bolnica> findByBudzetLessThanOrderByBudzetAsc(double budzet);
}
