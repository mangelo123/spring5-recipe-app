package guru.springframework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.service.IngredientService;
import guru.springframework.service.RecipeService;
import guru.springframework.service.UnitOfMeasureService;

@Controller
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService uomService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService,
								UnitOfMeasureService uomService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.uomService = uomService;
	}
	
	@GetMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
		
		return "/recipe/ingredient/list";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable String recipeId,
									   @PathVariable String id, Model model) {
		
		model.addAttribute("ingredient", 
				ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		
		return "/recipe/ingredient/show";
		
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
	public String updateRecipeIngedient(@PathVariable String recipeId,
										@PathVariable String id, Model model) {
		
		model.addAttribute("ingredient",
				ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));		
		model.addAttribute("uomList", uomService.listAllUoms());
		
		return "/recipe/ingredient/ingredientForm";		
	}
	
	@PostMapping("/recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
		IngredientCommand saved = ingredientService.saveIngredientCommand(command);
		
		return "redirect:/recipe/" + saved.getRecipeId() + "/ingredient/" + saved.getId() + "/show";
	}
	
	
	
}
