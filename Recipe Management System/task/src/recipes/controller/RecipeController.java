package recipes.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import recipes.controller.dto.IdResponse;
import recipes.controller.dto.RecipeByIdResponse;
import recipes.controller.dto.RecipeNewRequest;
import recipes.service.RecipeService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/search/")
    public ResponseEntity<List<RecipeByIdResponse>> searchRecipe(
            @RequestParam Optional<String> category,
            @RequestParam Optional<String> name
    ) {
        if ((category.isPresent() && name.isPresent()) || (category.isEmpty() && name.isEmpty())) {
            return ResponseEntity.badRequest().build();
        }
        List<RecipeByIdResponse> recipes = recipeService.getRecipes(category, name);
        return ResponseEntity.ok(recipes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> putRecipe(
            @PathVariable Integer id,
            @RequestBody @Valid RecipeNewRequest recipeRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        try {
            recipeService.updateRecipe(id, recipeRequest, userDetails.getUsername());
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalCallerException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<IdResponse> postRecipe(
            @RequestBody @Valid RecipeNewRequest recipeRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        IdResponse response = recipeService.addRecipe(recipeRequest, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeByIdResponse> getRecipe(@PathVariable Integer id) {
        try {
            RecipeByIdResponse recipe = recipeService.getRecipe(id);
            return ResponseEntity.ok(recipe);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        try {
            recipeService.deleteRecipe(id, userDetails.getUsername());
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalCallerException e) {
            return ResponseEntity.status(403).build();
        }
    }
}
