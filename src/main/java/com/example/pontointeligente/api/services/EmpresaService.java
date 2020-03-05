package com.example.pontointeligente.api.services;

import com.example.pontointeligente.api.entities.Empresa;

import java.util.Optional;

public interface EmpresaService {

    /**
     * Retorna uma empresa dado um CNPJ
     * @param cnjp
     *
     * */
    Optional<Empresa> buscarPorCnjp(String cnjp);

    Empresa persistir(Empresa empresa);
}
