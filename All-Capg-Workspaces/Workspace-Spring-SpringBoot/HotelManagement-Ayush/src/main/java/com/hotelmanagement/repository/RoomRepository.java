package com.hotelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hotelmanagement.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findByRoomNumber(String roomNumber);
}