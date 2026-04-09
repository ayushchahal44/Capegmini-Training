package com.hotelmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ROOM")
public class Room {

    @Id
    @Column(name = "ROOM_ID")
    private Long roomId;

    @Column(name = "ROOM_NUMBER", unique = true, nullable = false)
    private String roomNumber;

    @Column(name = "ROOM_TYPE")
    private String roomType;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "STATUS")
    private String status;

    public Room() {}

    public Room(Long roomId, String roomNumber, String roomType, Double price, String status) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.status = status;
    }

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}