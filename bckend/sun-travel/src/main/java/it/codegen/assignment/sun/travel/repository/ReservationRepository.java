package it.codegen.assignment.sun.travel.repository;

import it.codegen.assignment.sun.travel.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r JOIN r.reservedRooms room WHERE room.roomTypeId = :roomId")
    List<Reservation> findReservationsByRoomIdAndDateRange(Long roomId);

    @Modifying
    @Query("UPDATE Reservation r SET r.checkInDate = :checkInDate, r.checkOutDate = :checkOutDate WHERE r.reservationId = :reservationId")
    void updateReservationByCheckInDateAndCheckOutDate(
            @Param("reservationId") Long reservationId,
            @Param("checkInDate") Date checkInDate,
            @Param("checkOutDate") Date checkOutDate
    );
}
