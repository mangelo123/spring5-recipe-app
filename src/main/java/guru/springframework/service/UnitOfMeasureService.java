package guru.springframework.service;

import java.util.Optional;
import java.util.Set;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;

public interface UnitOfMeasureService {

	Set<UnitOfMeasureCommand> listAllUoms();

	Optional<UnitOfMeasure> findByDescription(String description);

}