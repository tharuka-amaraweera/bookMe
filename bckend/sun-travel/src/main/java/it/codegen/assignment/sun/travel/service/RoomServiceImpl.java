package it.codegen.assignment.sun.travel.service;

import it.codegen.assignment.sun.travel.dto.*;
import it.codegen.assignment.sun.travel.entity.Contract;
import it.codegen.assignment.sun.travel.entity.Hotel;
import it.codegen.assignment.sun.travel.entity.Room;
import it.codegen.assignment.sun.travel.repository.ContractRepository;
import it.codegen.assignment.sun.travel.repository.RoomRepository;
import it.codegen.assignment.sun.travel.transformer.PayloadTransformer;
import it.codegen.assignment.sun.travel.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ContractService contractService;

    @Autowired
    HotelService hotelService;

    @Autowired
    ContractRepository contractRepository;

    PayloadTransformer payloadTransformer;

    @Autowired
    CommonService commonService;

    public RoomServiceImpl(PayloadTransformer payloadTransformer) {
        this.payloadTransformer = payloadTransformer;
    }

    @Override
    public RoomResponsePayloadDto addRoom(RoomRequestPayloadDto room) {
        Room savedRoom = null;
        Long contractId = room.getContractId();
        if (contractId != null) {
            Contract contract = findContract(room.getContractId());
            if (contract != null) {
                savedRoom = addRoom(room, contract);
            }
        } else {
            //todo: exception handling
            throw new RuntimeException("contract Id should not be null");
        }
        RoomResponsePayloadDto roomResponsePayloadDto = payloadTransformer.transformSingleRoomToPayloadDto(savedRoom);
        return roomResponsePayloadDto;
    }

    @Override
    public List<RoomResponsePayloadDto> getAllRooms() {
        List<Room> allRooms = roomRepository.findAll();
        List<RoomResponsePayloadDto> roomResponsePayloadDtos = payloadTransformer.transformMultipleRoomsToPayloadDto(allRooms);
        return roomResponsePayloadDtos;
    }

    @Override
    public List<RoomResponsePayloadDto> getRoomsByContractId(Long id) {
        List<RoomResponsePayloadDto> roomsList = null;
        Contract contract = findContract(id);
        if (contract != null) {
            Contract contractEntity = contractRepository.findById(contract.getContractId()).orElse(null);
            roomsList = payloadTransformer.transformRoomsfromSingleContract(contractEntity);
        }
        return roomsList;
    }

    @Override
    public List<RoomResponsePayloadDto> roomWiseSearch(RoomWiseSearchDto roomWiseSearch) {
        Date checkout = new Date(roomWiseSearch.getCheckIn().getTime() + (roomWiseSearch.getNumOfNights() * Constants.MILLISECONDSINDAY));
        commonService.getAvailableRooms(roomWiseSearch.getCheckIn(), checkout);


        return null;
    }
    /**
     * Add room.
     *
     * @param room the room
     */
    private Room addRoom(RoomRequestPayloadDto room, Contract contract) {
        Room roomEntity = payloadTransformer.transformSingleRoomToEntity(room, contract);
        Room savedRoom = roomRepository.save(roomEntity);
        return savedRoom;
    }

    /**
     * Find contract boolean.
     *
     * @param contractId the contract id
     * @return the boolean
     */
    private Contract findContract(Long contractId) {
        Hotel contractedHotel = null;
        Contract contractById = contractService.getContractById(contractId);
        if (contractById != null) {
            contractedHotel = findHotelByContractId(contractById);
        }
        if (contractedHotel != null) {
            contractById.setHotel(contractedHotel);
        }
        return contractById;

    }

    private Hotel findHotelByContractId(Contract contract) {
        return hotelService.getHotelById(contract.getContractId());
    }
}
