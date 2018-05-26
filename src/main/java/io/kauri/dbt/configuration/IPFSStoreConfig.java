package io.kauri.dbt.configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.consensys.tools.ipfs.ipfsstore.client.java.IPFSStore;
import net.consensys.tools.ipfs.ipfsstore.client.java.exception.IPFSStoreException;
import net.consensys.tools.ipfs.ipfsstore.client.java.wrapper.IPFSStoreWrapper;
import net.consensys.tools.ipfs.ipfsstore.service.StoreService;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "ipfs-document-persiter")
public class IPFSStoreConfig implements InitializingBean {

    private static final Logger log = Logger.getLogger(IPFSStoreConfig.class);
 
    private Map<String, IndexConfiguration> indexes;

    @Autowired
    private StoreService storeService;
    private IPFSStore client;
    private IPFSStoreWrapper wrapper;

    @PostConstruct
    public void init() {
        wrapper = new IPFSStoreWrapperRESTShortCircuit(storeService);
        client = new IPFSStore(wrapper);
    }
    
    @Bean
    IPFSStoreWrapper wrapper() {
        return wrapper;
    }
    
    @Bean
    IPFSStore client() {
        return client;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        indexes.forEach((k,v)->{
            try {
                log.info("Registering Index " + v.getName() + "...");
                client.createIndex(v.getName());
            } catch (IPFSStoreException e) {
                log.error("Error while registering the index " + v.getName(), e);
            }
        });
    }
    
    public Map<String, IndexConfiguration> getIndexes() {
        return indexes;
    }
    public void setIndexes(Map<String, IndexConfiguration> indexes) {
        this.indexes = indexes;
    }
    
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
    
}
