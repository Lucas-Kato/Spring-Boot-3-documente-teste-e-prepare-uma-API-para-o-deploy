package br.com.med.voll.api.dto.consulta.validacoes.agendamento;

import br.com.med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import br.com.med.voll.api.infra.exception.ValidacaoException;
import br.com.med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsultas{

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta){
        var primeiroHoario = dadosAgendamentoConsulta.data().withHour(7);
        var ultimoHorario = dadosAgendamentoConsulta.data().withHour(18);
        var pacientePossuiOutraConsultaNoDIa = consultaRepository.existByPacienteIdAndDataBetween(dadosAgendamentoConsulta.idPaciente(), primeiroHoario, ultimoHorario);

        if (pacientePossuiOutraConsultaNoDIa){
            throw new ValidacaoException("Paciente já possui uma consulta agendada nesse dia");
        }
    }
}
