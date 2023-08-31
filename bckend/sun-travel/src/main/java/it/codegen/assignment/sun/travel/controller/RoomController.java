package it.codegen.assignment.sun.travel.controller;

import it.codegen.assignment.sun.travel.dto.RoomRequestPayloadDto;
import it.codegen.assignment.sun.travel.dto.RoomResponsePayloadDto;
import it.codegen.assignment.sun.travel.dto.RoomWiseSearchDto;
import it.codegen.assignment.sun.travel.entity.Room;
import it.codegen.assignment.sun.travel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Room controller.
 */
@RestController
@RequestMapping("rooms")
public class RoomController {
    /**
     * The Room service.
     */
    @Autowired
    RoomService roomService;

    /**
     * Add room.
     *
     * @param newRoom the new room
     * @return the room
     */
    @PostMapping("addRooms")
    public RoomResponsePayloadDto addRoom(@RequestBody RoomRequestPayloadDto newRoom) {
        RoomResponsePayloadDto room = roomService.addRoom(newRoom);
        return room;
    }

    /**
     * Get all rooms list.
     *
     * @return the list
     */
    @GetMapping("getAllRooms")
    public List<RoomResponsePayloadDto> getAllRooms(){
        return roomService.getAllRooms();
    }

    /**
     * Get rooms by contract id list.
     *
     * @param id the id
     * @return the list
     */
    @GetMapping("getRoomsByContractId/{id}")
    public List<RoomResponsePayloadDto> getRoomsByContractId(@PathVariable Long id){
        return roomService.getRoomsByContractId(id);
    }

    /**
     * Room wise search list.
     *
     * @param roomWiseSearchDto the room wise search DTO
     * @return the list
     */
    @PostMapping("roomWiseSearch")
    public List<RoomResponsePayloadDto> roomWiseSearch(@RequestBody RoomWiseSearchDto roomWiseSearchDto){
        return roomService.roomWiseSearch(roomWiseSearchDto);
    }

}
