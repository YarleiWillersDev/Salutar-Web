package br.com.isiflix.salutar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.isiflix.salutar.model.Midia;
import br.com.isiflix.salutar.service.midia.IMidiaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@CrossOrigin("*")
public class MidiaController {

    @Autowired
    private IMidiaService service;

    @GetMapping("/midias/{id}")
    public ResponseEntity<Midia> recuperaPeloId(@PathVariable Integer id) {
        Midia midia = service.recuperarPeloId(id);
        if (midia != null) {
            return ResponseEntity.ok(midia);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/midias")
    public ResponseEntity<Midia> adicionarNova(@RequestBody Midia midia) {
        Midia novaMidia = service.cadastrarNova(midia);
        if(novaMidia != null) {
            return ResponseEntity.status(201).body(novaMidia);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/midias/{id}")
    public ResponseEntity<Midia> alterarDados(@RequestBody Midia midia, @PathVariable Integer id) {
        if (midia.getNumeroSequencial() == null) {
            midia.setNumeroSequencial(id);
        }
        Midia resultado = service.alterarDados(midia);
        if (resultado != null) {
            return ResponseEntity.ok(resultado);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/midias/{id}")
    public ResponseEntity<?> excluirMidia(@PathVariable Integer id) {
        if (service.excluir(id)) {
            return ResponseEntity.ok("Ok");
        }
        return ResponseEntity.notFound().build();
    }
}
