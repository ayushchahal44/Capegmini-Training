package com.hotelmanagement.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelmanagement.entity.Room;
import com.hotelmanagement.repository.RoomRepository;
import com.hotelmanagement.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(Long id) {

        Optional<Room> room = roomRepository.findById(id);

        if(room.isPresent())
            return room.get();

        throw new RuntimeException("Room not found with id " + id);
    }

    @Override
    public Room updateRoom(Long id, Room updatedRoom) {

        Room room = getRoomById(id);

        room.setRoomNumber(updatedRoom.getRoomNumber());
        room.setRoomType(updatedRoom.getRoomType());
        room.setPrice(updatedRoom.getPrice());
        room.setStatus(updatedRoom.getStatus());

        return roomRepository.save(room);
    }

    @Override
    public void deleteRoom(Long id) {

        Room room = getRoomById(id);
        roomRepository.delete(room);
    }
}