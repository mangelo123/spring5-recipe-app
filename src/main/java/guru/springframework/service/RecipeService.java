package guru.springframework.service;

import guru.springframework.domain.Recipe;

public interface RecipeService {

	Iterable<Recipe> getRecipes();

	Recipe save(Recipe recipe);

}