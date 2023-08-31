package it.codegen.assignment.sun.travel.dto;

import it.codegen.assignment.sun.travel.entity.Hotel;
import it.codegen.assignment.sun.travel.entity.Room;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The type Contract dto.
 */
@Getter
@Setter
public class ContractDto{
    private Long id;
    private Date validFrom;
    private Date validTo;
    private HotelDto hotel;
    private Double markup;
    private List<RoomDto> rooms;
}
