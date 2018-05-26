package io.kauri.dbt.service.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.kauri.dbt.configuration.IPFSStoreConfig;
import io.kauri.dbt.model.Document;
import net.consensys.tools.ipfs.ipfsstore.client.java.IPFSStore;
import net.consensys.tools.ipfs.ipfsstore.client.springdata.impl.IPFSStoreRepositoryImpl;

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

}

