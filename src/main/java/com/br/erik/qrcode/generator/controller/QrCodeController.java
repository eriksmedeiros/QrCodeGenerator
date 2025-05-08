package com.br.erik.qrcode.generator.controller;

import com.br.erik.qrcode.generator.dtos.QrCodeGenerateRequest;
import com.br.erik.qrcode.generator.dtos.QrCodeGenerateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

    public ResponseEntity<QrCodeGenerateResponse> generate(@RequestBody QrCodeGenerateRequest request) {
        return null;
    }
}
