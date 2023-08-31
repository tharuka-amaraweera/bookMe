package it.codegen.assignment.sun.travel.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ReservationResponsePayloadDto {
    private Date checkInDate;
    private Date checkOutDate;
    private Integer numberOfAdults;
    private Integer numberOfRooms;
    private List<RoomDto> rooms;
}
