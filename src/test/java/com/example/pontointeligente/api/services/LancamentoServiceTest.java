package com.example.pontointeligente.api.services;


import com.example.pontointeligente.api.entities.Lancamento;
import com.example.pontointeligente.api.repositories.LancamentoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoServiceTest {

    @MockBean
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private LancamentoService lancamentoService;

    @Before
    public void setup() {
        BDDMockito.given(lancamentoRepository.findByFuncionarioId(Mockito.anyLong(), Mockito.any(PageRequest.class))).willReturn(new PageImpl(new ArrayList<Lancamento>()));
        BDDMockito.given(lancamentoRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Lancamento()));
        BDDMockito.given(lancamentoRepository.save(Mockito.any(Lancamento.class))).willReturn(new Lancamento());
    }

    @Test
    public void testeBuscarLancamentoPorFuncionarioId() {
        Page<Lancamento> lancamento = this.lancamentoService.buscarPorFuncionarioId(1L, PageRequest.of(0, 10));

        assertNotNull(lancamento);
    }

    @Test
    public void testeBuscarLancamentoPorId() {
        Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(1L);

        assertNotNull(lancamento);
    }

    @Test
    public void testePersistirLancamento() {
        Lancamento lancamento = this.lancamentoService.persistir(new Lancamento());

        assertNotNull(lancamento);
    }

}
