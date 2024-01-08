package com.Hotel.HotelService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface HotelRepository extends MongoRepository<Hotel, String> {
    Page<Hotel> findAll( Pageable pageable);
    @Aggregation(pipeline = { "{ '$group': { '_id' : '$country' } }" })
    List<String> findDistinctCountries();

    @Aggregation(pipeline = { "{ '$group': { '_id' : '$location' } }" })
    List<String> findDistinctLocations();
}
