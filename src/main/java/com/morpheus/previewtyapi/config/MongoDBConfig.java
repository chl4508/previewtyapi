package com.morpheus.previewtyapi.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.stereotype.Component;

@Component
public class MongoDBConfig {
    @Value("${spring.data.mongodb.uri}")
    private String mongodb_uri;
    private MongoClient mongoClient;

    public MongoTemplate multiMongoTemplate(String databaseName) {
        if(mongoClient==null) {
            mongoClient = MongoClients.create(mongodb_uri);
        }
        MongoDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(mongoClient, databaseName);
        return new MongoTemplate(factory);
    }

    public GridFSBucket gridFSBucketTemplate(String databaseName){
        if(mongoClient==null) {
            mongoClient = MongoClients.create(mongodb_uri);
        }
        MongoDatabase db = mongoClient.getDatabase(databaseName);
        return GridFSBuckets.create(db);
    }

    public void closeMongoClient(){
        mongoClient.close();
        mongoClient=null;
    }

    /*
    public MongoTemplate mongoTemplate2(String databaseName) {
        MongoClient mongoClient = MongoClients.create(mongodb_uri);
        MongoDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(mongoClient, databaseName);
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(factory), new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null)); return new MongoTemplate(factory, converter);
    }*/


}
