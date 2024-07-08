package br.com.med.voll.api.dto.consulta.validacoes.agendamento;

import br.com.med.voll.api.dto.consulta.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsultas {

    void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta);
}
