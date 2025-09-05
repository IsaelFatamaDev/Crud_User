package pe.edu.vallegrande.quality.exception;

public class UserNotFoundException extends RuntimeException {
     public UserNotFoundException(String id) {
          super("Usuario no encontrado con ID: " + id);
     }
}
