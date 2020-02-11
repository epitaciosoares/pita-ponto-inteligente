package com.example.pontointeligente.api.services;

import com.example.pontointeligente.api.entities.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface LancamentoService {
    Page<Lancamento> buscarPorFuncionarioId(Long id, PageRequest pageRequest);

    Optional<Lancamento> buscarPorId(Long id);

    Lancamento persistir(Lancamento lancamento);

    void remover(Long id);
}
