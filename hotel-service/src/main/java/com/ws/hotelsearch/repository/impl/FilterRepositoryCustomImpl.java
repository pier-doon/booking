package com.ws.hotelsearch.repository.impl;

import com.ws.hotelsearch.repository.FilterRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class FilterRepositoryCustomImpl implements FilterRepositoryCustom {

    private final MongoTemplate mongo;

    @Autowired
    public FilterRepositoryCustomImpl(MongoTemplate mongo) {
        this.mongo = mongo;
    }


}