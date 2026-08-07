package com.auroraacademy.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.auroraacademy.backend.models.Usuario;
import com.auroraacademy.backend.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado."));
    }

    public Usuario cadastro(Usuario usuario) {
        validar(usuario);
        validarEmailDisponivel(usuario.getEmail());

        if (usuario.getAtivo() == null) {
            usuario.setAtivo(true);
        }

        if (usuario.getDataCadastro() == null) {
            usuario.setDataCadastro(LocalDateTime.now());
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Usuario usuario = buscarPorId(id);

        if (!usuario.getEmail().equals(usuarioAtualizado.getEmail())) {
            validarEmailDisponivel(usuarioAtualizado.getEmail());
        }

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setSenha(usuarioAtualizado.getSenha());
        usuario.setPerfil(usuarioAtualizado.getPerfil());
        usuario.setAtivo(usuarioAtualizado.getAtivo());

        validar(usuario);

        return usuarioRepository.save(usuario);
    }

    public void deletar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
    }

    private void validar(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome e obrigatorio.");
        }

        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email e obrigatorio.");
        }

        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new IllegalArgumentException("Senha e obrigatoria.");
        }

        if (usuario.getPerfil() == null) {
            throw new IllegalArgumentException("Perfil e obrigatorio.");
        }
    }

    private void validarEmailDisponivel(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email ja cadastrado.");
        }
    }

    public Usuario login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Email ou senha invalidos."));

        if (!usuario.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Email ou senha invalidos.");
        }

        if (usuario.getAtivo() != null && !usuario.getAtivo()) {
            throw new IllegalArgumentException("Usuario inativo.");
        }

        return usuario;
    }
}
