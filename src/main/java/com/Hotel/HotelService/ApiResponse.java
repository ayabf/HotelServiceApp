package com.Hotel.HotelService;

public class ApiResponse {
    private String message;
    // Constructeur
    public ApiResponse(String message) {
        this.message = message;
    }

    // Getter
    public String getMessage() {
        return message;
    }

    // Setter
    public void setMessage(String message) {
        this.message = message;
    }
}
