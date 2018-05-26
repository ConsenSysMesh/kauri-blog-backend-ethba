package io.kauri.dbt.model.dto;

import io.kauri.dbt.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogPost {

    private String id;
    private String user;
    private String title;
    private String content;
    private int totalTip;
    private Status status;
    private String contentHash;
    
    public BlogPost(String user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }
    
    
    
}
