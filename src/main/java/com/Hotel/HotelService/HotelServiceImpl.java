package com.Hotel.HotelService;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    private static final Logger log = LoggerFactory.getLogger(HotelServiceImpl.class);

    @Autowired
    HotelRepository hotelRepository;
    private final MongoTemplate mongoTemplate;

    public HotelServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Override
    public Page<Hotel> getAllHotels(Pageable pageable) {
        return hotelRepository.findAll(pageable);
    }

    @Override
    public Hotel getHotelById(String id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);

        if (optionalHotel.isPresent()) {
            return optionalHotel.get();
        } else {
            throw new HotelNotFoundException("Hôtel non trouvé avec l'ID : " + id);
        }
    }

    @Override
    public Hotel createHotel(Hotel hotel) {
        Hotel createdHotel = hotelRepository.save(hotel);

        if (createdHotel != null) {
            log.info("Hôtel créé avec succès !");
        } else {
            log.error("Échec de la création de l'hôtel.");
        }

        return createdHotel;
    }

    @Override
    public void updateHotel( String id,Hotel hotel) {
        Hotel existiongHotel = hotelRepository.findById(id).get();
        existiongHotel.setNbRooms(hotel.getNbRooms());
        existiongHotel.setPrice(hotel.getPrice());
        existiongHotel.setNbSuites(hotel.getNbSuites());
        existiongHotel.setStarNumber(hotel.getStarNumber());
        existiongHotel.setAddress(hotel.getAddress());
        existiongHotel.setCountry(hotel.getCountry());
        existiongHotel.setName(hotel.getName());
        existiongHotel.setLocation(hotel.getLocation());
        if (hotel.getImageUrl() != null && !hotel.getImageUrl().isEmpty()) {
            existiongHotel.setImageUrl(hotel.getImageUrl());

        }

        hotelRepository.save(existiongHotel);

    }

    @Override
    public void deleteHotel(String id) {
        Optional<Hotel> optionalExistingHotel = hotelRepository.findById(id);

        if (optionalExistingHotel.isPresent()) {
            Hotel existingHotel = optionalExistingHotel.get();

            hotelRepository.delete(existingHotel);
            log.info("Hôtel avec l'ID {} supprimé avec succès !", id);
        } else {
            log.error("Échec de la suppression de l'hôtel. Hôtel avec l'ID {} non trouvé.", id);
        }
    }


    @Override
    public List<String> getCountries() {
        List<String> countries = hotelRepository.findDistinctCountries();
        return countries;
    }

    @Override
    public List<String> getLocations() {
        List<String> locations = hotelRepository.findDistinctLocations();
        return locations;
    }



@Override
    public String saveImage(MultipartFile image) {
    String serverAddress = "http://localhost:8081";
    String directory = "uploads/";
        String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        String filePath = directory + fileName;
    try {
        Files.copy(image.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
        e.printStackTrace();
    }


    return serverAddress + "/" + filePath;
    }
    public Page<Hotel> searchHotels(
            String country,
            String location,
            Double minPrice,
            Double maxPrice,
            Integer starNumber,
            Pageable pageable
    ) {
        Query query = new Query();

        if (country != "" && !country.isEmpty()) {
            query.addCriteria(Criteria.where("country").is(country));
        }

        if (location != "" && !location.isEmpty()) {
            query.addCriteria(Criteria.where("location").is(location));
        }
        if (minPrice != null  && minPrice != 0 && maxPrice != null  && maxPrice != 0) {
            query.addCriteria(Criteria.where("price").gte(minPrice).lte(maxPrice));

        }else if (minPrice == null  && minPrice == 0) {
            query.addCriteria(Criteria.where("price").lte(maxPrice));

        }else {
            query.addCriteria(Criteria.where("price").gte(minPrice));

        }

        if (starNumber != null  && starNumber != 0) {
            query.addCriteria(Criteria.where("starNumber").is(starNumber));
        }

        long count = mongoTemplate.count(query, Hotel.class);
        List<Hotel> hotels = mongoTemplate.find(query.with(pageable), Hotel.class);

        return new PageImpl<>(hotels, pageable, count);
    }
}
