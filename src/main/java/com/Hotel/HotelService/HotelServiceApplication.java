package com.Hotel.HotelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

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
		hotel1.setImageUrl("Bureau\\img.png");
		hotel1.setCountry("Country A");
		hotel1.setLocation("Location A");
		hotel1.setCheckIn(LocalDate.now());
		hotel1.setCheckOut(LocalDate.now().plusDays(3));
		hotel1.setDuration("4");
		hotel1.setMembers(2);

		Hotel hotel2 = new Hotel();
		hotel2.setName("Hotel B");
		hotel2.setAddress("456 Oak Avenue");
		hotel2.setRating(3);
		hotel2.setImageUrl("Bureau\\img.png");
		hotel2.setCountry("Country B");
		hotel2.setLocation("Location B");
		hotel2.setCheckIn(LocalDate.now().plusDays(1));
		hotel2.setCheckOut(LocalDate.now().plusDays(4));
		hotel2.setDuration("3");
		hotel2.setMembers(3);

		hotelRepository.save(hotel1);
		hotelRepository.save(hotel2);
	}
}
