package it.codegen.assignment.sun.travel.entity;

import it.codegen.assignment.sun.travel.dto.RoomTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Room.
 */
@Entity
@Table(name = "E1829_ROOM")
@Data
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //todo: use spring validator to validate before insert
    private Long roomTypeId;
    private RoomTypeEnum room_name;
    private Integer maxAdults;
    private Integer numberOfAvailableRooms;
    private Double rate;

    @ManyToOne
    @JoinColumn(name = "fk_contract_id")
    private Contract contract;

    @ManyToMany(mappedBy = "reservedRooms")
    private List<Reservation> reservations = new ArrayList<>();
}
