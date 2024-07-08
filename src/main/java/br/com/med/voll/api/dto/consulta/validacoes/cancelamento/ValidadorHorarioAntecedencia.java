package br.com.med.voll.api.dto.consulta.validacoes.cancelamento;

import br.com.med.voll.api.dto.consulta.DadosCancelamentoConsulta;
import br.com.med.voll.api.infra.exception.ValidacaoException;
import br.com.med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosCancelamentoConsulta dadosCancelamentoConsulta) {
        var consulta = consultaRepository.getReferenceById(dadosCancelamentoConsulta.idConsulta());
        var agora = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();

        if (diferencaEmHoras < 24){
            throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
        }
    }
}
