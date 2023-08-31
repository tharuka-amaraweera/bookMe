package it.codegen.assignment.sun.travel.service;

import it.codegen.assignment.sun.travel.dto.*;
import it.codegen.assignment.sun.travel.entity.Reservation;
import it.codegen.assignment.sun.travel.entity.Room;
import it.codegen.assignment.sun.travel.exception.WebErrorException;
import it.codegen.assignment.sun.travel.repository.ReservationRepository;
import it.codegen.assignment.sun.travel.transformer.PayloadTransformer;
import it.codegen.assignment.sun.travel.util.Constants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * The type Reservation service.
 */
@Service
public class ReservationServiceImpl implements ReservationService {

    /**
     * The Common service.
     */
    @Autowired
    CommonService commonService;

    /**
     * The Reservation repository.
     */
    @Autowired
    ReservationRepository reservationRepository;

    /**
     * The Entity manager.
     */
    @PersistenceContext
    EntityManager entityManager;

    /**
     * The Payload transformer.
     */
    PayloadTransformer payloadTransformer;

    /**
     * The constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    /**
     * Instantiates a new Reservation service.
     *
     * @param payloadTransformer the payload transformer
     */
    public ReservationServiceImpl(PayloadTransformer payloadTransformer) {
        this.payloadTransformer = payloadTransformer;
    }

    @Override
    public List<SearchedRoomResponseDto> searchReservation(ReservationRequestPayloadDto reservationRequestPayloadDto) {
        //todo: make the price calculation here
        logger.info("ReservationServiceImpl.searchReservation - reservationRequestPayloadDto: {}", reservationRequestPayloadDto);
        List<SearchedRoomDto> result = new ArrayList<>();
        List<ReservationRequest> reservationRequests = reservationRequestPayloadDto.getReservationRequest();

        Date checkout;
        try {
            checkout = new Date(reservationRequestPayloadDto.getCheckInDate().getTime() + (reservationRequestPayloadDto.getNumOfNights() * Constants.MILLISECONDSINDAY));
        } catch (NullPointerException e) {
            logger.error("ReservationServiceImpl.searchReservation - NullPointerException", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.SEARCH_RESERVATION_EMPTY_VALUES, HttpStatus.BAD_REQUEST.value(), Constants.ErrorCode.ERRORCODE_CG_01, e.getMessage()));
        }
        try {
            List<RoomDto> availableRooms = commonService.getAvailableRooms(reservationRequestPayloadDto.getCheckInDate(), checkout);

            for (ReservationRequest request : reservationRequests) {
                SearchedRoomDto searchedRoomDto = new SearchedRoomDto();
                searchedRoomDto.setReservationRequest(request);

                List<RoomDto> eligibleRooms = new ArrayList<>();

                List<RoomDto> roomsWithMatchingAdultsCount = getRoomsForMatchingNumberOfAdults(availableRooms, request.getNumberOfAdults());
                if (!roomsWithMatchingAdultsCount.isEmpty()) {
                    for (RoomDto matchingRoom : roomsWithMatchingAdultsCount) {
                        //get reservations for each matching room
                        List<Reservation> reservedRooms = getReservationsByRoomIdAndDateRange(matchingRoom, reservationRequestPayloadDto.getCheckInDate(), checkout);
                        if (reservedRooms.isEmpty()) {
                            //no rooms have reservations. all rooms are available
                            if (matchingRoom.getNumberOfAvailableRooms() >= request.getNumberOfRooms()) {
                                eligibleRooms.add(matchingRoom);
                            }
                        } else {
                            //rooms have reservation
                            int totalNumberOfRooms = getTotalNumberOfRoomsPerReservation(reservedRooms);
                            if (matchingRoom.getNumberOfAvailableRooms() - totalNumberOfRooms <= request.getNumberOfRooms()) {
                                eligibleRooms.add(matchingRoom);
                            }
                        }
                    }
                }
                searchedRoomDto.setRoomDtoList(eligibleRooms);
                result.add(searchedRoomDto);
            }
        }catch (WebErrorException e) {
            throw e;
        }
        catch (Exception e) {
            logger.error("ReservationServiceImpl.searchReservation - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_INTERNAL_SERVER_ERROR, e.getMessage()));
        }

        return payloadTransformer.transformIntoSearchedRoomDtoList(result);
    }

