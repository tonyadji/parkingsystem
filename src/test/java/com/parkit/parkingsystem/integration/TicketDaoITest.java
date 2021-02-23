package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;


@ExtendWith(MockitoExtension.class)
class TicketDaoITest {
	
    private static TicketDAO ticketDAO = new TicketDAO();
    
    private static ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();

    private int parkingSpot;
    
    @BeforeEach
    private void setUpPerTest() throws Exception {
        parkingSpot = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);
    }

    @AfterAll
    private static void tearDown(){

    }

    @Test
    void shoudlReturnTrue_whenSaveTicketSuccessfully(){
       final Ticket ticket = new Ticket();
       ticket.setId(0);
       ticket.setInTime(new Date());
       ticket.setParkingSpot(new ParkingSpot(parkingSpot, ParkingType.CAR, true));
       ticket.setVehicleRegNumber("ALPHAROMEO");
       ticketDAO.saveTicket(ticket);
       final Ticket savedTicket = ticketDAO.getTicket("ALPHAROMEO");
       assertNotNull(savedTicket);
    }
    
    @Test
    void shouldReturn0_WhenGettingOccurencesOfUnregisteredVehicule() {
    	final int occurences = ticketDAO.countOccurrences("UNREGISTERED");
    	Assertions.assertEquals(0, occurences);
    }
    
    @Test
    void shouldReturnTheOccurences_WhenGettingOccurencesOfRegisteredVehicule() {
    	final int occurences = ticketDAO.countOccurrences("ALPHAROMEO");
    	Assertions.assertTrue(occurences > 0);
    }
}
