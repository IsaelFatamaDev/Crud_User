package pe.edu.vallegrande.quality.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
     private String id;
     private String name;
     private String email;
     private Integer age;
}
