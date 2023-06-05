package com.morpheus.previewtyapi.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MongoService {

    Map<String, Object> mongoFind(String databaseName, String collectionName, String fieldName, String fieldDataName);

    Map<String, Object> mongoFindCount(String databaseName);

    Map<String, Object> mongoFindList(String databaseName);

//    Map<String, Object> mongoInsertList(String databaseName);
//
//    Map<String, Object> mongoUpdateList(String databaseName);
//
//    Map<String, Object> mongoDelete(String databaseName);




}
