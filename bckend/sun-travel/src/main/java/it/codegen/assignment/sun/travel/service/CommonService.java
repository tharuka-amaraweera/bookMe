package it.codegen.assignment.sun.travel.service;

import it.codegen.assignment.sun.travel.dto.RoomDto;

import java.util.Date;
import java.util.List;

public interface CommonService {
    /**
     * Get available rooms list.
     *
     * @param checkIn  the check in
     * @param checkOut the checkout
     * @return the list
     */
    List<RoomDto> getAvailableRooms(Date checkIn, Date checkOut);
}
