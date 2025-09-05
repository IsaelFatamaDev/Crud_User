package pe.edu.vallegrande.quality.constants;

public final class AppConstants {

     public static final class Api {
          public static final String BASE_PATH = "/api/v1";
          public static final String USERS_PATH = BASE_PATH + "/users";

          private Api() {
          }
     }

     public static final class Messages {
          public static final String USER_CREATED = "Usuario creado exitosamente";
          public static final String USER_UPDATED = "Usuario actualizado exitosamente";
          public static final String USER_DELETED = "Usuario eliminado exitosamente";
          public static final String USERS_RETRIEVED = "Usuarios obtenidos exitosamente";

          private Messages() {
          }
     }

     public static final class Defaults {
          public static final int DEFAULT_AGE = 0;

          private Defaults() {
          }
     }

     private AppConstants() {
     }
}
