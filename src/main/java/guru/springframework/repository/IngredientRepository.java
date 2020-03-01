package guru.springframework.repository;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.domain.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

}
