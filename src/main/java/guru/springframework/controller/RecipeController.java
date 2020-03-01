package guru.springframework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.service.RecipeService;

@Controller
public class RecipeController {

	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}
	
	@RequestMapping("/recipe/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		
		model.addAttribute("recipe", recipeService.findById(Long.parseLong(id)));
		
		return "/recipe/show";
	}
	
	@RequestMapping("/recipe/new")
	public String newRecipe(Model model) {
		
		model.addAttribute("recipe", new RecipeCommand());
		
		return "/recipe/recipeForm";
	}
	
	@RequestMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "/recipe/recipeForm";
	}		
	
	@RequestMapping("/recipe/{id}/delete")
	public String deleteRecipe(@PathVariable String id) {
		recipeService.deleteById(Long.valueOf(id));
		
		return "redirect:/";
	}
	
	@PostMapping("recipe")
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		RecipeCommand saveCommand = recipeService.saveRecipeCommand(command);
		
		return "redirect:/recipe/"+ saveCommand.getId() + "/show";
	}
}
