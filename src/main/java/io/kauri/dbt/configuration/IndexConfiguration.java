package io.kauri.dbt.configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexConfiguration {
    private static final String SEPARATOR = ",";
    
    private String name;
    private String fields;
    private String fullTextFields;
    
    public Set<String> getFields() {
        if(fields == null || fields.isEmpty()) {
            return null;
        }
        String[] values = fields.split(SEPARATOR);
        return new HashSet<String>(Arrays.asList(values));
    }
    
    public Set<String> getFullTextFields() {
        if(fullTextFields == null || fullTextFields.isEmpty()) {
            return null;
        }
        String[] values = fullTextFields.split(SEPARATOR);
        return new HashSet<String>(Arrays.asList(values));
    }
  
}