package io.kauri.dbt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    private String hash;    
    private String id;
    private String title;
    private String content;
    private String user;
    
}
