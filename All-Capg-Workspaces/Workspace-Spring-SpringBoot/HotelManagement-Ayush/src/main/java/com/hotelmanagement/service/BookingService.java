package com.hotelmanagement.service;

import java.util.List;
import com.hotelmanagement.entity.Booking;

public interface BookingService {

    Booking createBooking(Booking booking, Long roomId);

    List<Booking> getAllBookings();

    Booking getBookingById(Long id);

    Booking updateBooking(Long id, Booking booking);

    void cancelBooking(Long id);
}