package br.com.med.voll.api.controller;

import br.com.med.voll.api.dto.medico.DadosAtualizarMedicoDTO;
import br.com.med.voll.api.dto.medico.DadosCadastroMedicoDTO;
import br.com.med.voll.api.dto.medico.DadosDetalhamentoMedico;
import br.com.med.voll.api.dto.medico.DadosListagemMedicoDTO;
import br.com.med.voll.api.model.medico.Medico;
import br.com.med.voll.api.repository.MedicoRepository;
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
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedicoDTO dados, UriComponentsBuilder uriComponentsBuilder){

        var medico = new Medico(dados);

        medicoRepository.save(medico);

        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedicoDTO>> listar(@PageableDefault(size = 10, sort = "nome") Pageable paginacao){
        var page = medicoRepository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemMedicoDTO::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizarMedicoDTO dados){
        var medico = medicoRepository.getReferenceById(dados.id());

        medico.atualizarInformacaoMedico(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var medico = medicoRepository.getReferenceById(id);

        medico.desativarMedico();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var medico = medicoRepository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}
