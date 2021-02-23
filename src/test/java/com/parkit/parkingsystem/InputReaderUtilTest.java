/**
 * 
 */
package com.parkit.parkingsystem;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.util.InputReaderUtil;

/**
 * @author tonys
 *
 */
@ExtendWith(MockitoExtension.class)
class InputReaderUtilTest {
	
	private InputReaderUtil inputReaderUtil = new InputReaderUtil();
	
	@Test
	void testReadSelection_withCorrectInput_shouldReturnAnInteger() {
		//arrange
		String input = "5";
	    Scanner scan = new Scanner(new ByteArrayInputStream(input.getBytes()));
	    InputReaderUtil.setScanner(scan);
		//act
		final int result  = inputReaderUtil.readSelection();
		
		//assert
		Assertions.assertEquals(5, result);
	}
	
	@Test
	void testReadSelection_withBadInput_shouldThrowException() {
		//arrange
		String input = "alibaba";
	    Scanner scan = new Scanner(new ByteArrayInputStream(input.getBytes()));
	    InputReaderUtil.setScanner(scan);
		//act
		final int result  = inputReaderUtil.readSelection();
		//assert
		Assertions.assertEquals(-1, result);
	}
	
	@Test
	void testReadVehicleRegistrationNumber_withCorrectInput_shouldReturnAString() throws Exception {
		//arrange
		String input = "ABCDEF";
	    Scanner scan = new Scanner(new ByteArrayInputStream(input.getBytes()));
	    InputReaderUtil.setScanner(scan);
		//act
		final String result  = inputReaderUtil.readVehicleRegistrationNumber();
		
		//assert
		Assertions.assertEquals("ABCDEF", result);
	}
	
	@Test
	void testReadVehicleRegistrationNumber_withBlankString_shouldThrowException() throws Exception {
		//arrange
		String input = " ";
	    Scanner scan = new Scanner(new ByteArrayInputStream(input.getBytes()));
	    InputReaderUtil.setScanner(scan);
		
	    //act && assert
	    Assertions.assertThrows(IllegalArgumentException.class, ()-> inputReaderUtil.readVehicleRegistrationNumber());
		
	}
	
	@Test
	void testReadVehicleRegistrationNumber_withEmptyString_shouldThrowException() throws Exception {
		//arrange
		String input = "";
	    Scanner scan = new Scanner(new ByteArrayInputStream(input.getBytes()));
	    InputReaderUtil.setScanner(scan);
		
	    //act && assert
	    Assertions.assertThrows(Exception.class, ()-> inputReaderUtil.readVehicleRegistrationNumber());
		
	}
	
}
