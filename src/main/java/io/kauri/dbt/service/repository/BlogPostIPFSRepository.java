package io.kauri.dbt.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import io.kauri.dbt.configuration.IPFSStoreConfig;
import io.kauri.dbt.model.Document;
import io.kauri.dbt.model.dto.filter.BlogPostFilter;
import net.consensys.tools.ipfs.ipfsstore.client.java.IPFSStore;
import net.consensys.tools.ipfs.ipfsstore.client.springdata.impl.IPFSStoreRepositoryImpl;
import net.consensys.tools.ipfs.ipfsstore.dto.query.Query;

@Repository
public class BlogPostIPFSRepository extends IPFSStoreRepositoryImpl<Document, String> {
    private static final String KEY = "blogpost";
    
    @Autowired
    public BlogPostIPFSRepository(IPFSStoreConfig config, IPFSStore client) {
        super(client, 
              config.getIndexes().get(KEY).getName(),
              config.getIndexes().get(KEY).getFields(),
              config.getIndexes().get(KEY).getFullTextFields(),
              Document.class);
    }
    
    public Page<Document> findByFilter(PageRequest pagination, BlogPostFilter filter) {
        Query query = Query.newQuery();
        
        if(filter != null) {
            if(filter.getFullText() != null && !filter.getFullText().isEmpty()) {
                query.fullText(fullTextFields.toArray(new String[fullTextFields.size()]), filter.getFullText());
            }
            if(filter.getDateCreatedLessThan() != null) {
                query.lessThan("date_created", filter.getDateCreatedLessThan().getTime());
            }
            if(filter.getDateCreatedGreaterThan() != null) {
                query.greaterThan("date_created", filter.getDateCreatedGreaterThan().getTime());
            }
            if(filter.getDateUpdatedLessThan() != null || filter.getDateUpdatedLessThan() != null) {
                query.lessThan("date_updated", filter.getDateUpdatedLessThan().getTime());
            }
            if(filter.getDateUpdatedGreaterThan() != null) {
                query.greaterThan("date_updated", filter.getDateUpdatedGreaterThan().getTime());
            }
            if(filter.getUserIdEqual() != null) {
                query.equals("user_id", filter.getUserIdEqual().toLowerCase());
            }
            if(filter.getStatusIn() != null && filter.getStatusIn().length > 0) {
                query.in("status", filter.getStatusIn());
            }
            
        }
        
        return this.search(query, pagination);
    }
}

