package br.com.med.voll.api.dto.consulta.validacoes.agendamento;

import br.com.med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import br.com.med.voll.api.infra.exception.ValidacaoException;
import br.com.med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsultas{

    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta){
        //escolha do medico opcional
        if (dadosAgendamentoConsulta.idMedico() == null){
            return;
        }

        var medicoEstaAtivo = medicoRepository.findAtivoById(dadosAgendamentoConsulta.idMedico());
        if (!medicoEstaAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada com médico excluído");
        }
    }
}