    @Override
    public ReservationResponsePayloadDto addReservation(ReservationDto reservationDto) {
        logger.info("ReservationServiceImpl.addReservation - reservationDto: {}", reservationDto);
        try {
            Reservation savedreservation = null;
            if (reservationDto.getRooms() != null && reservationDto.getNumberOfRooms()!=null && reservationDto.getNumberOfAdults()!=null && reservationDto.getNumOfNights()!=null && reservationDto.getCheckInDate()!=null) {
                Reservation reservation = payloadTransformer.transformToReservation(reservationDto);
                savedreservation = reservationRepository.save(reservation);
            }else {
                logger.error("ReservationServiceImpl.addReservation - WebErrorException: {}", Constants.ErrorMessage.INPUT_DATA_VIOLATION);
                throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.INPUT_DATA_VIOLATION, HttpStatus.BAD_REQUEST.value(), Constants.ErrorCode.ERRORCODE_CG_04));
            }
            return payloadTransformer.transformToReservationDto(savedreservation);
        } catch (DataIntegrityViolationException e) {
            logger.error("ReservationServiceImpl.addReservation - DataIntegrityViolationException", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.ROOM_RESERVATION_VIOLATION, HttpStatus.BAD_REQUEST.value(), Constants.ErrorCode.ERRORCODE_CG_03, e.getMessage()));
        }
        catch (WebErrorException e){
            throw e;
        }
        catch (Exception e) {
            logger.error("ReservationServiceImpl.addReservation - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @Override
    public void deleteReservationById(Long reservationId) {
        logger.info("ReservationServiceImpl.deleteReservationById - reservationId: {}", reservationId);
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation != null) {
            reservationRepository.delete(reservation);
        } else {
            logger.error("ReservationServiceImpl.deleteReservationById - WebErrorException : {}", Constants.ErrorMessage.DELETE_RESERVATION_VIOLATION);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.DELETE_RESERVATION_VIOLATION, HttpStatus.NOT_FOUND.value(), Constants.ErrorCode.ERRORCODE_CG_05));
        }
    }

    @Override
    @Transactional
    public ReservationResponsePayloadDto updateReservation(UpdateReservationDto reservationDto) {
        logger.info("ReservationServiceImpl.updateReservation - reservationDto: {}", reservationDto);
        Reservation reservation = new Reservation();
        Date checkout = new Date(reservationDto.getCheckInDate().getTime() + (reservationDto.getNumOfNights() * Constants.MILLISECONDSINDAY));
        reservation.setCheckOutDate(checkout);
        reservation.setCheckInDate(reservationDto.getCheckInDate());
        validateAvailability(reservationDto, checkout, reservation);

        Reservation updatedReservation = updateAndReturnUpdatedEntity(reservationDto.getReservationId(), reservationDto.getCheckInDate(), checkout);
        entityManager.refresh(updatedReservation);
        //Reservation updatedReservation = reservationRepository.findById(reservationDto.getReservationId()).orElse(null);

        return payloadTransformer.transformToReservationDto(updatedReservation);
    }

    /**
     * Update and return updated entity reservation.
     *
     * @param reservationId the reservation id
     * @param checkInDate   the check in date
     * @param checkOutDate  the checkout date
     * @return the reservation
     */
    private Reservation updateAndReturnUpdatedEntity(Long reservationId, Date checkInDate, Date checkOutDate) {
        reservationRepository.updateReservationByCheckInDateAndCheckOutDate(reservationId, checkInDate, checkOutDate);
        return entityManager.find(Reservation.class, reservationId);
    }

    /**
     * Gets rooms for matching number of adults.
     *
     * @param availableRooms the available rooms
     * @param numberOfAdults the number of adults
     * @return the rooms for matching number of adults
     */
    private List<RoomDto> getRoomsForMatchingNumberOfAdults(List<RoomDto> availableRooms, Integer numberOfAdults) {
        List<RoomDto> roomsList = new ArrayList<>();
        for (RoomDto room : availableRooms) {
            if (room.getMaxAdults() == numberOfAdults) {
                roomsList.add(room);
            }
        }
        return roomsList;
    }

    /**
     * Gets reservations by room id and date range.
     *
     * @param matchingRoom the matching room
     * @param checkIn      the check in
     * @param checkOut     the checkout
     * @return the reservations by room id and date range
     */
    private List<Reservation> getReservationsByRoomIdAndDateRange(RoomDto matchingRoom, Date checkIn, Date checkOut) {
        List<Reservation> reservedRooms = new ArrayList<>();
        List<Reservation> reservationsByRoomIdAndDateRange = reservationRepository.findReservationsByRoomIdAndDateRange(matchingRoom.getRoomTypeId());
        for (Reservation reservation : reservationsByRoomIdAndDateRange) {
            if (isDateWithinRange(checkIn, reservation.getCheckInDate(), reservation.getCheckOutDate()) && isDateWithinRange(checkOut, reservation.getCheckInDate(), reservation.getCheckOutDate())) {
                reservedRooms.add(reservation);
            }
        }
        return reservedRooms;
    }

    /**
     * Validate availability.
     *
     * @param reservationDto the reservation dto
     * @param checkout       the checkout
     * @param reservation    the reservation
     */
    private void validateAvailability(UpdateReservationDto reservationDto, Date checkout, Reservation reservation) {
        Reservation oldReservation = reservationRepository.findById(reservationDto.getReservationId()).orElse(null);
        //get all reserved rooms for the previous reservation
        List<Room> reservedRoomTypes = null;
        if (oldReservation != null) {
            reservedRoomTypes = oldReservation.getReservedRooms();
        } else {
            throw new RuntimeException("no existing reservation for this id");
        }

        if (reservedRoomTypes != null) {
            for (Room room : reservedRoomTypes) {
                List<Reservation> reservedRooms = new ArrayList<>();
                //check room contract validity period
                if (room.getContract().getValidTo().after(checkout) && room.getContract().getValidFrom().before(reservationDto.getCheckInDate())) {
                    List<Reservation> reservationListPerRoom = reservationRepository.findReservationsByRoomIdAndDateRange(room.getRoomTypeId());
                    for (Reservation res : reservationListPerRoom) {
                        if (isDateWithinRange(reservationDto.getCheckInDate(), res.getCheckInDate(), res.getCheckOutDate()) && isDateWithinRange(checkout, res.getCheckInDate(), res.getCheckOutDate())) {
                            reservedRooms.add(reservation);
                        }
                    }
                    if ((room.getNumberOfAvailableRooms() - getTotalNumberOfRoomsPerReservation(reservedRooms)) == 0) {
                        //this room type is not available for the new date range
                        throw new RuntimeException("reservation cannot be made for the selected dates");
                    }

                }
            }
        }
    }

    /**
     * Is date within range boolean.
     *
     * @param date      the date
     * @param startDate the start date
     * @param endDate   the end date
     * @return the boolean
     */
    private boolean isDateWithinRange(Date date, Date startDate, Date endDate) {
        return date.after(startDate) && date.before(endDate) || date.equals(startDate) || date.equals(endDate);
    }

    /**
     * Gets total number of rooms per reservation.
     *
     * @param reservedRooms the reserved rooms
     * @return the total number of rooms per reservation
     */
    public int getTotalNumberOfRoomsPerReservation(List<Reservation> reservedRooms) {
        int totalNumberOfRooms = 0;
        for (Reservation reservation : reservedRooms) {
            totalNumberOfRooms = totalNumberOfRooms + reservation.getNumberOfRooms();
        }
        return totalNumberOfRooms;
    }
}
