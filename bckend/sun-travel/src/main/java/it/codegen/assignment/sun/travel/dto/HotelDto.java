package it.codegen.assignment.sun.travel.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * The type Hotel dto.
 */
@Getter
@Setter
public class HotelDto {
    private Long hotelId;
    private String hotelName;
    private List<ContractDto> contracts;

}
