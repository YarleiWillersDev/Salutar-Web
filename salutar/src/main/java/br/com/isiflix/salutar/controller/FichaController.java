package br.com.isiflix.salutar.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.isiflix.salutar.model.FichaPaciente;
import br.com.isiflix.salutar.service.ficha.IFichaService;

@RestController
@CrossOrigin("*")
public class FichaController {
	
	@Autowired
	private IFichaService service;
	
	@GetMapping("/fichas/busca")
	public ResponseEntity<List<FichaPaciente>> recuperarPeloNome(@RequestParam (name = "nome") String nome) {
		List<FichaPaciente> lista = service.buscaPorNome(nome);
		if (lista.size() > 0) {
			return ResponseEntity.ok(lista);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/fichas")
	public ResponseEntity<FichaPaciente> cadastrarNovaFicha(@RequestBody FichaPaciente novaFicha) throws Exception {
		FichaPaciente resultado = service.cadastrar(novaFicha);
		if(resultado != null) {
			return ResponseEntity.created(new URI("/fichas/" + resultado.getIdFicha())).body(resultado);
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/fichas/{id}")
	public ResponseEntity<FichaPaciente> buscarPeloId(@PathVariable Integer id) {
		FichaPaciente resposta = service.recuperaPeloId(id);
		if (resposta != null) {
			return ResponseEntity.ok(resposta);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/fichas/{id}")
	public ResponseEntity<FichaPaciente> alterarFicha(@RequestBody FichaPaciente ficha, @PathVariable Integer id) {
		if (ficha.getIdFicha() == null) {
			ficha.setIdFicha(id);
		}
		FichaPaciente resposta = service.alterar(ficha);
		if(resposta != null) {
			return ResponseEntity.ok(resposta);
		}
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/fichas/{id}")
	public ResponseEntity<FichaPaciente> excluirFicha(@PathVariable Integer id) {
		boolean resultado = service.excluir(id);
		if (resultado) {
			return ResponseEntity.ok(service.recuperaPeloId(id));
		}
		return ResponseEntity.notFound().build();
	} 
}

