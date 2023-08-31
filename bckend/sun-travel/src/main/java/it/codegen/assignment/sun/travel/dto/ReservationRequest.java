package it.codegen.assignment.sun.travel.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequest {
    private Integer numberOfAdults;
    private Integer numberOfRooms;
}
