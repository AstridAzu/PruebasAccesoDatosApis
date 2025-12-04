package mains.jercicio3APIAutenticaciónBásica.repositories;

import java.util.*;

public class TokenRepository {
    private final Map<String, String> tokens = new HashMap<>(); // token -> username

    public void save(String token, String username) {
        tokens.put(token, username);
    }

    public Optional<String> findUsernameByToken(String token) {
        return Optional.ofNullable(tokens.get(token));
    }

    public void delete(String token) {
        tokens.remove(token);
    }

    public boolean exists(String token) {
        return tokens.containsKey(token);
    }
}
