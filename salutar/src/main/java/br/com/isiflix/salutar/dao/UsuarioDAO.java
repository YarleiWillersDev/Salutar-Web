package br.com.isiflix.salutar.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.isiflix.salutar.model.Usuario;

public interface UsuarioDAO extends CrudRepository<Usuario, Integer>{
    public Usuario findByLogin(String login); 
}
