package com.example.pontointeligente.api.controllers;

import com.example.pontointeligente.api.dtos.EmpresaDto;
import com.example.pontointeligente.api.entities.Empresa;
import com.example.pontointeligente.api.response.Response;
import com.example.pontointeligente.api.services.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    /**
     * Retorna uma Empresa dado um CNPJ
     *
     * @return ResponseEntity<Response < EmpresaDto>>
     */
    @GetMapping(value = "/cnpj/{cnpj}")
    public ResponseEntity<Response<EmpresaDto>> buscarPorCnpj(@PathVariable("cnpj") String cnpj) {
        log.info("Buscando empresa por CNPJ: {}", cnpj);
        Response<EmpresaDto> response = new Response<>();
        Optional<Empresa> empresa = empresaService.buscarPorCnjp(cnpj);
        if (!empresa.isPresent()) {
            log.info("Empresa não endontrada para o CNPJ: {}", cnpj);
            response.getErrors().add("Empresa nao encontrada para o CNPJ: " + cnpj);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.conveterEmpresaDTO(empresa.get()));
        return ResponseEntity.ok(response);
    }

    private EmpresaDto conveterEmpresaDTO(Empresa empresa) {
        EmpresaDto empresaDto = new EmpresaDto();
        empresaDto.setId(empresa.getId());
        empresaDto.setCnpj(empresa.getCnpj());
        empresaDto.setRazaoSocial(empresa.getRazaoSocial());
        return empresaDto;
    }
}
