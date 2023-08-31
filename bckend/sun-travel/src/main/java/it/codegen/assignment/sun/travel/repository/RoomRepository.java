package it.codegen.assignment.sun.travel.repository;

import it.codegen.assignment.sun.travel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
