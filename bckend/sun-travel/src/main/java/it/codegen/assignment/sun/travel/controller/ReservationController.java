package it.codegen.assignment.sun.travel.controller;

import it.codegen.assignment.sun.travel.dto.*;
import it.codegen.assignment.sun.travel.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    /**
     * Add resrvation string.
     *
     * @param reservationRequestPayloadDto the reservation request payload dto
     * @return the string
     */
    @PostMapping("searchReservation")
    public List<SearchedRoomResponseDto> searchReservation(@RequestBody ReservationRequestPayloadDto reservationRequestPayloadDto){
        List<SearchedRoomResponseDto> roomDtos = reservationService.searchReservation(reservationRequestPayloadDto);
        return roomDtos;
    }

    /**
     * Add reservation reservation request payload dto.
     *
     * @param reservationDto the reservation dto
     * @return the reservation request payload dto
     */
    @PostMapping("addReservation")
    public ReservationResponsePayloadDto addReservation(@RequestBody ReservationDto reservationDto){
        return reservationService.addReservation(reservationDto);
    }

    /**
     * Delete reservation by id.
     *
     * @param reservationId the reservation id
     */
    @DeleteMapping("deleteById/{reservationId}")
    public void deleteReservationById(@PathVariable Long reservationId){
        reservationService.deleteReservationById(reservationId);
    }

    @PutMapping("updateReservation")
    public ReservationResponsePayloadDto updateReservation(@RequestBody UpdateReservationDto reservationDto){
        return reservationService.updateReservation(reservationDto);
    }

}
