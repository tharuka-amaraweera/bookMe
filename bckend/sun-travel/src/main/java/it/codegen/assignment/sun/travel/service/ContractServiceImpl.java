package it.codegen.assignment.sun.travel.service;

import it.codegen.assignment.sun.travel.dto.BasicContractDto;
import it.codegen.assignment.sun.travel.dto.ContractDto;
import it.codegen.assignment.sun.travel.dto.DatesDto;
import it.codegen.assignment.sun.travel.dto.WebErrorDTO;
import it.codegen.assignment.sun.travel.entity.Contract;
import it.codegen.assignment.sun.travel.entity.Hotel;
import it.codegen.assignment.sun.travel.exception.WebErrorException;
import it.codegen.assignment.sun.travel.repository.ContractRepository;
import it.codegen.assignment.sun.travel.repository.HotelRepository;
import it.codegen.assignment.sun.travel.transformer.PayloadTransformer;
import it.codegen.assignment.sun.travel.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The type Contract service.
 */
@Service
public class ContractServiceImpl implements ContractService {
    /**
     * The Contract repository.
     */
    @Autowired
    ContractRepository contractRepository;

    /**
     * The Payload transformer.
     */
    PayloadTransformer payloadTransformer;

    /**
     * The constant logger.
     */
    public static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);

    /**
     * Instantiates a new Contract service.
     *
     * @param payloadTransformer the payload transformer
     */
    public ContractServiceImpl(PayloadTransformer payloadTransformer) {
        this.payloadTransformer = payloadTransformer;
    }

    /**
     * The Hotel repository.
     */
    @Autowired
    HotelRepository hotelRepository;

    @Override
    public ContractDto addContract(ContractDto contractDto) {
        ContractDto newContract = null;
        logger.info("ContractServiceImpl.addContract - contractDto: {}", contractDto);
        try {
            if (contractDto.getHotel() != null && contractDto.getHotel().getHotelId() != null) {
                Hotel existingHotel = hotelRepository.findById(contractDto.getHotel().getHotelId()).orElse(null);
                if (existingHotel != null) {
                    newContract = saveContract(contractDto);
                } else {
                    logger.error("ContractServiceImpl.addContract - WebErrorException: {}", Constants.ErrorMessage.NO_HOTEL_FOUND_ERROR);
                    throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.NO_HOTEL_FOUND_ERROR, HttpStatus.NOT_FOUND.value(), Constants.ErrorCode.ERRORCODE_CG_06));
                }
            } else {
                logger.error("ContractServiceImpl.addContract - WebErrorException: {}", Constants.ErrorMessage.INPUT_DATA_VIOLATION);
                throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.INPUT_DATA_VIOLATION, HttpStatus.BAD_REQUEST.value(), Constants.ErrorCode.ERRORCODE_CG_04));
            }
        } catch (Exception e) {
            logger.error("ContractServiceImpl.addContract - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_09, e.getMessage()));
        }
        return newContract;

    }

    @Override
    public Contract getContractById(Long id) {
        logger.info("ContractServiceImpl.addContract - id: {}", id);
        try {
            Contract contract = contractRepository.findById(id).orElse(null);
            if (contract != null) {
                return contract;
            }else {
                logger.error("ContractServiceImpl.getContractById - WebErrorException: {}", Constants.ErrorMessage.NO_CONTRACT_FOUND);
                throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.NO_CONTRACT_FOUND, HttpStatus.NOT_FOUND.value(), Constants.ErrorCode.ERRORCODE_CG_10));
            }
        } catch (WebErrorException e) {
            logger.error("ContractServiceImpl.getContractById - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_09, e.getMessage()));
        }

    }

    @Override
    public List<ContractDto> getAvailableContracts(DatesDto datesDto) {
        List<Contract> contractsWithCurrentDateInRange = contractRepository.findAllWithCurrentDateInRange(datesDto.getCheckIn(), datesDto.getCheckOut());
        List<ContractDto> validContractsList = payloadTransformer.transformMultipleContracts(contractsWithCurrentDateInRange);

        return validContractsList;
    }

    /**
     * Save contract.
     *
     * @param contractDto the contract dto
     */
    private ContractDto saveContract(ContractDto contractDto) {
        ContractDto responseContract = null;
        try {
            Contract contract = payloadTransformer.transformToSingleContractEntity(contractDto);
            Contract savedContract = contractRepository.save(contract);
            responseContract = payloadTransformer.transformSingleContract(savedContract);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return responseContract;
    }

}
