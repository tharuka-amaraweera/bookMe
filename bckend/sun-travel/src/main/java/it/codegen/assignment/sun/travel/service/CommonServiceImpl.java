package it.codegen.assignment.sun.travel.service;

import it.codegen.assignment.sun.travel.dto.ContractDto;
import it.codegen.assignment.sun.travel.dto.DatesDto;
import it.codegen.assignment.sun.travel.dto.RoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class CommonServiceImpl implements CommonService{

    @Autowired
    ContractService contractService;

    @Override
    public List<RoomDto> getAvailableRooms(Date checkIn, Date checkOut) {
        DatesDto datesDto = new DatesDto();
        datesDto.setCheckIn(checkIn);
        datesDto.setCheckOut(checkOut);
        List<ContractDto> availableContracts = contractService.getAvailableContracts(datesDto);
        List<RoomDto> allRooms = new ArrayList<>();
        for (ContractDto contract : availableContracts) {
            List<RoomDto> roomList = contract.getRooms();
            for(RoomDto roomDto : roomList){
                roomDto.setContractDto(contract);
                allRooms.add(roomDto);
            }
        }
        return allRooms;
    }
}
