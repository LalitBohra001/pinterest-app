package com.example.pinterest.controller;

import com.example.pinterest.model.ImageMeta;
import com.example.pinterest.repository.ImageMetaRepository;
import com.example.pinterest.service.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final StorageService storageService;
    private final ImageMetaRepository repo;

    public ImageController(StorageService storageService, ImageMetaRepository repo) {
        this.storageService = storageService;
        this.repo = repo;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageMeta> upload(@RequestParam("file") MultipartFile file,
                                            @RequestParam(required = false) String title,
                                            @RequestParam(required = false) String description) throws Exception {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        String url = storageService.upload(file);

        ImageMeta meta = new ImageMeta();
        meta.setFilename(file.getOriginalFilename());
        meta.setBlobUrl(url);
        meta.setTitle(title == null ? file.getOriginalFilename() : title);
        meta.setDescription(description);
        meta.setUploader("anonymous");
        meta.setCreatedAt(Instant.now());
        repo.save(meta);

        return ResponseEntity.status(HttpStatus.CREATED).body(meta);
    }

    @GetMapping
    public List<ImageMeta> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageMeta> get(@PathVariable Long id) {
        Optional<ImageMeta> o = repo.findById(id);
        return o.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<ImageMeta> like(@PathVariable Long id) {
        Optional<ImageMeta> o = repo.findById(id);
        if (o.isEmpty()) return ResponseEntity.notFound().build();
        ImageMeta m = o.get();
        m.setLikes(m.getLikes() == null ? 1 : m.getLikes() + 1);
        repo.save(m);
        return ResponseEntity.ok(m);
    }
}
