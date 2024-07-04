package br.com.med.voll.api.dto.paciente;

import br.com.med.voll.api.model.endereco.Endereco;
import br.com.med.voll.api.model.paciente.Paciente;

public record DadosDetalhamentoPaciente(String nome,
                                        String email,
                                        String telefone,
                                        String cpf,
                                        Endereco endereco) {

    public DadosDetalhamentoPaciente(Paciente paciente){
        this(   paciente.getNome(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getCpf(),
                paciente.getEndereco());
    }
}
