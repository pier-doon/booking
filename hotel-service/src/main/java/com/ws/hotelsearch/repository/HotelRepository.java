package com.ws.hotelsearch.repository;

import com.ws.hotelsearch.model.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, UUID>, FilterRepositoryCustom {
}
