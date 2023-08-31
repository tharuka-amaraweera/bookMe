package it.codegen.assignment.sun.travel.service;

import it.codegen.assignment.sun.travel.entity.Hotel;
import it.codegen.assignment.sun.travel.repository.HotelRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class HotelServiceImplTest {
    @InjectMocks
    HotelServiceImpl hotelService;

    @Mock
    HotelRepository hotelRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Optional<Hotel> hotel = Optional.of(new Hotel());
        when(hotelRepository.findById(Mockito.anyLong())).thenReturn(hotel);
    }

    @Test
    public void testHotelById(){
        hotelService.getHotelById(1L);
    }
}
