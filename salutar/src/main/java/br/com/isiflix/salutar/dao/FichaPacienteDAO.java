package br.com.isiflix.salutar.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.isiflix.salutar.model.FichaPaciente;

public interface FichaPacienteDAO extends CrudRepository<FichaPaciente, Integer>{
	public List<FichaPaciente> findByNomePacienteContaining(String palavraChave);
}

