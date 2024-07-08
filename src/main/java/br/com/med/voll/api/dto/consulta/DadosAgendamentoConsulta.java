package br.com.med.voll.api.dto.consulta;

import br.com.med.voll.api.dto.medico.Especialidade;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(Long idMedico,
                                       @NotNull
                                       Long idPaciente,
                                       @NotNull
                                       @Future
                                       LocalDateTime data,
                                       Especialidade especialidade) {

}
