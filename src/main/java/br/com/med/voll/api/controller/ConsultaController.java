package br.com.med.voll.api.controller;

import br.com.med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import br.com.med.voll.api.dto.consulta.DadosCancelamentoConsulta;
import br.com.med.voll.api.model.consulta.AgendaDeConsultas;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var dto = agenda.agendar(dados);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados){
        agenda.cancelar(dados);

        return ResponseEntity.noContent().build();
    }
}
