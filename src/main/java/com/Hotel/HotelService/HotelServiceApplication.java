package com.Hotel.HotelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class HotelServiceApplication implements CommandLineRunner {


	@Autowired
	public HotelRepository hotelRepository;

	public static void main(String[] args) {
		SpringApplication.run(HotelServiceApplication.class, args);
	}

	@Override
	public void run(String... args) {
		createSampleHotels();
	}

	private void createSampleHotels() {
		Hotel hotel1 = new Hotel();
		hotel1.setName("Hotel A");
		hotel1.setAddress("123 Main Street");
		hotel1.setRating(4);
		hotelRepository.save(hotel1);

		Hotel hotel2 = new Hotel();
		hotel2.setName("Hotel B");
		hotel2.setAddress("456 Oak Avenue");
		hotel2.setRating(3);
		hotelRepository.save(hotel2);

	}
}
