package com.memoire.album.dto;

import com.memoire.album.model.Photo;
import java.time.LocalDate;

public class PhotoDto {

    public String id;
    public String place;
    public double lat;
    public double lng;
    public LocalDate date;
    public String notes;
    public String planId;

    public static PhotoDto from(Photo p) {
        PhotoDto d = new PhotoDto();
        d.id = p.getId();
        d.place = p.getPlace();
        d.lat = p.getLat();
        d.lng = p.getLng();
        d.date = p.getDate();
        d.notes = p.getNotes();
        d.planId = p.getPlanItem() != null ? p.getPlanItem().getId() : null;

        return d;
    }

}