/**
 * 
 */
package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.util.InputReaderUtil;

/**
 * @author tonys
 *
 */
class ParkingSpotIT {

    private static ParkingSpotDAO parkingSpotDAO;
    private static DataBasePrepareService dataBasePrepareService;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception{
    	ParkingSpotDAO.setDataBaseConfig(new DataBaseTestConfig());
        parkingSpotDAO = new ParkingSpotDAO();
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    void setUpPerTest() {
    	dataBasePrepareService.clearDataBaseEntries();
    }

    @Test
    void givenBikeSpotAvailable_WhenGetNextAvailableSlot_ThenShouldReturnMoreThan0() {
    	//arrange
    	
    	//act
    	int result = parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE);
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
