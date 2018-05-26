package io.kauri.dbt.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.consensys.tools.ipfs.ipfsstore.client.java.exception.IPFSStoreException;
import net.consensys.tools.ipfs.ipfsstore.client.java.wrapper.IPFSStoreWrapper;
import net.consensys.tools.ipfs.ipfsstore.dto.IndexerRequest;
import net.consensys.tools.ipfs.ipfsstore.dto.IndexerResponse;
import net.consensys.tools.ipfs.ipfsstore.dto.Metadata;
import net.consensys.tools.ipfs.ipfsstore.dto.query.Query;
import net.consensys.tools.ipfs.ipfsstore.exception.ServiceException;
import net.consensys.tools.ipfs.ipfsstore.service.StoreService;

@Component
public class IPFSStoreWrapperRESTShortCircuit implements IPFSStoreWrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(IPFSStoreWrapperRESTShortCircuit.class);

    private ObjectMapper objectMapper;

    private StoreService storeService;
    
    public IPFSStoreWrapperRESTShortCircuit(StoreService storeService) {
        this.storeService = storeService;
        
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public String store(byte[] file) throws IPFSStoreException {
        try {
            return this.storeService.storeFile(file);
        } catch (ServiceException ex) {
            LOGGER.error("Error while storing file[]", ex);
            throw new IPFSStoreException("Error while storing file[]", ex);
        }
    }

    @Override
    public IndexerResponse index(IndexerRequest request) throws IPFSStoreException {
        try {
            return this.storeService.indexFile(request);
        } catch (ServiceException ex) {
            LOGGER.error("Error while indexing file[request="+request+"]", ex);
            throw new IPFSStoreException("Error while indexing file[request="+request+"]", ex);
        }
    }

    @Override
    public IndexerResponse storeAndIndex(byte[] file, IndexerRequest request) throws IPFSStoreException {
        try {
            return this.storeService.storeAndIndexFile(file, request);
        } catch (ServiceException ex) {
            LOGGER.error("Error while storing & indexing file[request="+request+"]", ex);
            throw new IPFSStoreException("Error while storing & indexing file[request="+request+"]", ex);
        }
    }

    @Override
    public byte[] fetch(String indexName, String hash) throws IPFSStoreException {
        try {
            return this.storeService.getFileByHash(hash);
        } catch (ServiceException ex) {
            LOGGER.error("Error while fetching file[indexName="+indexName+", hash="+hash+"]", ex);
            throw new IPFSStoreException("Error while fetching file[indexName="+indexName+", hash="+hash+"]", ex);
        }
    }

    @Override
    public Page<Metadata> search(String indexName, Query query, Pageable pageable) throws IPFSStoreException {
        try {
            return this.storeService.searchFiles(indexName, query, pageable);
        } catch (ServiceException ex) {
            LOGGER.error("Error while searching files [indexName="+indexName+", query="+query+"]", ex);
            throw new IPFSStoreException("Error while searching files [indexName="+indexName+", query="+query+"]", ex);
        }
    }

    @Override
    public void createIndex(String indexName) throws IPFSStoreException {
        try {
            this.storeService.createIndex(indexName);
        } catch (ServiceException ex) {
            LOGGER.error("Error while creating index [indexName="+indexName+"]", ex);
            throw new IPFSStoreException("Error while creating index[indexName="+indexName+"]", ex);
        }  
    }

    @Override
    public RestTemplate getClient() {
        throw new UnsupportedOperationException();
    }

}
