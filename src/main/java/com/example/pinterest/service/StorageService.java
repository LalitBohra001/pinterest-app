package com.example.pinterest.service;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.specialized.BlockBlobClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class StorageService {

    private final BlobServiceClient blobServiceClient;
    private final String containerName = "images";

    public StorageService(BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;
        // ensure container exists
        BlobContainerClient container = blobServiceClient.getBlobContainerClient(containerName);
        if (!container.exists()) {
            container = blobServiceClient.createBlobContainer(containerName);
        }
    }

    public String upload(MultipartFile file) throws Exception {
        String blobName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        BlobContainerClient container = blobServiceClient.getBlobContainerClient(containerName);
        BlockBlobClient blob = container.getBlobClient(blobName).getBlockBlobClient();
        try (InputStream is = file.getInputStream()) {
            blob.upload(is, file.getSize(), true);
        }
        return container.getBlobClient(blobName).getBlobUrl();
    }
}
