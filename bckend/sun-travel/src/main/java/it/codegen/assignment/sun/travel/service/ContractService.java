package it.codegen.assignment.sun.travel.service;

import it.codegen.assignment.sun.travel.dto.ContractDto;
import it.codegen.assignment.sun.travel.dto.DatesDto;
import it.codegen.assignment.sun.travel.entity.Contract;

import java.util.Date;
import java.util.List;

/**
 * The interface Contract service.
 */
public interface ContractService {
    /**
     * Add contract.
     *
     * @param contract the contract
     * @return the contract
     */
    ContractDto addContract(ContractDto contractDto);

    /**
     * Gets contract by id.
     *
     * @param id the id
     * @return the contract by id
     */
    Contract getContractById(Long id);

    /**
     * Gets available contracts.
     *
     * @return the available contracts
     */
    List<ContractDto> getAvailableContracts(DatesDto datesDto);
}
