package br.com.isiflix.salutar.service.ficha;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.isiflix.salutar.dao.FichaPacienteDAO;
import br.com.isiflix.salutar.model.FichaPaciente;

@Component
public class FichaServiceImplementacao implements IFichaService{

	@Autowired
	private FichaPacienteDAO dao;
	
	@Override
	public FichaPaciente cadastrar(FichaPaciente nova) {
		nova.setUuid(UUID.randomUUID().toString());
		nova.setAtivo(1);
		return dao.save(nova);
	}

	@Override
	public FichaPaciente alterar(FichaPaciente ficha) {
		FichaPaciente tmp = dao.findById(ficha.getIdFicha()).orElse(ficha);
		if (tmp != null) {
			if (ficha.getAtivo() != null) {
				tmp.setAtivo(ficha.getAtivo());
			}
			return dao.save(ficha);
		}
		return null;
	}

	@Override
	public List<FichaPaciente> buscaPorNome(String nome) {
		
		return dao.findByNomePacienteContaining(nome);
	}

	@Override
	public FichaPaciente recuperaPeloId(Integer id) {
		
		return dao.findById(id).orElse(null);
	}

	@Override
	public boolean excluir(Integer id) {
		FichaPaciente ficha = recuperaPeloId(id);
		if (ficha != null) {
			ficha.setAtivo(0);
			dao.save(ficha);
			return true;
		}
		return false;
	}
	
}

