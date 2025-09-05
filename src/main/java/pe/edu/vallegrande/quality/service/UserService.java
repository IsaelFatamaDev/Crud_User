
package pe.edu.vallegrande.quality.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.quality.dto.UserRequest;
import pe.edu.vallegrande.quality.dto.UserResponse;
import pe.edu.vallegrande.quality.exception.UserNotFoundException;
import pe.edu.vallegrande.quality.exception.UserValidationException;
import pe.edu.vallegrande.quality.model.User;
import pe.edu.vallegrande.quality.repository.UserRepository;
import pe.edu.vallegrande.quality.util.UserMapper;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        log.info("Obteniendo todos los usuarios");
        List<User> users = userRepository.findAll();

        // Ordenar por nombre de forma consistente
        users.sort(Comparator.comparing(User::getName, String.CASE_INSENSITIVE_ORDER));

        List<UserResponse> response = UserMapper.toResponseList(users);
        log.info("Se obtuvieron {} usuarios", response.size());
        return response;
    }

    public UserResponse createUser(UserRequest userRequest) {
        log.info("Creando nuevo usuario con email: {}", userRequest.getEmail());

        validateUserRequest(userRequest);

        User user = UserMapper.toEntity(userRequest);
        user.setId(UUID.randomUUID().toString());

        User savedUser = userRepository.save(user);
        log.info("Usuario creado exitosamente con ID: {}", savedUser.getId());

        return UserMapper.toResponse(savedUser);
    }

    public UserResponse getUserById(String id) {
        log.info("Buscando usuario con ID: {}", id);

        if (id == null || id.trim().isEmpty()) {
            throw new UserValidationException("El ID del usuario es obligatorio");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return UserMapper.toResponse(user);
    }

    public UserResponse updateUser(String id, UserRequest userRequest) {
        log.info("Actualizando usuario con ID: {}", id);

        if (id == null || id.trim().isEmpty()) {
            throw new UserValidationException("El ID del usuario es obligatorio");
        }

        validateUserRequest(userRequest);

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        existingUser.setName(userRequest.getName());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setAge(userRequest.getAge());

        User updatedUser = userRepository.save(existingUser);
        log.info("Usuario actualizado exitosamente con ID: {}", updatedUser.getId());

        return UserMapper.toResponse(updatedUser);
    }

    public void deleteUser(String id) {
        log.info("Eliminando usuario con ID: {}", id);

        if (id == null || id.trim().isEmpty()) {
            throw new UserValidationException("El ID del usuario es obligatorio");
        }

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        boolean deleted = userRepository.deleteById(id);
        if (deleted) {
            log.info("Usuario eliminado exitosamente con ID: {}", id);
        } else {
            throw new UserValidationException("No se pudo eliminar el usuario con ID: " + id);
        }
    }

    private void validateUserRequest(UserRequest userRequest) {
        if (userRequest == null) {
            throw new UserValidationException("Los datos del usuario son obligatorios");
        }

        if (userRequest.getName() == null || userRequest.getName().trim().isEmpty()) {
            throw new UserValidationException("El nombre es obligatorio");
        }

        if (userRequest.getEmail() == null || userRequest.getEmail().trim().isEmpty()) {
            throw new UserValidationException("El email es obligatorio");
        }

        if (!isValidEmail(userRequest.getEmail())) {
            throw new UserValidationException("El formato del email no es válido");
        }

        if (userRequest.getAge() == null || userRequest.getAge() < 0 || userRequest.getAge() > 120) {
            throw new UserValidationException("La edad debe estar entre 0 y 120 años");
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}
