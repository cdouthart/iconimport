package com.discoverychurch.icon.contributions;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class GivingRecord {
    private int id;
    private int memberId;
    private Date date;
    private float amount; // stored in db as float
    private String designation;
    private String comment;

}
