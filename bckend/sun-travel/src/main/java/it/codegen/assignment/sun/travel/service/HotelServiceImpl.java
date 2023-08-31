package it.codegen.assignment.sun.travel.service;

import it.codegen.assignment.sun.travel.dto.HotelDto;
import it.codegen.assignment.sun.travel.dto.WebErrorDTO;
import it.codegen.assignment.sun.travel.entity.Contract;
import it.codegen.assignment.sun.travel.entity.Hotel;
import it.codegen.assignment.sun.travel.exception.WebErrorException;
import it.codegen.assignment.sun.travel.repository.HotelRepository;
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

import java.util.List;

/**
 * The type Hotel service.
 */
@Service
public class HotelServiceImpl implements HotelService {

    /**
     * The Hotel repository.
     */
    @Autowired
    private HotelRepository hotelRepository;


    /**
     * The Payload transformer.
     */
    private final PayloadTransformer payloadTransformer;

    /**
     * The Entity manager.
     */
    @PersistenceContext
    EntityManager entityManager;

    /**
     * The constant logger.
     */
    public static final Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);


    /**
     * Instantiates a new Hotel service.
     *
     * @param payloadTransformer the payload transformer
     */
    public HotelServiceImpl(PayloadTransformer payloadTransformer) {
        this.payloadTransformer = payloadTransformer;
    }

    @Override
    public List<Hotel> addHotels(List<Hotel> hotelList) {
        logger.info("HotelServiceImpl.addHotels - hotelList: {}", hotelList);
        if (hotelList.isEmpty()) {
            logger.error("HotelServiceImpl.addHotels - WebErrorException: {}", Constants.ErrorMessage.EMPTY_HOTEL_LIST);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.EMPTY_HOTEL_LIST, HttpStatus.BAD_REQUEST.value(), Constants.ErrorCode.ERRORCODE_CG_07));
        }
        return hotelRepository.saveAll(hotelList);
    }

    @Override
    public List<HotelDto> getAllHotels() {
        logger.info("HotelServiceImpl.getAllHotels");
        List<Hotel> hotelList = null;
        List<HotelDto> hotelDTOs = null;
        try {
            hotelList = hotelRepository.findAll();
            hotelDTOs = payloadTransformer.getHotelList(hotelList);
        } catch (Exception e) {
            logger.error("HotelServiceImpl.getAllHotels - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_INTERNAL_SERVER_ERROR, e.getMessage()));
        }
        return hotelDTOs;
    }

    @Override
    public Hotel getHotelById(Long id) {
        logger.info("HotelServiceImpl.getHotelById - id: {}", id);
        Hotel hotel = null;
        try {
            hotel = hotelRepository.findById(id).orElse(null);
            if (hotel == null) {
                logger.error("HotelServiceImpl.getHotelById - WebErrorException: {}", Constants.ErrorMessage.NO_HOTEL_FOUND_ERROR);
                throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.NO_HOTEL_FOUND_ERROR, HttpStatus.NOT_FOUND.value(), Constants.ErrorCode.ERRORCODE_CG_06));
            }
        } catch (DataIntegrityViolationException e) {
            logger.error("HotelServiceImpl.getHotelById - DataIntegrityViolationException", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.DB_DATA_VIOLATION, HttpStatus.BAD_REQUEST.value(), Constants.ErrorCode.ERRORCODE_CG_03, e.getMessage()));
        } catch (Exception e){
            logger.error("HotelServiceImpl.getHotelById - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_INTERNAL_SERVER_ERROR, e.getMessage()));
        }
        return hotel;
    }

    @Override
    public void deleteHotelById(Long hotelId) {
        logger.info("HotelServiceImpl.deleteHotelById - hotelId: {}", hotelId);
        try {
            if (hotelId!=null) {
                Hotel hotel = hotelRepository.findById(hotelId).orElse(null);
                if (hotel != null) {
                    for (Contract contract : hotel.getContracts()) {
                        contract.getRooms().clear();
                    }
                    hotel.getContracts().clear();

                    // Delete the hotel
                    hotelRepository.delete(hotel);
                } else {
                    logger.error("HotelServiceImpl.deleteHotelById - WebErrorException: {}", Constants.ErrorMessage.NO_HOTEL_FOUND_ERROR);
                    throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.NO_HOTEL_FOUND_ERROR, HttpStatus.NOT_FOUND.value(), Constants.ErrorCode.ERRORCODE_CG_06));
                }
            } else {
                logger.error("HotelServiceImpl.deleteHotelById - WebErrorException: {}", Constants.ErrorMessage.INVALID_HOTEL_ID);
                throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.INVALID_HOTEL_ID, HttpStatus.BAD_REQUEST.value(), Constants.ErrorCode.ERRORCODE_CG_08));
            }
        } catch (DataIntegrityViolationException e) {
            logger.error("HotelServiceImpl.deleteHotelById - DataIntegrityViolationException", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.DB_DATA_VIOLATION, HttpStatus.BAD_REQUEST.value(), Constants.ErrorCode.ERRORCODE_CG_03, e.getMessage()));
        } catch (Exception e){
            logger.error("HotelServiceImpl.deleteHotelById - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @Override
    @Transactional
    public HotelDto updateById(Hotel hotel, Long hotelId) {
        logger.info("HotelServiceImpl.updateById - hotel: {}, hotelId: {}", hotel, hotelId);
        Hotel updatedHotel = null;
        try {
            if(hotel!=null && hotelId!=null){
                Hotel existingHotel = hotelRepository.findById(hotelId).orElse(null);
                if(existingHotel!=null){
                    hotelRepository.updateHotelByHotelId(hotelId, hotel.getHotel_name());
                    updatedHotel = getHotelById(hotelId);
                    entityManager.refresh(updatedHotel);
                }else {
                    logger.error("HotelServiceImpl.updateById - WebErrorException: {}", Constants.ErrorMessage.INVALID_HOTEL_ID);
                    throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.INVALID_HOTEL_ID, HttpStatus.NOT_FOUND.value(), Constants.ErrorCode.ERRORCODE_CG_06));
                }
            }else {
                logger.error("HotelServiceImpl.updateById - WebErrorException: {}", Constants.ErrorMessage.INPUT_DATA_VIOLATION);
                throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.INPUT_DATA_VIOLATION, HttpStatus.BAD_REQUEST.value(), Constants.ErrorCode.ERRORCODE_CG_04));
            }
        } catch (Exception e) {
            logger.error("HotelServiceImpl.updateById - Exception", e);
            throw new WebErrorException(new WebErrorDTO(Constants.ErrorMessage.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.ErrorCode.ERRORCODE_CG_INTERNAL_SERVER_ERROR, e.getMessage()));
        }
        return payloadTransformer.transformHotelDto(updatedHotel);
    }
}
