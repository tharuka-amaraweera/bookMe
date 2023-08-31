package it.codegen.assignment.sun.travel.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The entity Contract.
 */
@Entity
@Table(name = "E1829_CONTRACT")
@Data
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ContractId;
    private Date validFrom;
    private Date validTo;
    private Double markup;

    @ManyToOne
    @JoinColumn(name = "fk_hotel_id")
    private Hotel hotel;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();
}