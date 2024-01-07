package com.Hotel.HotelService;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDate;

@Document(collection = "hotel")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Getter
@Setter
public class Hotel {
    @Id
    private String _id;
    private String name;
    private String address;
    private Double price;
    private int starNumber;
    private String country;
    private String location;
    private Integer nbRooms;
    private Integer nbSuites;
    private String imageUrl;
}
