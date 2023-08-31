package it.codegen.assignment.sun.travel.repository;

import it.codegen.assignment.sun.travel.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * The interface Contract repository.
 */
public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query("SELECT c FROM Contract c WHERE c.validFrom <= :checkIn AND c.validTo >= :checkOut")
    List<Contract> findAllWithCurrentDateInRange(Date checkIn, Date checkOut);
}
