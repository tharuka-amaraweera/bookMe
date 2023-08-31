package it.codegen.assignment.sun.travel.controller;


import it.codegen.assignment.sun.travel.dto.BasicContractDto;
import it.codegen.assignment.sun.travel.dto.ContractDto;
import it.codegen.assignment.sun.travel.dto.DatesDto;
import it.codegen.assignment.sun.travel.entity.Contract;
import it.codegen.assignment.sun.travel.service.ContractService;
import it.codegen.assignment.sun.travel.service.HotelServiceImpl;
import it.codegen.assignment.sun.travel.transformer.PayloadTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * The type Contract controller.
 */
@RestController
@RequestMapping("contracts")
public class ContractController {

    PayloadTransformer payloadTransformer;
    /**
     * The Contract service.
     */
    @Autowired
    ContractService contractService;

    /**
     * The constant logger.
     */
    public static final Logger logger = LoggerFactory.getLogger(ContractController.class);

    /**
     * Instantiates a new Contract controller.
     *
     * @param payloadTransformer the payload transformer
     */
    public ContractController(PayloadTransformer payloadTransformer) {
        this.payloadTransformer = payloadTransformer;
    }


    /**
     * Add contract dto.
     *
     * @param contractDto the contract dto
     * @return the contract dto
     */
    @PostMapping("addContract")
    public ContractDto addContract(@RequestBody ContractDto contractDto){
        logger.info("ContractController.addContract - contractDto: {}", contractDto);
        return contractService.addContract(contractDto);
    }

    /**
     * Get contract by id contract.
     *
     * @param id the id
     * @return the contract
     */
    @GetMapping("getContractById/{id}")
    public ContractDto getContractById(@PathVariable Long id){
        logger.info("ContractController.getContractById - id: {}", id);
        return payloadTransformer.transformSingleContract(contractService.getContractById(id));
    }

    @PostMapping("getAvailableContracts")
    public List<ContractDto> getAvailableContracts(@RequestBody DatesDto datesDto){
        logger.info("ContractController.getAvailableContracts - datesDto: {}", datesDto);
        return contractService.getAvailableContracts(datesDto);
    }
}
