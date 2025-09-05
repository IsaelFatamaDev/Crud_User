package pe.edu.vallegrande.quality.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.vallegrande.quality.dto.UserRequest;
import pe.edu.vallegrande.quality.dto.UserResponse;
import pe.edu.vallegrande.quality.exception.UserNotFoundException;
import pe.edu.vallegrande.quality.exception.UserValidationException;
import pe.edu.vallegrande.quality.model.User;
import pe.edu.vallegrande.quality.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

     @Mock
     private UserRepository userRepository;

     @InjectMocks
     private UserService userService;

     private UserRequest validUserRequest;
     private User validUser;

     @BeforeEach
     void setUp() {
          validUserRequest = new UserRequest("Juan Pérez", "juan@email.com", 25);
          validUser = User.builder()
                    .id("1")
                    .name("Juan Pérez")
                    .email("juan@email.com")
                    .age(25)
                    .build();
     }

     @Test
     void getAllUsers_ShouldReturnSortedUsers() {
          // Given
          List<User> users = Arrays.asList(
                    User.builder().id("2").name("Carlos").email("carlos@email.com").age(30).build(),
                    User.builder().id("1").name("Ana").email("ana@email.com").age(25).build());
          when(userRepository.findAll()).thenReturn(users);

          // When
          List<UserResponse> result = userService.getAllUsers();

          // Then
          assertNotNull(result);
          assertEquals(2, result.size());
          assertEquals("Ana", result.get(0).getName());
          assertEquals("Carlos", result.get(1).getName());
     }

     @Test
     void createUser_WithValidData_ShouldCreateUser() {
          // Given
          when(userRepository.save(any(User.class))).thenReturn(validUser);

          // When
          UserResponse result = userService.createUser(validUserRequest);

          // Then
          assertNotNull(result);
          assertEquals(validUserRequest.getName(), result.getName());
          assertEquals(validUserRequest.getEmail(), result.getEmail());
          assertEquals(validUserRequest.getAge(), result.getAge());
          verify(userRepository, times(1)).save(any(User.class));
     }

     @Test
     void createUser_WithInvalidData_ShouldThrowException() {
          // Given
          UserRequest invalidRequest = new UserRequest("", "invalid-email", -5);

          // When & Then
          assertThrows(UserValidationException.class, () -> userService.createUser(invalidRequest));
          verify(userRepository, never()).save(any(User.class));
     }

     @Test
     void getUserById_WithValidId_ShouldReturnUser() {
          // Given
          when(userRepository.findById("1")).thenReturn(Optional.of(validUser));

          // When
          UserResponse result = userService.getUserById("1");

          // Then
          assertNotNull(result);
          assertEquals(validUser.getName(), result.getName());
     }

     @Test
     void getUserById_WithInvalidId_ShouldThrowException() {
          // Given
          when(userRepository.findById("999")).thenReturn(Optional.empty());

          // When & Then
          assertThrows(UserNotFoundException.class, () -> userService.getUserById("999"));
     }

     @Test
     void deleteUser_WithValidId_ShouldDeleteUser() {
          // Given
          when(userRepository.existsById("1")).thenReturn(true);
          when(userRepository.deleteById("1")).thenReturn(true);

          // When
          assertDoesNotThrow(() -> userService.deleteUser("1"));

          // Then
          verify(userRepository, times(1)).deleteById("1");
     }

     @Test
     void deleteUser_WithInvalidId_ShouldThrowException() {
          // Given
          when(userRepository.existsById("999")).thenReturn(false);

          // When & Then
          assertThrows(UserNotFoundException.class, () -> userService.deleteUser("999"));
          verify(userRepository, never()).deleteById(anyString());
     }
}
