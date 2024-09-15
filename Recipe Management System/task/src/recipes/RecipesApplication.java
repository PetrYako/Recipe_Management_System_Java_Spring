package recipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Iterator;
import java.util.Map;

@SpringBootApplication
public class RecipesApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecipesApplication.class, args);
    }
}
