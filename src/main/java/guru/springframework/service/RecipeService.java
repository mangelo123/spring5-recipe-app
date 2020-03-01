package guru.springframework.service;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;

import java.util.Set;


public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long l);
    
    void deleteById(Long id);
    
    RecipeCommand findCommandById(Long id);
    
    RecipeCommand saveRecipeCommand(RecipeCommand command);
}