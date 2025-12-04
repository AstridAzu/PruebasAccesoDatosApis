package mains.jercicio3APIAutenticaciónBásica.repositories;

import mains.jercicio3APIAutenticaciónBásica.models.Usuario;

import java.util.*;

public class UsuarioRepository {
    private final Map<String, Usuario> usuarios = new HashMap<>();

    public Optional<Usuario> findByUsername(String username) {
        return Optional.ofNullable(usuarios.get(username));
    }

    public boolean existsByUsername(String username) {
        return usuarios.containsKey(username);
    }

    public Usuario save(Usuario usuario) {
        usuarios.put(usuario.getUsername(), usuario);
        return usuario;
    }

    public List<Usuario> findAll() {
        return new ArrayList<>(usuarios.values());
    }
}
