package io.kauri.dbt.model.dto;

import java.util.Date;

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
    private double totalTip;
    private Status status;
    private String contentHash;
    private Date dateCreated;
    private Date dateUpdated;
    private String blogName;

    public BlogPost(String id, String user, String title, String content) {
        this(user, title, content);
        this.id = id;
    }
    public BlogPost(String user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }
    
    
    
}
