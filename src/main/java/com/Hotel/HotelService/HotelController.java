    package com.Hotel.HotelService;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.server.ResponseStatusException;

    import java.util.List;

    @RestController
    @CrossOrigin
    public class HotelController {
        @Autowired
        HotelService hotelService;
        @GetMapping({"/hotels"})
        public List<Hotel> getHotels() {
            return hotelService.getAllHotels();
        }
        @PostMapping("/hotels")
        public ApiResponse createHotel(@RequestBody Hotel hotel) {
            Hotel createdHotel = hotelService.createHotel(hotel);

            if (createdHotel != null) {
                return new ApiResponse("Hôtel créé avec succès !");
            } else {
                return new ApiResponse("Échec de la création de l'hôtel.");
            }
        }

        @PutMapping("/hotels/{id}")
        public ApiResponse updateHotel(@PathVariable Long id, @RequestBody Hotel hotel) {
            try {
                Hotel updatedHotel = hotelService.updateHotel(id, hotel);

                if (updatedHotel != null) {
                    return new ApiResponse("Hôtel mis à jour avec succès !");
                } else {
                    return new ApiResponse("Échec de la mise à jour de l'hôtel. Hôtel non trouvé avec l'ID : " + id);
                }
            } catch (Exception e) {
                return new ApiResponse("Échec de la mise à jour de l'hôtel. Erreur : " + e.getMessage());
            }
        }

        @DeleteMapping("/hotels/{id}")
        public ApiResponse deleteHotel(@PathVariable Long id) {
            try {
                hotelService.deleteHotel(id);
                return new ApiResponse("Hôtel supprimé avec succès !");
            } catch (Exception e) {
                return new ApiResponse("Échec de la suppression de l'hôtel. Erreur : " + e.getMessage());
            }
        }

        @GetMapping("/hotels/{id}")
        public ResponseEntity<?> getHotelById(@PathVariable Long id) {
            try {
                Hotel hotel = hotelService.getHotelById(id);
                return ResponseEntity.ok(hotel);
            } catch (HotelNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Hôtel non trouvé avec l'ID : " + id));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Erreur lors de la récupération de l'hôtel. Erreur : " + e.getMessage()));
            }
        }

    }
