package pe.edu.vallegrande.quality.util;

import pe.edu.vallegrande.quality.dto.UserResponse;
import pe.edu.vallegrande.quality.model.User;

import java.util.List;
import java.util.stream.Collectors;

public final class UserMapper {

     private UserMapper() {
     }

     public static UserResponse toResponse(User user) {
          if (user == null) {
               return null;
          }
          return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getAge());
     }

     public static List<UserResponse> toResponseList(List<User> users) {
          return users.stream()
                    .map(UserMapper::toResponse)
                    .collect(Collectors.toList());
     }

     public static User toEntity(pe.edu.vallegrande.quality.dto.UserRequest userRequest) {
          if (userRequest == null) {
               return null;
          }
          return User.builder()
                    .name(userRequest.getName())
                    .email(userRequest.getEmail())
                    .age(userRequest.getAge())
                    .build();
     }
}
