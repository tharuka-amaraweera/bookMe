package it.codegen.assignment.sun.travel.controller;

import it.codegen.assignment.sun.travel.dto.HotelDto;
import it.codegen.assignment.sun.travel.entity.Hotel;
import it.codegen.assignment.sun.travel.service.HotelService;
import it.codegen.assignment.sun.travel.transformer.PayloadTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Hotel controller.
 */
@RestController
@RequestMapping("hotels")
public class HotelController {

    /**
     * The Hotel service.
     */
    @Autowired
    HotelService hotelService;

    /**
     * The Payload transformer.
     */
    PayloadTransformer payloadTransformer;

    /**
     * The constant logger.
     */
    public static final Logger logger = LoggerFactory.getLogger(HotelController.class);

    /**
     * Instantiates a new Hotel controller.
     *
     * @param payloadTransformer the payload transformer
     */
    public HotelController(PayloadTransformer payloadTransformer) {
        this.payloadTransformer = payloadTransformer;
    }

    /**
     * Add hotels list.
     *
     * @param hotelList the hotel list
     * @return the list
     */
    @PostMapping("addHotels")
    public List<Hotel> addHotels(@RequestBody List<Hotel> hotelList) {
        logger.info("HotelController.addHotels - hotelList: {}", hotelList);
        return hotelService.addHotels(hotelList);
    }

    /**
     * Get hotels list.
     *
     * @return the list
     */
    @GetMapping("getHotels")
    public List<HotelDto> getHotelById() {
        logger.info("HotelController.getHotels");
        return hotelService.getAllHotels();
    }

    /**
     * Get hotel by id hotel.
     *
     * @param id the id
     * @return the hotel
     */
    @GetMapping("getHotelById/{id}")
    public HotelDto getHotelById(@PathVariable Long id) {
        logger.info("HotelController.addHotels - id: {}", id);
        Hotel hotelById = hotelService.getHotelById(id);
        HotelDto hotelDto = payloadTransformer.transformHotelDto(hotelById);
        return hotelDto;
    }

    /**
     * Delete hotel by id.
     *
     * @param hotelId the hotel id
     */
    @DeleteMapping("deleteById/{hotelId}")
    public void deleteHotelById(@PathVariable Long hotelId) {
        logger.info("HotelController.deleteHotelById - hotelId: {}", hotelId);
        hotelService.deleteHotelById(hotelId);
    }

    @PutMapping("updateById/{hotelId}")
    public HotelDto updateHotelById(@RequestBody Hotel hotel, @PathVariable Long hotelId) {
        logger.info("HotelController.updateHotelById - hotel: {}, hotelId: {}", hotel, hotelId);
        return hotelService.updateById(hotel,hotelId);
    }


}
