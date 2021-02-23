package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
class SecondParkingServiceTest {

    private static ParkingService parkingService;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static TicketDAO ticketDAO;

    @BeforeEach
    private void setUpPerTest() {
        parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        
    }

    
    @Test
    void processIncomingVehicleTest(){
    	when(inputReaderUtil.readSelection()).thenReturn(1);
    	when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);
        parkingService.processIncomingVehicle();
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
        verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
    }
    
    @Test
    void shouldNotProcessIncomingVehicle_whenSpotNotAvailable(){
    	when(inputReaderUtil.readSelection()).thenReturn(1);
    	when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(0);
        parkingService.processIncomingVehicle();
        verify(parkingSpotDAO, Mockito.times(0)).updateParking(any(ParkingSpot.class));
        verify(ticketDAO, Mockito.times(0)).saveTicket(any(Ticket.class));
    }

    @Test
    void shouldReturnCarParkingSpot_WhenGetNextParkingNumberIfAvailableAndVehiculeTypeIsCar() {
    	//arrange
    	when(inputReaderUtil.readSelection()).thenReturn(1);
    	when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);
    	
    	//act
    	final ParkingSpot parkingResult = parkingService.getNextParkingNumberIfAvailable();
    	
    	//assertThat
    	assertEquals(ParkingType.CAR,parkingResult.getParkingType());
    	verify(inputReaderUtil).readSelection();
    	verify(parkingSpotDAO).getNextAvailableSlot(ParkingType.CAR);
    }
    
    @Test
    void shouldReturnBikeParkingSpot_WhenGetNextParkingNumberIfAvailableAndVehiculeTypeIsBike() {
    	//arrange
    	when(inputReaderUtil.readSelection()).thenReturn(2);
    	when(parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE)).thenReturn(1);
    	
    	//act
    	final ParkingSpot parkingResult = parkingService.getNextParkingNumberIfAvailable();
    	
    	//assertThat
    	assertEquals(ParkingType.BIKE,parkingResult.getParkingType());
    	verify(inputReaderUtil).readSelection();
    	verify(parkingSpotDAO).getNextAvailableSlot(ParkingType.BIKE);
    }
    
    @Test
    void shouldThrowException_WhenGetNextParkingNumberAndTypeWrongInput() {
    	//arrange
    	when(inputReaderUtil.readSelection()).thenReturn(3);
    	
    	//act
    	final ParkingSpot parkingResult = parkingService.getNextParkingNumberIfAvailable();
    	
    	//assertThat
    	assertNull(parkingResult);
    	verify(inputReaderUtil).readSelection();
    }
    
    @Test
    void givenParkingSportIsFull_WhenGetNextParkingNumberIfAvailable_ThenThrowException_() {
    	//arrange
    	when(inputReaderUtil.readSelection()).thenReturn(1);
    	when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(0);
    	
    	//act
    	final ParkingSpot parkingResult = parkingService.getNextParkingNumberIfAvailable();
    	
    	//assertThat
    	assertNull(parkingResult);
    	verify(inputReaderUtil).readSelection();
    	verify(parkingSpotDAO).getNextAvailableSlot(ParkingType.CAR);
    }
    
    @Test
    void givenBadVehiculeType_WhenGetNextParkingNumberIfAvailable_ThenThrowException() {
    	//arrange
    	when(inputReaderUtil.readSelection()).thenThrow(IllegalArgumentException.class);
    	
    	//act
    	final ParkingSpot parkingResult = parkingService.getNextParkingNumberIfAvailable();
    	
    	//assert
    	assertNull(parkingResult);
    	verify(inputReaderUtil).readSelection();
    }
    

    @Test
    void shouldNotProcess_WhenProcessExitingVehicle_WithNullAsRegistrationNumber(){
    	try {
    	when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn(null);
        parkingService.processExitingVehicle();
        verify(ticketDAO,times(0)).countOccurrences(ArgumentMatchers.anyString());
    	}catch (Exception e) {
			// TODO: handle exception
		}
    }
}
