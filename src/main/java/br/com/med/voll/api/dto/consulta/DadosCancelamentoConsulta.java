package br.com.med.voll.api.dto.consulta;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(
        @NotNull
        Long idConsulta,
        @NotNull
        MotivoCancelamento motivoCancelamento
) {
}
