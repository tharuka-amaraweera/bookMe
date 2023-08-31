package it.codegen.assignment.sun.travel.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UpdateReservationDto {
    private Long reservationId;
    private Date checkInDate;
    private Integer numOfNights;
}
