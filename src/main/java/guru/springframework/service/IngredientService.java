package guru.springframework.service;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;

public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id);
	IngredientCommand saveIngredientCommand(IngredientCommand command);
}
