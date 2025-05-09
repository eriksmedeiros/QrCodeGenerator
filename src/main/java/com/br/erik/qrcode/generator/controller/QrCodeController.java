package com.br.erik.qrcode.generator.controller;

import com.br.erik.qrcode.generator.dtos.QrCodeGenerateRequest;
import com.br.erik.qrcode.generator.dtos.QrCodeGenerateResponse;
import com.br.erik.qrcode.generator.service.QrCodeGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

    private final QrCodeGeneratorService qrCodeGeneratorService;

    public QrCodeController(QrCodeGeneratorService qrCodeGeneratorService) {
        this.qrCodeGeneratorService = qrCodeGeneratorService;
    }

    // Metodo que mapeia a requisicao POST para o endpoint /qrcode
    @PostMapping
    public ResponseEntity<QrCodeGenerateResponse> generate(@RequestBody QrCodeGenerateRequest request) {
        try {
             // Gera o QR Code e faz o upload
             QrCodeGenerateResponse response = this.qrCodeGeneratorService.generateAndUploadQrCode(request.text());
             // Retorna HTTP 200 (ok) com os dados do QR Code
             return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Em caso de erro, retorna HTTP 500 (erro interno do servidor)
            return ResponseEntity.internalServerError().build();
        }
    }
}
