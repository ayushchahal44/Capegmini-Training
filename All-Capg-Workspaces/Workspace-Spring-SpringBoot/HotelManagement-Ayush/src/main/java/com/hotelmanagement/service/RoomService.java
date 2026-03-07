package com.hotelmanagement.service;

import java.util.List;
import com.hotelmanagement.entity.Room;

public interface RoomService {

    Room createRoom(Room room);

    List<Room> getAllRooms();

    Room getRoomById(Long id);

    Room updateRoom(Long id, Room room);

    void deleteRoom(Long id);
}