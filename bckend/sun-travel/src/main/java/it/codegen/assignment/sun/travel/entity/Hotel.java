package it.codegen.assignment.sun.travel.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Hotel.
 */
@Entity
@Table(name = "E1829_HOTEL")
@Data
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long hotelId;
    private String hotel_name;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Contract> contracts = new ArrayList<>();
}
