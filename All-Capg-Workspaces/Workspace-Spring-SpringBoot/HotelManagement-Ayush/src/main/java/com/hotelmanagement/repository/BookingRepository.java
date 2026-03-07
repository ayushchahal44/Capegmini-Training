package com.hotelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hotelmanagement.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}