
package pe.edu.vallegrande.quality.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.quality.constants.AppConstants;
import pe.edu.vallegrande.quality.dto.ApiResponse;
import pe.edu.vallegrande.quality.dto.UserRequest;
import pe.edu.vallegrande.quality.dto.UserResponse;
import pe.edu.vallegrande.quality.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(AppConstants.Api.USERS_PATH)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        log.info("Solicitud para obtener todos los usuarios");
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(AppConstants.Messages.USERS_RETRIEVED, users));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("Solicitud para crear usuario con email: {}", userRequest.getEmail());
        UserResponse createdUser = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(AppConstants.Messages.USER_CREATED, createdUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable String id) {
        log.info("Solicitud para obtener usuario con ID: {}", id);
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UserRequest userRequest) {
        log.info("Solicitud para actualizar usuario con ID: {}", id);
        UserResponse updatedUser = userService.updateUser(id, userRequest);
        return ResponseEntity.ok(ApiResponse.success(AppConstants.Messages.USER_UPDATED, updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String id) {
        log.info("Solicitud para eliminar usuario con ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success(AppConstants.Messages.USER_DELETED, null));
    }
}
