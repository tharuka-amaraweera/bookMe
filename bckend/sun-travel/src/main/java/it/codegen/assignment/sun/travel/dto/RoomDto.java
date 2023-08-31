package it.codegen.assignment.sun.travel.dto;

import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * The type Room dto.
 */
@Getter
@Setter
public class RoomDto {
    private Long roomTypeId;
    private RoomTypeEnum roomType;
    private Integer maxAdults;
    private Integer numberOfAvailableRooms;
    private Double rate;
    private ContractDto contractDto;
}
