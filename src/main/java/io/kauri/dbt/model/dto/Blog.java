package io.kauri.dbt.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    private String user;
    private String name;
    private double tipAmount;
}
