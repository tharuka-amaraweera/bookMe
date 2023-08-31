package it.codegen.assignment.sun.travel.service;

import it.codegen.assignment.sun.travel.dto.*;

import java.util.List;

public interface ReservationService {
    /**
     * Add reservation string.
     *
     * @param reservationRequestPayloadDto the reservation request payload dto
     * @return the string
     */
    List<SearchedRoomResponseDto> searchReservation(ReservationRequestPayloadDto reservationRequestPayloadDto);

    /**
     * Add reservation reservation request payload dto.
     *
     * @param reservationDto the reservation request payload dto
     * @return the reservation response payload dto
     */
    ReservationResponsePayloadDto addReservation(ReservationDto reservationDto);

    /**
     * Delete hotel by id.
     *
     * @param reservationId the reservation id
     */
    void deleteReservationById(Long reservationId);

    /**
     * Update reservation reservation response payload dto.
     *
     * @param reservationDto the reservation dto
     * @return the reservation response payload dto
     */
    ReservationResponsePayloadDto updateReservation(UpdateReservationDto reservationDto);
}
