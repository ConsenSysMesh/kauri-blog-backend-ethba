package io.kauri.dbt.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    @Id
    private String user;
    private String name;
    private double totalTips;
}
