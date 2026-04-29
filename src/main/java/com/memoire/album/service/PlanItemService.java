package com.memoire.album.service;

import com.memoire.album.dto.PlanItemRequest;
import com.memoire.album.model.Photo;
import com.memoire.album.model.PlanItem;
import com.memoire.album.repository.PhotoRepository;
import com.memoire.album.repository.PlanItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlanItemService {

    private final PlanItemRepository planRepo;
    private final PhotoRepository photoRepo;

    public PlanItemService(PlanItemRepository planRepo, PhotoRepository photoRepo) {
        this.planRepo = planRepo;
        this.photoRepo = photoRepo;
    }

    public List<PlanItem> getAll() {
        return planRepo.findAllByOrderByNumAsc();
    }

    public PlanItem findById(String id) {
        return planRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found: " + id));
    }

    /** Create a new plan; num is auto-incremented from the current max */
    @Transactional
    public PlanItem create(PlanItemRequest req) {
        PlanItem pl = new PlanItem();
        int nextNum = planRepo.findMaxNum() + 1;
        pl.setNum(nextNum);
        pl.setTitle(req.title != null && !req.title.isBlank()
                ? req.title
                : "Plan " + nextNum);
        pl.setLat(req.lat);
        pl.setLng(req.lng);
        pl.setDate(req.date);
        pl.setNotes(req.notes);
        return planRepo.save(pl);
    }

    /** Update metadata only — photo linkage is handled separately */
    @Transactional
    public PlanItem update(String id, PlanItemRequest req) {
        PlanItem pl = findById(id);
        if (req.title != null && !req.title.isBlank())
            pl.setTitle(req.title);
        pl.setLat(req.lat);
        pl.setLng(req.lng);
        pl.setDate(req.date);
        pl.setNotes(req.notes);
        return planRepo.save(pl);
    }

    /**
     * Attach a photo to this plan.
     * Sets photo.planItem = plan and saves.
     */
    @Transactional
    public PlanItem attachPhoto(String planId, String photoId) {
        PlanItem pl = findById(planId);
        Photo photo = photoRepo.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Photo not found: " + photoId));
        photo.setPlanItem(pl);
        photoRepo.save(photo);
        pl.getPhotos().add(photo);
        return pl;
    }

    /**
     * Delete a plan. Detaches all linked photos first
     * (sets their planItem to null) so photos are not deleted.
     */
    @Transactional
    public void delete(String id) {
        PlanItem pl = findById(id);
        pl.getPhotos().forEach(photo -> {
            photo.setPlanItem(null);
            photoRepo.save(photo);
        });
        pl.getPhotos().clear();
        planRepo.delete(pl);
    }

}