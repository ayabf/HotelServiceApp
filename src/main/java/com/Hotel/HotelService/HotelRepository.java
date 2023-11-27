package com.Hotel.HotelService;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByNameAndAddress(String name, String address);
    List<Hotel> findByName(String name);
    List<Hotel> findByAddress(String address);

    default List<Hotel> findByCountryAndLocationAndCheckInAndCheckOutAndDurationAndMembers(String country, String location, LocalDate checkIn, LocalDate checkOut, int duration, int members) {
        return null;
    }

    List<Hotel> findAll();
}
