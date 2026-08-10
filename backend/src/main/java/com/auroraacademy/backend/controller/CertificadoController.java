package com.auroraacademy.backend.controller;

import com.auroraacademy.backend.dto.CertificadoResposta;
import com.auroraacademy.backend.models.Certificado;
import com.auroraacademy.backend.service.CertificadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificados")
@CrossOrigin(origins = "*")
public class CertificadoController {

    private final CertificadoService certificadoService;

    public CertificadoController(
            CertificadoService certificadoService
    ) {
        this.certificadoService = certificadoService;
    }

    @PostMapping("/alunos/{alunoId}/cursos/{cursoId}")
    public ResponseEntity<CertificadoResposta> gerar(
            @PathVariable Long alunoId,
            @PathVariable Long cursoId
    ) {
        Certificado certificado =
                certificadoService.gerar(alunoId, cursoId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(converter(certificado));
    }

    @GetMapping("/alunos/{alunoId}")
    public ResponseEntity<List<CertificadoResposta>> listarPorAluno(
            @PathVariable Long alunoId
    ) {
        List<CertificadoResposta> resposta =
                certificadoService
                        .listarPorAluno(alunoId)
                        .stream()
                        .map(this::converter)
                        .toList();

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/validar/{codigoValidacao}")
    public ResponseEntity<CertificadoResposta> validar(
            @PathVariable String codigoValidacao
    ) {
        Certificado certificado =
                certificadoService.buscarPorCodigo(codigoValidacao);

        return ResponseEntity.ok(converter(certificado));
    }

    private CertificadoResposta converter(
            Certificado certificado
    ) {
        return new CertificadoResposta(
                certificado.getId(),
                certificado.getAluno().getId(),
                certificado.getAluno().getNome(),
                certificado.getCurso().getId(),
                certificado.getCurso().getTitulo(),
                certificado.getDataEmissao(),
                certificado.getCodigoValidacao()
        );
    }
}