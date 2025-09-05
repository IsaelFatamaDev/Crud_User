
package pe.edu.vallegrande.quality.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.quality.model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class UserRepository {

    // Usar ConcurrentHashMap para thread-safety en operaciones concurrentes
    private final Map<String, User> userStorage = new ConcurrentHashMap<>();

    public List<User> findAll() {
        log.debug("Obteniendo todos los usuarios");
        return new ArrayList<>(userStorage.values());
    }

    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("El usuario no puede ser null");
        }

        if (user.getId() == null || user.getId().trim().isEmpty()) {
            user.setId(UUID.randomUUID().toString());
        }

        userStorage.put(user.getId(), user);
        log.debug("Usuario guardado con ID: {}", user.getId());
        return user;
    }

    public Optional<User> findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }

        User user = userStorage.get(id);
        log.debug("Buscando usuario con ID: {}, encontrado: {}", id, user != null);
        return Optional.ofNullable(user);
    }

    public boolean deleteById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }

        boolean removed = userStorage.remove(id) != null;
        log.debug("Usuario con ID {} eliminado: {}", id, removed);
        return removed;
    }

    public boolean existsById(String id) {
        return id != null && userStorage.containsKey(id);
    }

    public long count() {
        return userStorage.size();
    }

    public void deleteAll() {
        userStorage.clear();
        log.debug("Todos los usuarios eliminados");
    }
}
