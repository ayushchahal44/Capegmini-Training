package com.hotelmanagement.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BOOKING")
public class Booking {

    @Id
    @Column(name = "BOOKING_ID")
    private Long bookingId;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "CUSTOMER_PHONE")
    private String customerPhone;

    @Temporal(TemporalType.DATE)
    @Column(name = "CHECKIN_DATE")
    private Date checkInDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "CHECKOUT_DATE")
    private Date checkOutDate;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    public Booking() {}

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public Date getCheckInDate() { return checkInDate; }
    public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }

    public Date getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
}