package model;

import lombok.Data;

@Data
public class StoreDTO {

    private int store_id, menu_id, capacity;
    private String vegan, category, name, detail, address, contact, open, close, image;
    private float lat, lon;

}
