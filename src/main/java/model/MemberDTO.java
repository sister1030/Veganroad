package model;

import lombok.Data;

import java.sql.Date;

@Data
public class MemberDTO {

    private String id, password, name, email, contact, address, vegan, status;
    private int auth;
    private Date register;

}
