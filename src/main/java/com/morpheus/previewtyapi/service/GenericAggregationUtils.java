package com.morpheus.previewtyapi.service;

import com.mongodb.DBObject;
import com.morpheus.previewtyapi.service.impl.GenericAggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

public interface GenericAggregationUtils {

    static AggregationOperation aggregate(String operation, String query) {
        return new GenericAggregationOperation(operation, query);
    }

    static AggregationOperation aggregate(String operation, DBObject query) {
        return new GenericAggregationOperation(operation, query);
    }
}
