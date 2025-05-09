package com.br.erik.qrcode.generator.service;

import com.br.erik.qrcode.generator.dtos.QrCodeGenerateResponse;
import com.br.erik.qrcode.generator.ports.StoragePort;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class QrCodeGeneratorService {
    private final StoragePort storage; // interface para o servico de armazenamento

    public QrCodeGeneratorService(StoragePort storage) {
        this.storage = storage;
    }

    // metodo para gerar QR code e fazer upload da imagem
    public QrCodeGenerateResponse generateAndUploadQrCode(String text) throws WriterException, IOException {
        // Inicializa o gerador de QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        // Gera uma matrix para o QR code
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        // buffer de saída para imagem
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // Escreve a imagem no buffer
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        // Converte para array de bytes
        byte[] qrCodeData = outputStream.toByteArray();

        // Faz o upload da imagem para o servico de armazenamento
        String url = storage.uploadFile(qrCodeData, UUID.randomUUID().toString(), "image/png");

        // Retorna a URL do QR code gerado
        return new QrCodeGenerateResponse(url);
    }

}
