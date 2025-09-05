package pe.edu.vallegrande.quality.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

     @NotBlank(message = "El nombre es obligatorio")
     @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
     private String name;

     @NotBlank(message = "El email es obligatorio")
     @Email(message = "El email debe tener un formato válido")
     private String email;

     @NotNull(message = "La edad es obligatoria")
     @Min(value = 0, message = "La edad debe ser mayor o igual a 0")
     @Max(value = 120, message = "La edad debe ser menor o igual a 120")
     private Integer age;
}
