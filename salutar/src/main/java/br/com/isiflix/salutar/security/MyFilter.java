package br.com.isiflix.salutar.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //VALIDAÇÃO
        if (request.getHeader("Authorization") != null) {
            Authentication auth = TokenUtil.decode(request);
            if (auth != null){
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            else {
                System.out.println("Erro!");
                response.setStatus(401);
                return;
            }

        }
   
        // ENCAMINHA REQUISIÇÃO
        filterChain.doFilter(request, response); 
    }

}
