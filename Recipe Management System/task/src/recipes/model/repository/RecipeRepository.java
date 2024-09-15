package recipes.model.repository;

import org.springframework.data.repository.CrudRepository;
import recipes.model.Recipe;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    List<Recipe> findByCategoryIgnoreCaseOrderByCreatedAtDesc(String category);
    List<Recipe> findByNameContainingIgnoreCaseOrderByCreatedAtDesc(String name);
}
