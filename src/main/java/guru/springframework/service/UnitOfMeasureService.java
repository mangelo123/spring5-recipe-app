package guru.springframework.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repository.UnitOfMeasureRepository;

@Service
public class UnitOfMeasureService {

	private UnitOfMeasureRepository repo;

	public UnitOfMeasureService(UnitOfMeasureRepository repo) {
		super();
		this.repo = repo;
	}
	
	public Iterable<UnitOfMeasure> getUnitsOfMeasure() {
		return repo.findAll();
	}
	
	public Optional<UnitOfMeasure> findByDescription(String description) {
		return repo.findByDescription(description);
	}
}
