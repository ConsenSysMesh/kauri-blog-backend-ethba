package io.kauri.dbt.model.dto.filter;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.kauri.dbt.model.Status;
import lombok.Data;

@Data
public class BlogFilter {

    private String fullText;
    
    private Date dateCreatedLessThan;
    
    private Date dateCreatedGreaterThan;
    
    private Date dateUpdatedLessThan;
    
    private Date dateUpdatedGreaterThan;
    
    private String userIdEqual;
    
    private String titleContain;
    
    private String textContain;
    
    private Status[] statusIn;
    

    @JsonProperty("full_text")
    public String getFullText() {
        return fullText;
    }
    public BlogFilter setFullText(String fullText) {
        this.fullText = fullText;
        return this;
    }
    @JsonProperty("date_created_lt")
    public Date getDateCreatedLessThan() {
        return dateCreatedLessThan;
    }
    public BlogFilter setDateCreatedLessThan(Date dateCreatedLessThan) {
        this.dateCreatedLessThan = dateCreatedLessThan;
        return this;
    }
    @JsonProperty("date_created_gt")
    public Date getDateCreatedGreaterThan() {
        return dateCreatedGreaterThan;
    }
    public BlogFilter setDateCreatedGreaterThan(Date dateCreatedGreaterThan) {
        this.dateCreatedGreaterThan = dateCreatedGreaterThan;
        return this;
    }
    @JsonProperty("date_updated_lt")
    public Date getDateUpdatedLessThan() {
        return dateUpdatedLessThan;
    }
    public BlogFilter setDateUpdatedLessThan(Date dateUpdatedLessThan) {
        this.dateUpdatedLessThan = dateUpdatedLessThan;
        return this;
    }
    @JsonProperty("date_updated_gt")
    public Date getDateCUpdatedreaterThan() {
        return dateUpdatedGreaterThan;
    }
    public BlogFilter setDateUpdatedGreaterThan(Date dateUpdatedGreaterThan) {
        this.dateUpdatedGreaterThan = dateUpdatedGreaterThan;
        return this;
    }
    @JsonProperty("user_id_eq")
    public String getUserIdEqual() {
        return userIdEqual;
    }
    public BlogFilter setUserId(String userIdEqual) {
        this.userIdEqual = userIdEqual;
        return this;
    }
    @JsonProperty("title_ct")
    public String getTitleContain() {
        return titleContain;
    }
    public BlogFilter setTitleContain(String titleContain) {
        this.titleContain = titleContain;
        return this;
    }
    @JsonProperty("text_ct")
    public String getTextContain() {
        return textContain;
    }
    public BlogFilter setTextContain(String textContain) {
        this.textContain = textContain;
        return this;
    }
    @JsonProperty("status_in")
    public Status[] getStatusIn() {
        return statusIn;
    }
    public BlogFilter setStatusIn(Status... statusIn) {
        this.statusIn = statusIn;
        return this;
    }
}
