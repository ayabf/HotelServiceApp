package com.Hotel.HotelService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CREATED)
public class HotelCreatedResponse extends ApiResponse{
    public HotelCreatedResponse(String message) {
        super(message);
    }
}
