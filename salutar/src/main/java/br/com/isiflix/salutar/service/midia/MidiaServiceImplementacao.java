package br.com.isiflix.salutar.service.midia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.isiflix.salutar.dao.MidiaDAO;
import br.com.isiflix.salutar.model.Midia;

@Component
public class MidiaServiceImplementacao implements IMidiaService{

    @Autowired
    private MidiaDAO dao;

    @Override
    public Midia alterarDados(Midia midia) {
        return dao.save(midia);
    }

    @Override
    public Midia cadastrarNova(Midia midia) {
        return dao.save(midia);
    }

    @Override
    public boolean excluir(Integer id) {
        if (dao.existsById(id)) {
            dao.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Midia recuperarPeloId(Integer id) {
        return dao.findById(id).orElse(null);
    }
}
