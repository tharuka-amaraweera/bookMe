package it.codegen.assignment.sun.travel.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "E1829_RESERVATION")
@Data
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reservationId;
    private Date checkInDate;
    private Date checkOutDate;
    private Integer numberOfAdults;
    private Integer numberOfRooms;

    @ManyToMany
    @JoinTable(name = "E1829_RESERVED_ROOMS",
            joinColumns = @JoinColumn(name = "reservationId"),
            inverseJoinColumns = @JoinColumn(name = "roomId"))
    private List<Room> reservedRooms = new ArrayList<>();
}
