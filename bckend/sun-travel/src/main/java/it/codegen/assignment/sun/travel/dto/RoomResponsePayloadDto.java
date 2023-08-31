package it.codegen.assignment.sun.travel.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomResponsePayloadDto {
    private Long roomTypeId;
    private RoomTypeEnum roomType;
    private Integer maxAdults;
    private Integer numberOfAvailableRooms;
    private Double rate;
    private ContractDto contractDto;
}
