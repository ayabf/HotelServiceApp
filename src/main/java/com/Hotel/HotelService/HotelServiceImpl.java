package com.Hotel.HotelService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {

    private static final Logger log = LoggerFactory.getLogger(HotelServiceImpl.class);

    @Autowired
    HotelRepository hotelRepository;

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotelById(Long id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);

        // Vérifier si l'hôtel avec l'ID spécifié existe
        if (optionalHotel.isPresent()) {
            return optionalHotel.get();
        } else {
            // Gérer le cas où l'hôtel n'est pas trouvé
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
    public Hotel updateHotel(Long id, Hotel hotel) {
        Optional<Hotel> optionalExistingHotel = hotelRepository.findById(id);

        if (optionalExistingHotel.isPresent()) {
            Hotel existingHotel = optionalExistingHotel.get();

            // Copier les propriétés non nulles de hotel vers existingHotel
            BeanUtils.copyProperties(hotel, existingHotel, "id");

            // Mettre à jour l'hôtel dans la base de données
            Hotel updatedHotel = hotelRepository.save(existingHotel);

            if (updatedHotel != null) {
                log.info("Hôtel avec l'ID {} mis à jour avec succès !", id);
            } else {
                log.error("Échec de la mise à jour de l'hôtel avec l'ID {}.", id);
            }

            return updatedHotel;
        } else {
            // Gérer le cas où l'hôtel n'est pas trouvé
            log.error("Échec de la mise à jour de l'hôtel. Hôtel avec l'ID {} non trouvé.", id);
            return null;
        }
    }

    @Override
    public void deleteHotel(Long id) {
        Optional<Hotel> optionalExistingHotel = hotelRepository.findById(id);

        if (optionalExistingHotel.isPresent()) {
            Hotel existingHotel = optionalExistingHotel.get();

            // Supprimer l'hôtel de la base de données
            hotelRepository.delete(existingHotel);

            log.info("Hôtel avec l'ID {} supprimé avec succès !", id);
        } else {
            // Gérer le cas où l'hôtel n'est pas trouvé
            log.error("Échec de la suppression de l'hôtel. Hôtel avec l'ID {} non trouvé.", id);
        }
    }


    @Override
    public List<Hotel> searchHotel(String name, String address) {
        if (name == null && address == null) {
            return hotelRepository.findAll();
        } else {
            return hotelRepository.findByNameAndAddress(name, address);
        }
    }
    private List<Hotel> filterByAvailability(List<Hotel> hotels, LocalDate checkIn, LocalDate checkOut) {

        return hotelRepository.findByAvailability(checkIn, checkOut);
    }




    public List<Hotel> advancedSearchHotels(String name, String address, String country, String location, LocalDate checkIn, LocalDate checkOut, String duration, Integer members) {
        List<Hotel> searchResults = hotelRepository.findByNameAndAddressAndCountryAndLocation(name, address, country, location);

        if (checkIn != null && checkOut != null) {
            searchResults = filterByAvailability(searchResults, checkIn, checkOut);
        }

        // Filter by duration of stay
        if (duration != null) {
            searchResults = filterByDuration(searchResults, duration); // Check for null before invoking intValue()
        }

        // Filter by number of members
        if (members != null) {
            searchResults = filterByMembers(searchResults, members);
        }

        return searchResults;
    }


    public List<Hotel> filterByDuration(List<Hotel> hotels, String duration) {
       return hotelRepository.findByDuration(duration);
    }

    private List<Hotel> filterByMembers(List<Hotel> hotels, int members) {
        return hotelRepository.findByMembers(members);
    }

    @Override
    public List<Hotel> searchHotels(String name, String address) {
        if (name == null && address == null) {
            // Retourner tous les hôtels si aucun critère de recherche n'est spécifié
            return hotelRepository.findAll();
        } else {
            // Filtrer les hôtels en fonction du nom et de l'adresse
            return hotelRepository.findByNameAndAddress(name, address);
        }
    }
    @Override
    public List<String> getCountries() {
        // Fetch countries from the database using your HotelRepository
        List<String> countries = hotelRepository.findDistinctCountries();
        return countries;
    }

    @Override
    public List<String> getLocations() {
        // Fetch locations from the database using your HotelRepository
        List<String> locations = hotelRepository.findDistinctLocations();
        return locations;
    }

    @Override
    public List<Hotel> findByNameAndAddressAndCountryAndLocation(String name, String address, String country, String location) {
        return hotelRepository.findByNameAndAddressAndCountryAndLocation(name, address, country, location);
    }







}
