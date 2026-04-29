package com.memoire.album.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plan_items")
public class PlanItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /** Auto-incremented display number, e.g. 1, 2, 3 */
    @Column(nullable = false)
    private int num;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private double lat;

    @Column(nullable = false)
    private double lng;

    private LocalDate date;

    @Lob
    private String notes;

    /**
     * Photos attached to this plan.
     * mappedBy = "planItem" means Photo owns the FK column.
     * cascade MERGE/PERSIST — but NOT REMOVE, so deleting a plan
     * does NOT cascade-delete the photos (we handle that manually).
     * orphanRemoval = false for the same reason.
     */
    @OneToMany(mappedBy = "planItem", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
    private List<Photo> photos = new ArrayList<>();

    // ── Getters & Setters ─────────────────────────────────────────────────────
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> p) {
        this.photos = p;
    }

}