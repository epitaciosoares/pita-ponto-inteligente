package com.example.pontointeligente.api.controllers;

import com.example.pontointeligente.api.dtos.CadastroPFDto;
import com.example.pontointeligente.api.entities.Empresa;
import com.example.pontointeligente.api.entities.Funcionario;
import com.example.pontointeligente.api.enums.PerfilEnum;
import com.example.pontointeligente.api.response.Response;
import com.example.pontointeligente.api.services.EmpresaService;
import com.example.pontointeligente.api.services.FuncionarioService;
import com.example.pontointeligente.api.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping("/api/cadastrar-pf")
@CrossOrigin(origins = "*")
public class CadastroPFController {

    private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private EmpresaService empresaService;

    public CadastroPFController() {
    }

    @PostMapping
    public ResponseEntity<Response<CadastroPFDto>> cadastrar(@Valid @RequestBody CadastroPFDto cadastroPFDto
            , BindingResult result) throws NoSuchAlgorithmException {

        log.info("Cadastro PF: {}", cadastroPFDto.toString());
        Response<CadastroPFDto> response = new Response<>();

        validarDadosExistentes(cadastroPFDto, result);
        Funcionario funcionario = this.converterDtoFuncionario(cadastroPFDto, result);

        if (result.hasErrors()) {
            log.error("Erro validando dados de cadastro de PF: {}", result.getAllErrors());
            result.getAllErrors().forEach(erro -> response.getErrors().add(erro.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Empresa> empresa = this.empresaService.buscarPorCnjp(cadastroPFDto.getCnpj());
        empresa.ifPresent(funcionario::setEmpresa);
        this.funcionarioService.persistir(funcionario);

        response.setData(this.converterCadastroDto(funcionario));
        return ResponseEntity.ok(response);
    }

    private CadastroPFDto converterCadastroDto(Funcionario funcionario) {
        CadastroPFDto cadastroPFDto = new CadastroPFDto();
        cadastroPFDto.setId(funcionario.getId());
        cadastroPFDto.setNome(funcionario.getNome());
        cadastroPFDto.setEmail(funcionario.getEmail());
        cadastroPFDto.setCpf(funcionario.getCpf());
        cadastroPFDto.setCnpj(funcionario.getEmpresa().getCnpj());
        funcionario.getQtdHorasAlmocoOpt().ifPresent(horas -> cadastroPFDto.setQtdHorasAlmoco(Optional.of(Float.toString(horas))));
        funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(horas -> cadastroPFDto.setQtdHorasTrabalhadasDia(Optional.of(Float.toString(horas))));
        funcionario.getValorHoraOpt().ifPresent(horas -> cadastroPFDto.setValorHora(Optional.of(horas.toString())));
        return cadastroPFDto;
    }

    private Funcionario converterDtoFuncionario(CadastroPFDto cadastroPFDto, BindingResult result) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(cadastroPFDto.getNome());
        funcionario.setEmail(cadastroPFDto.getEmail());
        funcionario.setCpf(cadastroPFDto.getCpf());
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPFDto.getSenha()));
        cadastroPFDto.getQtdHorasAlmoco().ifPresent(qdtHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qdtHorasAlmoco)));
        cadastroPFDto.getQtdHorasTrabalhadasDia().ifPresent(qdtHorasTrabDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qdtHorasTrabDia)));
        cadastroPFDto.getValorHora().ifPresent(vlrHora -> funcionario.setValorHora(new BigDecimal(vlrHora)));
        return funcionario;
    }

    private void validarDadosExistentes(CadastroPFDto cadastroPFDto, BindingResult result) {
        Optional<Empresa> empresa = this.empresaService.buscarPorCnjp(cadastroPFDto.getCnpj());
        if (!empresa.isPresent()) {
            result.addError(new ObjectError("empresa", "Empresa não cadastrada."));
        }

        this.funcionarioService.buscarPorCpf(cadastroPFDto.getCpf()).ifPresent(func -> result.addError(new ObjectError("funcionario", "Cpf já Existente.")));
        this.funcionarioService.buscarPorEmail(cadastroPFDto.getEmail()).ifPresent(func -> result.addError(new ObjectError("Email", "Email já Existente.")));

    }
}
