package it.codegen.assignment.sun.travel.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RoomWiseSearchDto {
    private Date checkIn;
    private Integer numOfNights;
    private List<RoomSearchMap> roomRequests;
}
