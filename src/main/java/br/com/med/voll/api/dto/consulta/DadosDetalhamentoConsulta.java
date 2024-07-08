package br.com.med.voll.api.dto.consulta;

import br.com.med.voll.api.model.consulta.Consulta;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id,
                                        Long idMedico,
                                        Long idPaciente,
                                        LocalDateTime data) {

    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(consulta.getId(),
                consulta.getMedico().getId(),
                consulta.getPaciente().getId(),
                consulta.getData());
    }
}
