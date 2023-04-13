package com.ems.entity;

import lombok.Data;

@Data
public class RecordResult extends Record{
    private String username;
    private String couriername;
    private String pakagename;
    private String warehousename;
    private String pakagetypename;
}
