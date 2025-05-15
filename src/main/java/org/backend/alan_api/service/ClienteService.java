package org.backend.alan_api.service;

import org.backend.alan_api.dto.ClienteDTO;
import org.backend.alan_api.exception.CpfExistenteException;
import org.backend.alan_api.exception.EmailExistenteException;
import org.backend.alan_api.model.Cliente;
import org.backend.alan_api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente criarCliente(ClienteDTO clienteDTO) {
        if (clienteRepository.existsByCpf(clienteDTO.getCpf())) {
            throw new CpfExistenteException("CPF já cadastrado");
        }

        if (clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            throw new EmailExistenteException("Email já cadastrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setTelefone(clienteDTO.getTelefone());

        if (clienteDTO.getDataNascimento() != null) {
            LocalDate localDate = clienteDTO.getDataNascimento();
            Date dataNascimento = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            cliente.setDataNascimento(dataNascimento);
        }

        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
}