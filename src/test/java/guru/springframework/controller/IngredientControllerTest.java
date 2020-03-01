package guru.springframework.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.repository.IngredientRepository;
import guru.springframework.service.IngredientService;
import guru.springframework.service.RecipeService;
import guru.springframework.service.UnitOfMeasureService;

public class IngredientControllerTest {

	@Mock
	RecipeService recipeService;
	
	@Mock
	IngredientRepository ingredientRepository;
	
	@Mock
	IngredientService ingredientService;
	
	@Mock
	UnitOfMeasureService uomService;
	
	IngredientController controller;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		IngredientToIngredientCommand ingredientToIngredientCommand = 
				new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
		
		IngredientCommandToIngredient ingredientCommandToIngredient = 
				new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
		
		controller = new IngredientController(recipeService, ingredientService, uomService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();		
	}
	
	@Test
	public void testListIngredients() throws Exception {
		RecipeCommand command = new RecipeCommand();
		when(recipeService.findCommandById(anyLong())).thenReturn(command);
		
		mockMvc.perform(get("/recipe/1/ingredients"))
			.andExpect(status().isOk())
			.andExpect(view().name("/recipe/ingredient/list"))
			.andExpect(model().attributeExists("recipe"));
		
		verify(recipeService, times(1)).findCommandById(anyLong());
	}
	
	@Test
	public void testShowIngredient() throws Exception {
		IngredientCommand command = new IngredientCommand();
		
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(command);
		
		mockMvc.perform(get("/recipe/1/ingredient/2/show"))
			.andExpect(status().isOk())
			.andExpect(view().name("/recipe/ingredient/show"))
			.andExpect(model().attributeExists("ingredient"));
	}
	
}
