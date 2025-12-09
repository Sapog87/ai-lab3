package ru.m1.ai.lab3.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "langchain4j.pgvector")
public class PgVectorConfigurationProperties {

    private String host;
    private Integer port;
    private String database;
    private String user;
    private String password;
    private String table;

}
