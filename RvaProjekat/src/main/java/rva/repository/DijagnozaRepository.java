package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rva.models.Dijagnoza;

@Repository
public interface DijagnozaRepository extends JpaRepository<Dijagnoza, Integer> {

	List<Dijagnoza> findByOznakaContainingIgnoreCase(String oznaka);
}
