/**
 * 
 */
package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Connection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.util.InputReaderUtil;

/**
 * @author tonys
 *
 */
class ParkingSpotIT {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static DataBasePrepareService dataBasePrepareService;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        dataBasePrepareService = new DataBasePrepareService();
    }

    

    @Test
    void givenBikeSpotAvailable_WhenGetNextAvailableSlot_ThenShouldReturnMoreThan0() {
    	//arrange
    	
    	//act
    	int result = parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE);
    	System.out.print(result);
    	//assert
    	assertTrue(result > 0); 
    }
    
    @Test
    void givenCarSpotAvailable_WhenGetNextAvailableSlot_ThenShouldReturnMoreThan0() {
    	//arrange
    	
    	//act
    	int result = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);
    	
    	//assert
    	assertTrue(result > 0); 
    }
}
