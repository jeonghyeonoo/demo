package com.memoire.album.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private double lat;

    @Column(nullable = false)
    private double lng;

    private LocalDate date;

    @Lob
    private String notes;

    /** Base64 data URL or a cloud storage URL (e.g. S3) */
    @Lob
    @Column(nullable = false)
    private byte[] imageData;

    @Column(nullable = false)
    private String imageType;

    /**
     * Nullable FK to plan_items.
     * null = standalone photo (no plan)
     * set = this photo belongs to that plan
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_item_id", nullable = true)
    private PlanItem planItem;

    // ── Getters & Setters ─────────────────────────────────────────

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] d) {
        this.imageData = d;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String t) {
        this.imageType = t;
    }

    public PlanItem getPlanItem() {
        return planItem;
    }

    public void setPlanItem(PlanItem p) {
        this.planItem = p;
    }

}