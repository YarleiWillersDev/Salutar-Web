package br.com.isiflix.salutar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.isiflix.salutar.model.FichaPaciente;
import br.com.isiflix.salutar.service.ficha.IFichaService;

@SpringBootTest
@ActiveProfiles("test")
public class FichaTests {
	
	@Autowired
	IFichaService service;
	
	@Test
	public void shouldCreateFicha() {
		FichaPaciente f = new FichaPaciente();
		f.setNomePaciente("Yarlei");
		f.setIdFicha(10);
		FichaPaciente res = service.cadastrar(f);
		assertTrue(res != null && res.getUuid() != null && res.getAtivo() == 1);
	}
	
	@Test
	public void shouldDeleteFicha() {
		assertTrue(service.excluir(10));
	}
	
	@Test
	public void shouldNotDeleteFicha() {
		assertFalse(service.excluir(128734));
	}
	
	@Test
	public void shouldReturnSeveralFicha() {
		List<FichaPaciente> lista = service.buscaPorNome("a");
		assertTrue(lista.size() > 0);
	}
	
	@Test
	public void shouldNotFindFicha() {
		List<FichaPaciente> lista = service.buscaPorNome("Adamastor");
		assertTrue(lista.size() == 0);
	}
}

