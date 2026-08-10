package com.auroraacademy.backend.dto;

import java.time.LocalDateTime;

public record CertificadoResposta(
    Long id,
    Long alunoId,
    String alunoNome,
    Long cursoId,
    String cursoTitulo,
    LocalDateTime dataEmissao,
    String codigoValidacao
) {
    

}
