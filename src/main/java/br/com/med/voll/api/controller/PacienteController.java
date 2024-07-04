package br.com.med.voll.api.controller;

import br.com.med.voll.api.dto.paciente.DadosAtualizarPacienteDTO;
import br.com.med.voll.api.dto.paciente.DadosCadastroPacienteDTO;
import br.com.med.voll.api.dto.paciente.DadosDetalhamentoPaciente;
import br.com.med.voll.api.dto.paciente.DadosListagemPacienteDTO;
import br.com.med.voll.api.model.paciente.Paciente;
import br.com.med.voll.api.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPacienteDTO dados, UriComponentsBuilder uriComponentsBuilder){
        var paciente = new Paciente((dados));

       pacienteRepository.save(paciente);

       var uri = uriComponentsBuilder.path("pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
       return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPacienteDTO>> listar(@PageableDefault(size = 10, sort = "nome") Pageable paginacao){
        var page = pacienteRepository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemPacienteDTO::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizarPacienteDTO dados){
        var paciente = pacienteRepository.getReferenceById(dados.id());

        paciente.atualizarInformacaoPaciente(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);

        paciente.desativarPaciente();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }
}
