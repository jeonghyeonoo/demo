package com.memoire.album.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memoire.album.dto.PhotoDto;
import com.memoire.album.dto.PlanItemDto;
import com.memoire.album.dto.PlanItemRequest;
import com.memoire.album.model.PlanItem;
import com.memoire.album.service.PlanItemService;

@RestController
@RequestMapping("/api/plans")
@CrossOrigin(origins = "*")
public class PlanItemController {

    private final PlanItemService service;

    public PlanItemController(PlanItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<PlanItemDto> list() {
        return service.getAll().stream()
                .map(PlanItemDto::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanItemDto> get(@PathVariable String id) {
        return ResponseEntity.ok(PlanItemDto.from(service.findById(id)));
    }

    @GetMapping("/{id}/photos")
    public List<PhotoDto> photos(@PathVariable String id) {
        return service.findById(id).getPhotos().stream()
                .map(PhotoDto::from)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<PlanItemDto> create(@RequestBody PlanItemRequest req) {
        PlanItem saved = service.create(req);
        return ResponseEntity.status(201).body(PlanItemDto.from(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanItemDto> update(@PathVariable String id,
            @RequestBody PlanItemRequest req) {
        return ResponseEntity.ok(PlanItemDto.from(service.update(id, req)));
    }

    @PostMapping("/{id}/photos/{photoId}")
    public ResponseEntity<PlanItemDto> attachPhoto(@PathVariable String id,
            @PathVariable String photoId) {
        return ResponseEntity.ok(PlanItemDto.from(service.attachPhoto(id, photoId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}