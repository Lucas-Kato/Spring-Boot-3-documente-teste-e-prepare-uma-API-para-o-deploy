package br.com.med.voll.api.repository;

import br.com.med.voll.api.model.consulta.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(Long idMedico, LocalDateTime data);

    boolean existByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHoario, LocalDateTime ultimoHorario);
}
