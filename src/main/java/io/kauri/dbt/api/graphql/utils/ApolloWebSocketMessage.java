package io.kauri.dbt.api.graphql.utils;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(Include.NON_NULL)
public class ApolloWebSocketMessage {
    public static final String GQL_CONNECTION_INIT = "connection_init"; // Client -> Server
    public static final String GQL_CONNECTION_ACK = "connection_ack"; // Server -> Client
    public static final String GQL_CONNECTION_ERROR = "connection_error"; // Server -> Client
    public static final String GQL_CONNECTION_KEEP_ALIVE = "ka"; // Server -> Client
    public static final String GQL_CONNECTION_TERMINATE = "connection_terminate"; // Client -> Server
    public static final String GQL_START = "start"; // Client -> Server
    public static final String GQL_DATA = "data"; // Server -> Client
    public static final String GQL_ERROR = "error"; // Server -> Client
    public static final String GQL_COMPLETE = "complete"; // Server -> Client
    public static final String GQL_STOP = "stop"; // Client -> Server
    
    private String id;
    private String type;
    private Map<String,Object> payload;
     
}