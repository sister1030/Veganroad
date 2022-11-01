package model;

import lombok.Data;

import java.sql.Date;

@Data
public class ReviewDTO {

    private int review_id, store_id;
    private String id, title, content;
    private Date register;

}
