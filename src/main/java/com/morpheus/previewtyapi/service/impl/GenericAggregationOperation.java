package com.morpheus.previewtyapi.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

public class GenericAggregationOperation implements AggregationOperation {

    private String operator;
    private DBObject query;

    public GenericAggregationOperation(String operator, DBObject query) {
        this.operator = operator;
        this.query = query;
    }

    public GenericAggregationOperation(String operator, String query) {
        this(operator, BasicDBObject.parse(query));
    }

    @Override
    public Document toDocument(AggregationOperationContext context) {
        return new Document(operator, query);
    }
}
