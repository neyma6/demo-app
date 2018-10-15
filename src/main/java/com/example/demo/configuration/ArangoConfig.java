package com.example.demo.configuration;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.velocypack.VPackAnnotationFieldFilter;
import com.arangodb.velocypack.module.jdk8.VPackJdk8Module;
import com.example.demo.database.ArangoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.Transient;

@Configuration
public class ArangoConfig {

    @Bean
    public ArangoDB getArangoDb(@Autowired ArangoProperties arangoProperties) {
        return createConnection(arangoProperties);
    }

    private ArangoDB createConnection(ArangoProperties arangoProperties) {
        try {
            return new ArangoDB.Builder()
                .host(arangoProperties.getHost(), arangoProperties.getPort())
                .user(arangoProperties.getUser())
                .password(arangoProperties.getPassword())
                .maxConnections(arangoProperties.getMaxConnections())
                //.useProtocol(Protocol.HTTP_VPACK)
                .registerModule(new VPackJdk8Module())
                .connectionTtl(arangoProperties.getConnectionTtl())
                .annotationFieldFilter(Transient.class, new VPackAnnotationFieldFilter<Transient>() {
                    @Override
                    public boolean serialize(final Transient annotation) {
                        return false;
                    }

                    @Override
                    public boolean deserialize(final Transient annotation) {
                        return false;
                    }
                }).build();
        } catch (ArangoDBException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
