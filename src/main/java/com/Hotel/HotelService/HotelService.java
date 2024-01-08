package com.Hotel.HotelService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface HotelService {
    Page<Hotel> getAllHotels(Pageable pageable);
    Hotel getHotelById(String id);
    Hotel createHotel(Hotel hotel);
    void updateHotel(String id, Hotel hotel);
    void deleteHotel(String id);

    List<String> getCountries();

    List<String> getLocations();
    String saveImage(MultipartFile image);
    Page<Hotel> searchHotels(String country, String location,Double minPrice,Double maxPrice,
                             Integer starNumber,Pageable pageable
                             );


}
