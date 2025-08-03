package br.com.isiflix.salutar.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.isiflix.salutar.dao.UsuarioDAO;
import br.com.isiflix.salutar.model.Usuario;
import br.com.isiflix.salutar.security.SalutarToken;
import br.com.isiflix.salutar.security.TokenUtil;

@Component
public class AuthServiceImplementacao implements IAuthService{

    @Autowired
    private UsuarioDAO dao;

    @Override
    public Usuario criarUsuario(Usuario novoUsuario) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        String novaSenha = encoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(novaSenha);
        return dao.save(novoUsuario);
    }

    @Override
    public SalutarToken realizarLogin(Usuario dadosLogin) {

        Usuario resultado = dao.findByLogin(dadosLogin.getLogin());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (resultado != null) {
            if (encoder.matches(dadosLogin.getSenha(), resultado.getSenha())) {
                return TokenUtil.enconde(resultado);
            }
        }
        return null;
    }
}
