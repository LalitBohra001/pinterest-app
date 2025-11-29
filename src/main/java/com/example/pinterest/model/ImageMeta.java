package com.example.pinterest.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class ImageMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Lob
    private String description;
    private String blobUrl;
    private String filename;
    private String uploader;
    private Instant createdAt;
    private Integer width;
    private Integer height;
    private Integer likes = 0;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getBlobUrl() { return blobUrl; }
    public void setBlobUrl(String blobUrl) { this.blobUrl = blobUrl; }

    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }

    public String getUploader() { return uploader; }
    public void setUploader(String uploader) { this.uploader = uploader; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Integer getWidth() { return width; }
    public void setWidth(Integer width) { this.width = width; }

    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }

    public Integer getLikes() { return likes; }
    public void setLikes(Integer likes) { this.likes = likes; }
}
