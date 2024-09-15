package recipes.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.controller.dto.IdResponse;
import recipes.controller.dto.RecipeByIdResponse;
import recipes.controller.dto.RecipeNewRequest;
import recipes.model.Recipe;
import recipes.model.repository.RecipeRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public IdResponse addRecipe(RecipeNewRequest recipeRequest, String username) {
        Recipe recipe = new Recipe(
                recipeRequest.getName(),
                recipeRequest.getDescription(),
                recipeRequest.getIngredients(),
                recipeRequest.getDirections(),
                recipeRequest.getCategory(),
                username
        );
        Recipe savedRecipe = recipeRepository.save(recipe);
        return new IdResponse(savedRecipe.getId());
    }

    public RecipeByIdResponse getRecipe(Integer id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow();
        return mapToRecipeByIdResponse(recipe);
    }

    public void deleteRecipe(Integer id, String username) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow();
        if (!recipe.getAuthor().equals(username)) {
            throw new IllegalCallerException("Only author can change the recipe");
        }
        recipeRepository.delete(recipe);
    }

    public void updateRecipe(Integer id, @Valid RecipeNewRequest recipeRequest, String username) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow();
        if (!recipe.getAuthor().equals(username)) {
            throw new IllegalCallerException("Only author can change the recipe");
        }
        recipe.setName(recipeRequest.getName());
        recipe.setDescription(recipeRequest.getDescription());
        recipe.setIngredients(recipeRequest.getIngredients());
        recipe.setDirections(recipeRequest.getDirections());
        recipe.setCategory(recipeRequest.getCategory());
        recipe.setUpdatedAt(LocalDateTime.now());
        recipeRepository.save(recipe);
    }

    public List<RecipeByIdResponse> getRecipes(Optional<String> category, Optional<String> name) {
        List<Recipe> recipes;
        if (category.isPresent()) {
            recipes = recipeRepository.findByCategoryIgnoreCaseOrderByCreatedAtDesc(category.get());
        } else if (name.isPresent()) {
            recipes = recipeRepository.findByNameContainingIgnoreCaseOrderByCreatedAtDesc(name.get());
        } else {
            recipes = Collections.emptyList();
        }
        return recipes.stream().map(this::mapToRecipeByIdResponse).toList();
    }

    private RecipeByIdResponse mapToRecipeByIdResponse(Recipe recipe) {
        return new RecipeByIdResponse(
                recipe.getName(),
                recipe.getCategory(),
                recipe.getUpdatedAt(),
                recipe.getDescription(),
                recipe.getIngredients(),
                recipe.getDirections()
        );
    }
}
