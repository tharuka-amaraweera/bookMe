package it.codegen.assignment.sun.travel.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ReservationDto {
    private Long reservationId;
    private Date checkInDate;
    private Integer numOfNights;
    private List<RoomDto> rooms;
    private Integer numberOfAdults;
    private Integer numberOfRooms;
}
