package br.com.med.voll.api.model.consulta;

import br.com.med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import br.com.med.voll.api.dto.consulta.DadosCancelamentoConsulta;
import br.com.med.voll.api.dto.consulta.DadosDetalhamentoConsulta;
import br.com.med.voll.api.dto.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsultas;
import br.com.med.voll.api.dto.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import br.com.med.voll.api.infra.exception.ValidacaoException;
import br.com.med.voll.api.model.medico.Medico;
import br.com.med.voll.api.repository.ConsultaRepository;
import br.com.med.voll.api.repository.MedicoRepository;
import br.com.med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private List<ValidadorAgendamentoDeConsultas> validadores;
    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){
        if (!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Id de paciente informando não existe");
        }
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id de médico informando não existe");
        }

        validadores.forEach(v -> v.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);

        if (medico == null){
            throw new ValidacaoException("Não existe médico disponível nessa data!");
        }

        var consulta = new Consulta(null, medico, paciente, dados.data(), null);

        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatória, quando médico não for escolhido!");
        }

        return medicoRepository.medicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())){
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivoCancelamento());
    }
}
