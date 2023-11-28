package com.Hotel.HotelService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByNameAndAddress(String name, String address);
    List<Hotel> findByName(String name);
    List<Hotel> findByAddress(String address);

    default List<Hotel> findByCountryAndLocationAndCheckInAndCheckOutAndDurationAndMembers(String country, String location, LocalDate checkIn, LocalDate checkOut, Integer duration, Integer members) {
        return null;
    }

    List<Hotel> findAll();
    @Query("SELECT h FROM Hotel h WHERE h.checkIn >= :checkIn AND h.checkOut <= :checkOut")
    List<Hotel> findByAvailability(
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );
    List<Hotel> findByDuration(Integer duration);
    List<Hotel> findByMembers(Integer members);
    @Query("SELECT h FROM Hotel h WHERE h.country = :country AND h.location = :location")
    List<Hotel> findByCountryAndLocation(
            @Param("country") String country,
            @Param("location") String location
    );

}
