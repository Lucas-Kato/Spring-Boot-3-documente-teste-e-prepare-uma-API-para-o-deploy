package br.com.med.voll.api.repository;

import br.com.med.voll.api.dto.medico.Especialidade;
import br.com.med.voll.api.model.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;

@EnableJpaRepositories
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    /*
    JPQL
     */
    @Query("""
            SELECT m.*
                   FROM Medico m
                   WHERE m.ativo = true
                     AND m.especialidade = :especialidade
                     AND m.id NOT IN (
                       SELECT c.medico_id
                       FROM Consulta c
                       WHERE c.data = :data
                         AND c.motivo_cancelamento IS NULL
                     )
                   ORDER BY RANDOM()
                   LIMIT 1;
            """)
    Medico medicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

    @Query("""
            SELECT m.ativo
                      FROM Medico m
                      WHERE m.id = :id
            """)
    boolean findAtivoById(Long id);
}
