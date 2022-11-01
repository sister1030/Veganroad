package model;

import lombok.Data;

@Data
public class ReserveDTO {

    private int reserve_id, store_id;
    private String store_name, id, start, end;
    private String reserve;

}
