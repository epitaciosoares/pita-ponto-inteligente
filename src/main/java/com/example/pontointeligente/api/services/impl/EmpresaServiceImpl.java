package com.example.pontointeligente.api.services.impl;


import com.example.pontointeligente.api.entities.Empresa;
import com.example.pontointeligente.api.repositories.EmpresaRepository;
import com.example.pontointeligente.api.services.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpresaServiceImpl implements EmpresaService {
    private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);

    @Autowired
    EmpresaRepository empresaRepository;

    @Override
    public Optional<Empresa> buscarPorCnjp(String cnjp) {
        log.info("buscando empresa cnpj {}", cnjp);
        return Optional.ofNullable(empresaRepository.findByCnpj(cnjp));
    }

    @Override
    public Empresa persistir(Empresa empresa) {
        log.info("persistindo empresa {}", empresa);
        return this.empresaRepository.save(empresa);
    }
}
