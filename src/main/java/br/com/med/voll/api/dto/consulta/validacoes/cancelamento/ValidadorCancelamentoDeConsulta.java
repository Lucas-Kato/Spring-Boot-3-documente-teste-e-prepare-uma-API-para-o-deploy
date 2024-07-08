package br.com.med.voll.api.dto.consulta.validacoes.cancelamento;

import br.com.med.voll.api.dto.consulta.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {

    void validar(DadosCancelamentoConsulta dadosCancelamentoConsulta);
}
