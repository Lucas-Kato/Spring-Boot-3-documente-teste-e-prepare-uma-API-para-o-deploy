package br.com.med.voll.api.dto.consulta.validacoes.agendamento;

import br.com.med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

@Component("ValidadorHorarioAntecedenciaAgendamento")
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsultas {

    @Override
    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {

    }
}
