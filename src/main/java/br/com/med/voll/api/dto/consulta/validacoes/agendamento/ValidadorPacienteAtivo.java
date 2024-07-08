package br.com.med.voll.api.dto.consulta.validacoes.agendamento;

import br.com.med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import br.com.med.voll.api.infra.exception.ValidacaoException;
import br.com.med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsultas{

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta){
        var pacienteEstaAtivo = pacienteRepository.findAtivoById(dadosAgendamentoConsulta.idPaciente());

        if (!pacienteEstaAtivo){
            throw  new ValidacaoException("Consulta não pode ser agendada com paciente excluído");
        }
    }
}
