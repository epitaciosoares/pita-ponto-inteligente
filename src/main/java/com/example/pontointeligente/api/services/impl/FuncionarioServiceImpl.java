package com.example.pontointeligente.api.services.impl;


import com.example.pontointeligente.api.entities.Funcionario;
import com.example.pontointeligente.api.repositories.FuncionarioRepository;
import com.example.pontointeligente.api.services.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
    private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public Funcionario persistir(Funcionario funcionario) {
        log.info("Persistindo funcionario {}", funcionario);
        return this.funcionarioRepository.save(funcionario);
    }

    @Override
    public Optional<Funcionario> buscarPorCpf(String cpf) {
        log.info("Buscando funcionario cpf {}", cpf);
        return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
    }

    @Override
    public Optional<Funcionario> buscarPorEmail(String email) {
        log.info("Buscando funcionario email {}", email);
        return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
    }

    @Override
    public Optional<Funcionario> buscarPorId(Long id) {
        log.info("Buscando funcionario por id {}", id);
        return this.funcionarioRepository.findById(id);
    }
}
