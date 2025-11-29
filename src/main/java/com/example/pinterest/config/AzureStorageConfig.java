package com.example.pinterest.config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureStorageConfig {

    @Value("${azure.storage.connection-string:}")
    private String connectionString;

    @Bean
    public BlobServiceClient blobServiceClient() {
        if (connectionString == null || connectionString.isBlank()) {
            // For local development without Azure, this will throw an error only if used.
            throw new IllegalStateException("Set AZURE_STORAGE_CONNECTION_STRING (property azure.storage.connection-string) to use Azure Blob Storage");
        }
        return new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }
}
