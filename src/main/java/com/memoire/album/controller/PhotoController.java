package com.memoire.album.controller;

import com.memoire.album.dto.PhotoDto;
import com.memoire.album.model.Photo;
import com.memoire.album.service.PhotoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/photos")
@CrossOrigin(origins = "*") // tighten in production
public class PhotoController {

    private final PhotoService service;

    public PhotoController(PhotoService service) {
        this.service = service;
    }

    /** GET /api/photos — list all photos */
    @GetMapping
    public List<PhotoDto> list() {
        return service.getAll().stream().map(PhotoDto::from).collect(Collectors.toList());
    }

    /** GET /api/photos/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<PhotoDto> get(@PathVariable String id) {
        return ResponseEntity.ok(PhotoDto.from(service.findById(id)));
    }

    /**
     * GET /api/photos/{id}/image — raw image bytes (used by <img src="..."> in the
     * frontend)
     */
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> image(@PathVariable String id) {
        Photo p = service.findById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, p.getImageType())
                .header(HttpHeaders.CACHE_CONTROL, "max-age=86400")
                .body(p.getImageData());
    }

    /** POST /api/photos — create */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PhotoDto> create(
            @RequestParam("image") MultipartFile image,
            @RequestParam("place") String place,
            @RequestParam("lat") double lat,
            @RequestParam("lng") double lng,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "notes", required = false, defaultValue = "") String notes,
            @RequestParam(value = "planId", required = false, defaultValue = "") String planId) throws IOException {
        Photo saved = service.create(image, place, lat, lng, date, notes, planId);
        return ResponseEntity.status(201).body(PhotoDto.from(saved));
    }

    /** DELETE /api/photos/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}