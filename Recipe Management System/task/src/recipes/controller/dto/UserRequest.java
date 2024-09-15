package recipes.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotBlank
    @Email(regexp = ".+[@].+[\\.].+")
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;
}
