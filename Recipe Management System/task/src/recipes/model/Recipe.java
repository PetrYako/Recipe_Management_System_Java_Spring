package recipes.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    @ElementCollection
    private List<String> ingredients;
    @ElementCollection
    private List<String> directions;
    private String category;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Recipe(String name, String description, List<String> ingredients, List<String> directions, String category, String author) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
        this.category = category;
        this.author = author;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Recipe() {

    }
}
