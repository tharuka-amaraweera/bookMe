package it.codegen.assignment.sun.travel.repository;

import it.codegen.assignment.sun.travel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * The interface Hotel repository.
 */
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Modifying
    @Query("UPDATE Hotel h SET h.hotel_name = :hotelName WHERE h.hotelId = :hotelId")
    Integer updateHotelByHotelId(
            @Param("hotelId") Long hotelId,
            @Param("hotelName") String hotelName
    );
}
