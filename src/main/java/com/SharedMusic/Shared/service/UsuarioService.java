package com.SharedMusic.Shared.service;

import com.SharedMusic.Shared.entity.Usuario;
import com.SharedMusic.Shared.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuario> lista() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getById(int id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> getByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public boolean existsById(int id) {
        return usuarioRepository.existsById(id);
    }

    public boolean existsByNombreusuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    public List<Usuario> list() {
        return usuarioRepository.findAll();
    }

    public void delete(int id) {
        usuarioRepository.deleteById(id);
    }

}
