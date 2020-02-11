package com.example.pontointeligente.api.services;

import com.example.pontointeligente.api.entities.Empresa;

import java.util.Optional;

public interface EmpresaService {

    Optional<Empresa> buscarPorCnjp(String cnjp);

    Empresa persistir(Empresa empresa);
}
