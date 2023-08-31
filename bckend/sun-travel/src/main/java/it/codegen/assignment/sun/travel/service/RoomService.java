package it.codegen.assignment.sun.travel.service;

import it.codegen.assignment.sun.travel.dto.RoomRequestPayloadDto;
import it.codegen.assignment.sun.travel.dto.RoomResponsePayloadDto;
import it.codegen.assignment.sun.travel.dto.RoomWiseSearchDto;

import java.util.List;

public interface RoomService {

    /**
     * Add rooms.
     *
     * @param roomList the room list
     */
    public RoomResponsePayloadDto addRoom(RoomRequestPayloadDto roomList);

    /**
     * Gets all rooms.
     *
     * @return the all rooms
     */
    List<RoomResponsePayloadDto> getAllRooms();

    /**
     * Gets rooms by contract id.
     *
     * @return the rooms by contract id
     */
    List<RoomResponsePayloadDto> getRoomsByContractId(Long id);

    List<RoomResponsePayloadDto> roomWiseSearch(RoomWiseSearchDto roomWiseSearch);
}
