package it.codegen.assignment.sun.travel.transformer;

import it.codegen.assignment.sun.travel.dto.*;
import it.codegen.assignment.sun.travel.entity.Contract;
import it.codegen.assignment.sun.travel.entity.Hotel;
import it.codegen.assignment.sun.travel.entity.Reservation;
import it.codegen.assignment.sun.travel.entity.Room;

import java.util.List;

/**
 * The interface Payload transformer.
 */
public interface PayloadTransformer {
    /**
     * Gets hotel list.
     *
     * @param hotelList the hotel list
     * @return the hotel list
     */
    List<HotelDto> getHotelList(List<Hotel> hotelList);

    /**
     * Transform single contract contract dto.
     *
     * @param contractById the contract by id
     * @return the contract dto
     */
    ContractDto transformSingleContract(Contract contractById);

    /**
     * Transform single room to entity.
     *
     * @param room the room
     */
    Room transformSingleRoomToEntity(RoomRequestPayloadDto room, Contract contract);

    /**
     * Transform single room to payload dto room response payload dto.
     *
     * @param savedRoom the saved room
     * @return the room response payload dto
     */
    RoomResponsePayloadDto transformSingleRoomToPayloadDto(Room savedRoom);

    /**
     * Transform multiple contracts list.
     *
     * @param contractsWithCurrentDateInRange the contracts with current date in range
     * @return the list
     */
    List<ContractDto> transformMultipleContracts(List<Contract> contractsWithCurrentDateInRange);

    /**
     * Transform to single contract entity contract.
     *
     * @param contractDto the contract dto
     * @return the contract
     */
    Contract transformToSingleContractEntity(ContractDto contractDto);

    /**
     * Transform multiple rooms to payload dto list.
     *
     * @param allRooms the all rooms
     * @return the list
     */
    List<RoomResponsePayloadDto> transformMultipleRoomsToPayloadDto(List<Room> allRooms);

    /**
     * Transform roomsfrom single contract list.
     *
     * @param contractEntity the contract entity
     * @return the list
     */
    List<RoomResponsePayloadDto> transformRoomsfromSingleContract(Contract contractEntity);

    /**
     * Transform hotel dto hotel dto.
     *
     * @param hotel the hotel
     * @return the hotel dto
     */
    HotelDto transformHotelDto(Hotel hotel);

    /**
     * Transform rooms dto to response list.
     *
     * @param roomList the room list
     * @return the list
     */
    List<RoomResponsePayloadDto> transformRoomsDtoToResponse(List<RoomDto> roomList);

    /**
     * Transform to reservation.
     *
     * @param reservationDto the reservation request payload dto
     * @return the reservation
     */
    Reservation transformToReservation(ReservationDto reservationDto);

    /**
     * Transform to reservation dto reservation request payload dto.
     *
     * @param savedreservation the savedreservation
     * @return the reservation response payload dto
     */
    ReservationResponsePayloadDto transformToReservationDto(Reservation savedreservation);

    /**
     * Transform into searched room dto list.
     *
     * @param result the result
     * @return the list
     */
    List<SearchedRoomResponseDto> transformIntoSearchedRoomDtoList(List<SearchedRoomDto> result);
}
