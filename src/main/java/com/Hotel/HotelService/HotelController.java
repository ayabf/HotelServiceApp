    package com.Hotel.HotelService;

    import com.fasterxml.jackson.core.JsonProcessingException;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.format.annotation.DateTimeFormat;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.stereotype.Repository;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;
    import org.springframework.web.server.ResponseStatusException;

    import javax.annotation.security.RolesAllowed;
    import java.time.LocalDate;
    import java.util.Collections;
    import java.util.List;

    @RestController
    @CrossOrigin(origins = "http://localhost:4200")
    public class HotelController {

        private final HotelService hotelService;
        private final HotelRepository hotelRepository;

        @Autowired
        public HotelController(HotelService hotelService, HotelRepository hotelRepository) {
            this.hotelService = hotelService;
            this.hotelRepository = hotelRepository;
        }

        @GetMapping({"/hotels"})
        @RolesAllowed("admin")
        public Page<Hotel> getHotels(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int size) {
            return hotelService.getAllHotels(PageRequest.of(page, size));
        }

        @PostMapping(value ="/hotels",consumes = {"multipart/form-data"})
        @RolesAllowed("admin")
        public ApiResponse createHotel(@RequestPart("image") MultipartFile image, @RequestPart("hotel") String hotel) throws JsonProcessingException {
            Hotel hotelRequest = new ObjectMapper().readValue(hotel, Hotel.class);

            String imageUrl = hotelService.saveImage(image);
            hotelRequest.setImageUrl(imageUrl);

            Hotel createdHotel = hotelService.createHotel(hotelRequest);

            if (createdHotel != null) {
                return new ApiResponse("Hôtel créé avec succès !");
            } else {
                return new ApiResponse("Échec de la création de l'hôtel.");
            }
        }



        @PutMapping("/hotels/{id}")
        public ApiResponse updateHotel(@PathVariable String id,@RequestPart("image") MultipartFile image, @RequestPart("hotel") String hotel) {
            try {
                Hotel hotelRequest = new ObjectMapper().readValue(hotel, Hotel.class);
                if (image != null && !image.isEmpty()) {
                String imageUrl = hotelService.saveImage(image);
                hotelRequest.setImageUrl(imageUrl);}

                 hotelService.updateHotel(id,hotelRequest);
                return new ApiResponse("Mise à jour avec succès ");


            } catch (Exception e) {
                return new ApiResponse("Échec de la mise à jour de l'hôtel. Erreur : " + e.getMessage());
            }
        }

        @DeleteMapping("/hotels/{id}")
        public ApiResponse deleteHotel(@PathVariable String id) {
            try {
                hotelService.deleteHotel(id);
                return new ApiResponse("Hôtel supprimé avec succès !");
            } catch (Exception e) {
                return new ApiResponse("Échec de la suppression de l'hôtel. Erreur : " + e.getMessage());
            }
        }

        @GetMapping("/hotels/{id}")
        public ResponseEntity<?> getHotelById(@PathVariable String id) {
            try {
                Hotel hotel = hotelService.getHotelById(id);
                return ResponseEntity.ok(hotel);
            } catch (HotelNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Hôtel non trouvé avec l'ID : " + id));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Erreur lors de la récupération de l'hôtel. Erreur : " + e.getMessage()));
            }
        }

        @GetMapping("/hotels/countries")
        public ResponseEntity<List<String>> getCountries() {
            List<String> countries = hotelService.getCountries();
            return new ResponseEntity<>(countries, HttpStatus.OK);
        }

        @GetMapping("/hotels/locations")
        public ResponseEntity<List<String>> getLocations() {
            List<String> locations = hotelService.getLocations();
            return new ResponseEntity<>(locations, HttpStatus.OK);
        }
    }
