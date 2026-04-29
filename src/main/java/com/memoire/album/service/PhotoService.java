package com.memoire.album.service;

import com.memoire.album.model.Photo;
import com.memoire.album.model.PlanItem;
import com.memoire.album.repository.PhotoRepository;
import com.memoire.album.repository.PlanItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class PhotoService {

    private final PhotoRepository photoRepo;
    private final PlanItemRepository planRepo;

    public PhotoService(PhotoRepository photoRepo, PlanItemRepository planRepo) {
        this.photoRepo = photoRepo;
        this.planRepo = planRepo;
    }

    public List<Photo> getAll() {
        return photoRepo.findAllByOrderByDateDesc();
    }

    public Photo findById(String id) {
        return photoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Photo not found: " + id));
    }

    @Transactional
    public Photo create(MultipartFile image, String place,
            double lat, double lng,
            LocalDate date, String notes,
            String planId) throws IOException {
        Photo p = new Photo();
        p.setPlace(place);
        p.setLat(lat);
        p.setLng(lng);
        p.setDate(date);
        p.setNotes(notes);
        p.setImageData(image.getBytes());
        p.setImageType(image.getContentType() != null
                ? image.getContentType()
                : "image/jpeg");

        if (planId != null && !planId.isBlank()) {
            PlanItem pl = planRepo.findById(planId).orElse(null);
            if (pl != null)
                p.setPlanItem(pl);
        }

        return photoRepo.save(p);
    }

    public void delete(String id) {
        Photo photo = findById(id);
        PlanItem plan = photo.getPlanItem();
        if (plan != null) {
            plan.getPhotos().remove(photo);
            photo.setPlanItem(null);
        }
        photoRepo.delete(photo);
    }

}