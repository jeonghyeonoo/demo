package com.memoire.album.dto;

import com.memoire.album.model.PlanItem;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PlanItemDto {
    public String id;
    public int num;
    public String title;
    public double lat;
    public double lng;
    public LocalDate date;
    public String notes;
    public List<String> photoIds; // IDs of attached photos — frontend uses this for count

    public static PlanItemDto from(PlanItem pl) {
        PlanItemDto d = new PlanItemDto();
        d.id = pl.getId();
        d.num = pl.getNum();
        d.title = pl.getTitle();
        d.lat = pl.getLat();
        d.lng = pl.getLng();
        d.date = pl.getDate();
        d.notes = pl.getNotes();
        d.photoIds = pl.getPhotos().stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());
        return d;
    }

}