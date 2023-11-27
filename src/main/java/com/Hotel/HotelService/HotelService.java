package com.Hotel.HotelService;

import java.time.LocalDate;
import java.util.List;

public interface HotelService {
    List<Hotel> getAllHotels();
    Hotel getHotelById(Long id);
    Hotel createHotel(Hotel hotel);
    Hotel updateHotel(Long id, Hotel hotel);
    void deleteHotel(Long id);
    List<Hotel> searchHotels(String name, String address, String country, String location, LocalDate checkIn, LocalDate checkOut, Integer duration, Integer members);
}
