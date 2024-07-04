package br.com.med.voll.api.controller;

import br.com.med.voll.api.dto.usuario.DadosAutenticacaoDTO;
import br.com.med.voll.api.infra.security.DadosTokenJWT;
import br.com.med.voll.api.infra.security.TokenService;
import br.com.med.voll.api.model.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DadosAutenticacaoDTO dados){

        try{
            var authenticationTokentoken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
            var authentication = authenticationManager.authenticate(authenticationTokentoken);

            var tokenJWT = tokenService.token((Usuario) authentication.getPrincipal());

            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
