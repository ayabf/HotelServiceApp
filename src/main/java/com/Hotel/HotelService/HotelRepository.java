package com.Hotel.HotelService;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByNameAndAddress(String name, String address);
    List<Hotel> findByName(String name);
    List<Hotel> findByAddress(String address);
    List<Hotel> findAll();

}
