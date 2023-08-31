package it.codegen.assignment.sun.travel.dto;

import lombok.Getter;

@Getter
public enum RoomTypeEnum {
    /**
     * Single room type.
     */
    SINGLE("SINGLE"),
    /**
     * Double room type.
     */
    DOUBLE("DOUBLE"),
    /**
     * Triple room type.
     */
    TRIPLE("TRIPLE");

    RoomTypeEnum(String name) {
    }
}
