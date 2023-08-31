package it.codegen.assignment.sun.travel.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BasicContractDto {
    private Long id;
    private Date validFrom;
    private Date validTo;
}
