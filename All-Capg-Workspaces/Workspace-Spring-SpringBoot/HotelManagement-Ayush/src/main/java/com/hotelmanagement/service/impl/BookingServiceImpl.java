package com.hotelmanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelmanagement.entity.Booking;
import com.hotelmanagement.entity.Room;
import com.hotelmanagement.repository.BookingRepository;
import com.hotelmanagement.repository.RoomRepository;
import com.hotelmanagement.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Booking createBooking(Booking booking, Long roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        booking.setRoom(room);

        room.setStatus("Occupied");

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBookingById(Long id) {

        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Override
    public Booking updateBooking(Long id, Booking updatedBooking) {

        Booking booking = getBookingById(id);

        booking.setCustomerName(updatedBooking.getCustomerName());
        booking.setCustomerPhone(updatedBooking.getCustomerPhone());
        booking.setCheckInDate(updatedBooking.getCheckInDate());
        booking.setCheckOutDate(updatedBooking.getCheckOutDate());

        return bookingRepository.save(booking);
    }

    @Override
    public void cancelBooking(Long id) {

        Booking booking = getBookingById(id);

        Room room = booking.getRoom();
        room.setStatus("Available");

        bookingRepository.delete(booking);
    }
}