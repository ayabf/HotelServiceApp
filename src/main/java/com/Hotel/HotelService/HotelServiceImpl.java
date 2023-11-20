package com.Hotel.HotelService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
} // test
