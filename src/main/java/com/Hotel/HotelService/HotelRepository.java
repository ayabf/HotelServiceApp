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

    List<Hotel> findAll();
    @Query("SELECT h FROM Hotel h WHERE h.checkIn >= :checkIn AND h.checkOut <= :checkOut")
    List<Hotel> findByAvailability(
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );
    List<Hotel> findByDuration(String duration);
    List<Hotel> findByMembers(Integer members);
    @Query("SELECT h FROM Hotel h WHERE h.country = :country AND h.location = :location")
    List<Hotel> findByCountryAndLocation(
            @Param("country") String country,
            @Param("location") String location
    );
    @Query("SELECT DISTINCT country FROM Hotel")
    List<String> findDistinctCountries();

    @Query("SELECT DISTINCT location FROM Hotel")
    List<String> findDistinctLocations();
    List<Hotel> findByNameAndAddressAndCountryAndLocation(String name, String address, String country, String location);

}
