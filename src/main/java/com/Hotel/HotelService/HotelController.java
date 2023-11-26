    package com.Hotel.HotelService;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Repository;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.server.ResponseStatusException;

    import java.util.Collections;
    import java.util.List;

    @RestController
    @CrossOrigin(origins = "http://localhost:4200") // Remplacez par l'URL de votre application Angular
    public class HotelController {

        private final HotelService hotelService;
        private final HotelRepository hotelRepository;

        @Autowired
        public HotelController(HotelService hotelService, HotelRepository hotelRepository) {
            this.hotelService = hotelService;
            this.hotelRepository = hotelRepository;
        }

        @GetMapping({"/hotels"})
        public List<Hotel> getHotels() {
            return hotelService.getAllHotels();
        }

        @PostMapping("/hotels")
        public ApiResponse createHotel(@RequestBody Hotel hotelRequest) {
            // Perform validation and processing for other fields

            // Add image URL to the hotel entity
            String imageUrl = hotelRequest.getImageUrl();
            hotelRequest.setImageUrl(imageUrl);

            Hotel createdHotel = hotelService.createHotel(hotelRequest);

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

        @GetMapping("/search")
        public ResponseEntity<?> searchHotels(@RequestParam(required = false) String name, @RequestParam(required = false) String address) {
            try {
                List<Hotel> hotels;

                if (name == null && address == null) {
                    // Retourner tous les hôtels si aucun critère de recherche n'est spécifié
                    hotels = hotelRepository.findAll();
                } else {
                    // Filtrer les hôtels en fonction du nom et de l'adresse
                    if (name != null && address != null) {
                        hotels = hotelRepository.findByNameAndAddress(name, address);
                    } else if (name != null) {
                        hotels = hotelRepository.findByName(name);
                    } else if (address != null) {
                        hotels = hotelRepository.findByAddress(address);
                    } else {
                        // Gérer d'autres cas si nécessaire
                        hotels = Collections.emptyList();
                    }
                }

                if (hotels.isEmpty()) {
                    return ResponseEntity.ok("Aucun hôtel trouvé.");
                } else {
                    return ResponseEntity.ok(hotels);
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la recherche des hôtels. Erreur : " + e.getMessage());
            }
        }
    }
