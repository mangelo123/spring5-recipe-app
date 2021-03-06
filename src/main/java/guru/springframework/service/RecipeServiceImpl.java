package guru.springframework.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;   

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the service");

        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long l) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(l);

        if (!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe Not Found!");
        }

        return recipeOptional.get();
    }
    
    @Override
    @Transactional
    public void deleteById(Long id) {
    	recipeRepository.deleteById(id);
    }
    
	@Override
	@Transactional
	public RecipeCommand findCommandById(Long id) {
		return recipeToRecipeCommand.convert(findById(id)); 
	}    
    
    
    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
    	Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
    	
    	Recipe savedRecipe = recipeRepository.save(detachedRecipe);
    	log.debug("Saved Recipe Id:" + savedRecipe.getId());
    	return recipeToRecipeCommand.convert(savedRecipe);
    }

}
