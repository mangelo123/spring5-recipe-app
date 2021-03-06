package guru.springframework.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repository.IngredientRepository;
import guru.springframework.repository.RecipeRepository;
import guru.springframework.repository.UnitOfMeasureRepository;

public class IngredientServiceImplTest {

	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	RecipeService recipeService;

	@Mock
	IngredientRepository ingredientRepository;
	
	@Mock
	IngredientService ingredientService;

	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;
	
	IngredientToIngredientCommand ingredientToIngredientCommand;
	IngredientCommandToIngredient ingredientCommandToIngredient;
	
	public IngredientServiceImplTest() {
		this.ingredientToIngredientCommand = 
				new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
		this.ingredientCommandToIngredient =  
				new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}
	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
                recipeRepository, unitOfMeasureRepository);
    }

	@Test
	public void testFindByRecipeIdAndIngredientId() {

		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		Ingredient ing1 = new Ingredient();
		ing1.setId(1L);
		
		Ingredient ing2 = new Ingredient();
		ing2.setId(2L);
		
		Ingredient ing3 = new Ingredient();
		ing3.setId(3L);
		
		recipe.addIngredient(ing1);
		recipe.addIngredient(ing2);
		recipe.addIngredient(ing3);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);
		
		assertEquals(Long.valueOf(3L), ingredientCommand.getId());
		assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
		verify(recipeRepository, times(1)).findById(anyLong());			
	}
	
	@Test
	public void testSaveRecipeCmmand() {
		// given
		IngredientCommand command = new IngredientCommand();
		command.setId(3L);
		command.setRecipeId(2L);
		
		Optional<Recipe> recipeOptional = Optional.of(new Recipe());
		
		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId(3L);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		when(recipeRepository.save(any())).thenReturn(savedRecipe);
		
		// when
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
		
		// then
		assertEquals(Long.valueOf(3L), savedCommand.getId());
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any(Recipe.class));
		
	}

}
