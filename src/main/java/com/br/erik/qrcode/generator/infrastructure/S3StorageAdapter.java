package com.br.erik.qrcode.generator.infrastructure;

import com.br.erik.qrcode.generator.ports.StoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

// Implementacao da Interface StoragePort usando o servico S3 da AWS
@Component
public class S3StorageAdapter implements StoragePort {

    // Cliente S3 da AWS
    private final S3Client s3Client;
    // Nome do bucket onde os arquivos serao armazenados
    private final String bucketName;
    // Regiao da AWS onde o bucket esta localizado
    private final String region;

    // Construtor que inicializa o cliente S3 e as variaveis de configuracao
    public S3StorageAdapter(@Value("${aws.s3.region}") String bucketName,
                            @Value("${aws.s3.bucket-name}") String region) {
        this.bucketName = bucketName;
        this.region = region;
        this.s3Client = S3Client.builder()
                .region(Region.of(this.region))
                .build();
    }

    // Metodo para fazer o upload de um arquivo para o S3
    @Override
    public String uploadFile(byte[] fileData, String fileName, String contentType) {
        // Cria um request para o upload do arquivo
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(contentType) // Define o tipo de conteudo do arquivo
                .build();

        // Faz o upload do arquivo para o S3
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileData));

        // Retorna a URL do arquivo armazenado no S3
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileName);
    }
}
