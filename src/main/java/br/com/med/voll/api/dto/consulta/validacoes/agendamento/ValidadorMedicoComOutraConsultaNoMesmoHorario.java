package br.com.med.voll.api.dto.consulta.validacoes.agendamento;

import br.com.med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import br.com.med.voll.api.infra.exception.ValidacaoException;
import br.com.med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsultas{

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta){
        var medicoPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dadosAgendamentoConsulta.idMedico(), dadosAgendamentoConsulta.data());

        if (medicoPossuiOutraConsultaNoMesmoHorario){
            throw new ValidacaoException("Médico já possui outra consulta agendada nesse mesmo horário");
        }
    }
}
