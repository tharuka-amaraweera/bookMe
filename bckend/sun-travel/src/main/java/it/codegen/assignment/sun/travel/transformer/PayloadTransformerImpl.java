package it.codegen.assignment.sun.travel.transformer;

import it.codegen.assignment.sun.travel.dto.*;
import it.codegen.assignment.sun.travel.entity.Contract;
import it.codegen.assignment.sun.travel.entity.Hotel;
import it.codegen.assignment.sun.travel.entity.Reservation;
import it.codegen.assignment.sun.travel.entity.Room;
import it.codegen.assignment.sun.travel.exception.WebErrorException;
import it.codegen.assignment.sun.travel.service.HotelServiceImpl;
import it.codegen.assignment.sun.travel.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PayloadTransformerImpl implements PayloadTransformer {

    /**
     * The constant logger.
     */
    public static final Logger logger = LoggerFactory.getLogger(PayloadTransformerImpl.class);
    @Override
    public List<HotelDto> getHotelList(List<Hotel> hotelList) {
        try {
            List<HotelDto> hotelDTOs = hotelList.stream()
                    .map(this::convertToHotelDTO)
                    .toList();
            return hotelDTOs;
        } catch (Exception e) {
            logger.error("PayloadTransformerImpl.getHotelList - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.PAYLOAD_TRANSFORMATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_09, e.getMessage()));
        }
    }

    @Override
    public ContractDto transformSingleContract(Contract contract) {
        try {
            return convertToContractDTO(contract);
        } catch (Exception e) {
            logger.error("PayloadTransformerImpl.transformSingleContract - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.PAYLOAD_TRANSFORMATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_09, e.getMessage()));
        }
    }


    @Override
    public Room transformSingleRoomToEntity(RoomRequestPayloadDto roomDto, Contract contract) {
        Room room = null;
        try {
            room = convertToRoomRequestPayloadFromDto(roomDto);
            room.setContract(contract);
        } catch (Exception e) {
            logger.error("PayloadTransformerImpl.transformSingleRoomToEntity - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.PAYLOAD_TRANSFORMATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_09, e.getMessage()));
        }
        return room;
    }

    @Override
    public RoomResponsePayloadDto transformSingleRoomToPayloadDto(Room savedRoom) {
        RoomResponsePayloadDto roomResponsePayloadDto = null;
        try {
            roomResponsePayloadDto = getRoomResponsePayloadDto(savedRoom);
        } catch (Exception e) {
            logger.error("PayloadTransformerImpl.transformSingleRoomToPayloadDto - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.PAYLOAD_TRANSFORMATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_09, e.getMessage()));
        }
        return roomResponsePayloadDto;
    }

    @Override
    public List<ContractDto> transformMultipleContracts(List<Contract> contractsWithCurrentDateInRange) {
        List<ContractDto> validContractList = null;
        try {
            validContractList = contractsWithCurrentDateInRange.stream().map(this::convertToContractDTO).toList();
        } catch (Exception e) {
            logger.error("PayloadTransformerImpl.transformMultipleContracts - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.PAYLOAD_TRANSFORMATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_09, e.getMessage()));
        }
        return validContractList;
    }

    @Override
    public Contract transformToSingleContractEntity(ContractDto contractDto) {
        Contract contract = null;
        try {
            contract = new Contract();
            contract.setValidFrom(contractDto.getValidFrom());
            contract.setValidTo(contractDto.getValidTo());
            contract.setMarkup(contractDto.getMarkup());

            Hotel hotel = new Hotel();
            hotel.setHotelId(contractDto.getHotel().getHotelId());
            contract.setHotel(hotel);
        } catch (Exception e) {
            logger.error("PayloadTransformerImpl.transformToSingleContractEntity - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.PAYLOAD_TRANSFORMATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_09, e.getMessage()));
        }
        return contract;
    }

    @Override
    public List<RoomResponsePayloadDto> transformMultipleRoomsToPayloadDto(List<Room> allRooms) {
        List<RoomResponsePayloadDto> roomList = null;
        try {
            roomList = allRooms.stream().map(this::getRoomResponsePayloadDto).toList();
        } catch (Exception e) {
            logger.error("PayloadTransformerImpl.transformMultipleRoomsToPayloadDto - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.PAYLOAD_TRANSFORMATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_09, e.getMessage()));
        }
        return roomList;
    }

    @Override
    public List<RoomResponsePayloadDto> transformRoomsfromSingleContract(Contract contractEntity) {
        List<RoomResponsePayloadDto> roomList = null;
        try {
            List<Room> rooms = contractEntity.getRooms();
            roomList = rooms.stream().map(this::getRoomResponsePayloadDto).toList();
        } catch (Exception e) {
            logger.error("PayloadTransformerImpl.transformRoomsfromSingleContract - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.PAYLOAD_TRANSFORMATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_09, e.getMessage()));
        }
        return roomList;
    }

    @Override
    public HotelDto transformHotelDto(Hotel hotel) {
        try {
            HotelDto hotelDto = getHotelDto(hotel);
            return hotelDto;
        } catch (Exception e) {
            logger.error("PayloadTransformerImpl.transformHotelDto - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.PAYLOAD_TRANSFORMATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_09, e.getMessage()));
        }
    }

    @Override
    public List<RoomResponsePayloadDto> transformRoomsDtoToResponse(List<RoomDto> roomList) {
        List<RoomResponsePayloadDto> roomResponsePayloadDtos = null;
        try {
            roomResponsePayloadDtos = new ArrayList<>();
            roomResponsePayloadDtos = roomList.stream().map(this::getRoomResponsePayloadDtoFromDto).toList();
        } catch (Exception e) {
            logger.error("PayloadTransformerImpl.transformRoomsDtoToResponse - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.PAYLOAD_TRANSFORMATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_09, e.getMessage()));
        }
        return roomResponsePayloadDtos;
    }

    @Override
    public Reservation transformToReservation(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        try {
            reservation.setCheckInDate(reservationDto.getCheckInDate());
            Date checkout = new Date(reservationDto.getCheckInDate().getTime() + (reservationDto.getNumOfNights() * Constants.MILLISECONDSINDAY));
            reservation.setCheckOutDate(checkout);
            reservation.setNumberOfAdults(reservationDto.getNumberOfAdults());
            reservation.setNumberOfRooms(reservationDto.getNumberOfRooms());


            List<RoomDto> rooms = reservationDto.getRooms();
            List<Room> roomList = rooms.stream().map(this::convertToRoomFromDto).toList();
            reservation.setReservedRooms(roomList);
        } catch (Exception e) {
            logger.error("PayloadTransformerImpl.transformToReservation - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.PAYLOAD_TRANSFORMATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_09, e.getMessage()));
        }
        return reservation;
    }

    @Override
    public ReservationResponsePayloadDto transformToReservationDto(Reservation savedreservation) {
        ReservationResponsePayloadDto reservationResponsePayloadDto = new ReservationResponsePayloadDto();
        try {
            reservationResponsePayloadDto.setCheckInDate(savedreservation.getCheckInDate());
            reservationResponsePayloadDto.setCheckOutDate(savedreservation.getCheckOutDate());
            reservationResponsePayloadDto.setNumberOfAdults(savedreservation.getNumberOfAdults());
            reservationResponsePayloadDto.setNumberOfRooms(savedreservation.getNumberOfRooms());

            List<Room> reservedRooms = savedreservation.getReservedRooms();
            List<RoomDto> roomList = reservedRooms.stream().map(this::convertToRoomDto).toList();
            reservationResponsePayloadDto.setRooms(roomList);
        } catch (Exception e) {
            logger.error("PayloadTransformerImpl.transformToReservationDto - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.PAYLOAD_TRANSFORMATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_09, e.getMessage()));
        }
        return reservationResponsePayloadDto;
    }

    @Override
    public List<SearchedRoomResponseDto> transformIntoSearchedRoomDtoList(List<SearchedRoomDto> results) {
        List<SearchedRoomResponseDto> finalResult = null;
        try {
            finalResult = results.stream().map(this::convertToSearchedRoomDto).toList();
        } catch (Exception e) {
            logger.error("PayloadTransformerImpl.transformIntoSearchedRoomDtoList - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.PAYLOAD_TRANSFORMATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_09, e.getMessage()));
        }
        return finalResult;
    }

    private SearchedRoomResponseDto convertToSearchedRoomDto(SearchedRoomDto searchedRoomDto) {
        SearchedRoomResponseDto searchedRoom = new SearchedRoomResponseDto();

        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setNumberOfRooms(searchedRoomDto.getReservationRequest().getNumberOfRooms());
        reservationRequest.setNumberOfAdults(searchedRoomDto.getReservationRequest().getNumberOfAdults());
        searchedRoom.setReservationRequest(reservationRequest);

        List<RoomDto> roomDtoList = searchedRoomDto.getRoomDtoList();

        List<RoomDto> finalRoomDtoList = roomDtoList.stream().map(this::convertToRoomDtoList).toList();
        searchedRoom.setRoomDtoList(finalRoomDtoList);
        return searchedRoom;
    }

    private RoomDto convertToRoomDtoList(RoomDto room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setRoomType(room.getRoomType());
        roomDto.setRate(room.getRate());
        roomDto.setNumberOfAvailableRooms(room.getNumberOfAvailableRooms());
        roomDto.setMaxAdults(room.getMaxAdults());
        roomDto.setRoomTypeId(room.getRoomTypeId());
        return roomDto;
    }

    /**
     * Gets room response payload dto from dto.
     *
     * @param roomDto the room dto
     * @return the room response payload dto from dto
     */
    private RoomResponsePayloadDto getRoomResponsePayloadDtoFromDto(RoomDto roomDto) {
        RoomResponsePayloadDto roomResponsePayloadDto = new RoomResponsePayloadDto();
        roomResponsePayloadDto.setRoomType(roomDto.getRoomType());
        roomResponsePayloadDto.setNumberOfAvailableRooms(roomDto.getNumberOfAvailableRooms());
        roomResponsePayloadDto.setRate(roomDto.getRate());
        roomResponsePayloadDto.setRoomTypeId(roomDto.getRoomTypeId());
        roomResponsePayloadDto.setMaxAdults(roomDto.getMaxAdults());
        ContractDto contractDto = new ContractDto();
        contractDto.setHotel(roomDto.getContractDto().getHotel());
        contractDto.setMarkup(roomDto.getContractDto().getMarkup());
        contractDto.setValidFrom(roomDto.getContractDto().getValidFrom());
        contractDto.setValidTo(roomDto.getContractDto().getValidTo());
        contractDto.setId(roomDto.getContractDto().getId());

        roomResponsePayloadDto.setContractDto(contractDto);

        return roomResponsePayloadDto;
    }

    /**
     * Convert to room dto room dto.
     *
     * @param room the room
     * @return the room dto
     */
    private RoomDto convertToRoomDto(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setRoomType(room.getRoom_name());
        roomDto.setRate(room.getRate());
        roomDto.setNumberOfAvailableRooms(room.getNumberOfAvailableRooms());
        roomDto.setMaxAdults(room.getMaxAdults());
        roomDto.setRoomTypeId(room.getRoomTypeId());
        return roomDto;
    }

    /**
     * Convert to hotel dto
     *
     * @param hotel
     * @return hotel
     */
    private HotelDto convertToHotelDTO(Hotel hotel) {
        HotelDto hotelDTO = new HotelDto();
        hotelDTO.setHotelId(hotel.getHotelId());
        hotelDTO.setHotelName(hotel.getHotel_name());
        List<Contract> contractList = hotel.getContracts();
        List<ContractDto> contractDtos = contractList.stream().map(this::convertToContractDTO).toList();
        hotelDTO.setContracts(contractDtos);

        return hotelDTO;
    }

    /**
     * Convert to contract dto hotel contract dto.
     *
     * @param contract the contract
     * @return the hotel contract dto
     */
    private ContractDto convertToContractDTO(Contract contract) {
        List<Room> roomList = contract.getRooms();
        List<RoomDto> roomDtoList = roomList.stream().map(this::convertToRoomDto).toList();

        HotelDto hotelDto = new HotelDto();
        hotelDto.setHotelId(contract.getHotel().getHotelId());
        hotelDto.setHotelName(contract.getHotel().getHotel_name());

        ContractDto contractDto = new ContractDto();
        contractDto.setId(contract.getContractId());
        contractDto.setValidFrom(contract.getValidFrom());
        contractDto.setValidTo(contract.getValidTo());
        contractDto.setRooms(roomDtoList);
        contractDto.setHotel(hotelDto);
        contractDto.setMarkup(contract.getMarkup());
        return contractDto;
    }

    /**
     * Gets room response payload dto.
     *
     * @param savedRoom the saved room
     * @return the room response payload dto
     */
    private RoomResponsePayloadDto getRoomResponsePayloadDto(Room savedRoom) {
        RoomResponsePayloadDto roomResponsePayloadDto = new RoomResponsePayloadDto();
        roomResponsePayloadDto.setRoomType(savedRoom.getRoom_name());
        roomResponsePayloadDto.setRate(savedRoom.getRate());
        roomResponsePayloadDto.setNumberOfAvailableRooms(savedRoom.getNumberOfAvailableRooms());
        roomResponsePayloadDto.setRoomTypeId(savedRoom.getRoomTypeId());
        roomResponsePayloadDto.setMaxAdults(savedRoom.getMaxAdults());

        //set rooms list
        List<Room> roomList = savedRoom.getContract().getRooms();
        List<RoomDto> roomDtoList = roomList.stream().map(this::convertToRoomDto).toList();

        //set hotel
        HotelDto hotelDto = getHotelDto(savedRoom.getContract().getHotel());

        //set contract
        ContractDto contractDto = new ContractDto();
        contractDto.setId(savedRoom.getContract().getContractId());
        contractDto.setMarkup(savedRoom.getContract().getMarkup());
        contractDto.setValidFrom(savedRoom.getContract().getValidFrom());
        contractDto.setValidTo(savedRoom.getContract().getValidTo());
        contractDto.setRooms(roomDtoList);
        contractDto.setHotel(hotelDto);

        roomResponsePayloadDto.setContractDto(contractDto);
        return roomResponsePayloadDto;
    }

    /**
     * Gets hotel dto.
     *
     * @param hotel the hotel
     * @return the hotel dto
     */
    private HotelDto getHotelDto(Hotel hotel) {
        HotelDto hotelDto = new HotelDto();
        hotelDto.setHotelId(hotel.getHotelId());
        hotelDto.setHotelName(hotel.getHotel_name());
        List<Contract> contracts = hotel.getContracts();

        List<ContractDto> contractList = contracts.stream().map(this::convertToContractDTO).toList();
        hotelDto.setContracts(contractList);

        return hotelDto;
    }

    private Room convertToRoomRequestPayloadFromDto(RoomRequestPayloadDto roomDto) {
        Room room = new Room();
        room.setRoom_name(roomDto.getRoomType());
        room.setRoomTypeId(roomDto.getRoomTypeId());
        room.setRate(roomDto.getRate());
        room.setNumberOfAvailableRooms(roomDto.getNumberOfAvailableRooms());
        room.setMaxAdults(roomDto.getMaxAdults());
        return room;
    }

    private Room convertToRoomFromDto(RoomDto roomDto) {
        Room room = new Room();
        room.setRoom_name(roomDto.getRoomType());
        room.setRoomTypeId(roomDto.getRoomTypeId());
        room.setRate(roomDto.getRate());
        room.setNumberOfAvailableRooms(roomDto.getNumberOfAvailableRooms());
        room.setMaxAdults(roomDto.getMaxAdults());
        return room;
    }

}
