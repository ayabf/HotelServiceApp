package com.Hotel.HotelService;

import java.time.LocalDate;
import java.util.List;

public interface HotelService {
    List<Hotel> getAllHotels();
    Hotel getHotelById(Long id);
    Hotel createHotel(Hotel hotel);
    Hotel updateHotel(Long id, Hotel hotel);
    void deleteHotel(Long id);
    List<Hotel> searchHotel(String name, String address);
    List<Hotel> advancedSearchHotels(String name, String address,String country, String location, LocalDate checkIn, LocalDate checkOut, String duration, Integer members);
    List<Hotel> searchHotels(String name, String address);
    List<String> getCountries();

    List<String> getLocations();
    List<Hotel>findByNameAndAddressAndCountryAndLocation(String name, String address, String country, String location);
    List<Hotel> filterByDuration(List<Hotel> hotels, String duration);

}
