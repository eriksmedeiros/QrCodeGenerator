package com.br.erik.qrcode.generator.ports;

// Interface para o servico de armazenamento
public interface StoragePort {
    String uploadFile(byte[] fileData, String fileName, String contentType);
}