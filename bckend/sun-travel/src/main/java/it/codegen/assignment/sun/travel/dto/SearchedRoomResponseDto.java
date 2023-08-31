package it.codegen.assignment.sun.travel.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SearchedRoomResponseDto {
    ReservationRequest reservationRequest;
    List<RoomDto> roomDtoList;
}
