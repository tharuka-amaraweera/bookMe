package it.codegen.assignment.sun.travel.service;

import it.codegen.assignment.sun.travel.dto.HotelDto;
import it.codegen.assignment.sun.travel.entity.Hotel;

import java.util.List;

public interface HotelService {
    /**
     * Add hotels list.
     *
     * @param hotelList the hotel list
     * @return the list
     */
    List<Hotel> addHotels(List<Hotel> hotelList);

    /**
     * Gets all hotels.
     *
     * @return the all hotels
     */
    List<HotelDto> getAllHotels();

    /**
     * Gets hotel by id.
     *
     * @param id the id
     * @return the hotel by id
     */
    Hotel getHotelById(Long id);

    /**
     * Delete hotel by id.
     *
     * @param hotelId the hotel id
     */
    void deleteHotelById(Long hotelId);

    /**
     * Update by id hotel.
     *
     * @param hotel   the hotel
     * @param hotelId the hotel id
     * @return the hotel
     */
    HotelDto updateById(Hotel hotel, Long hotelId);
}
