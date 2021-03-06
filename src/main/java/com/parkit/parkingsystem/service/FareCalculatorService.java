package com.parkit.parkingsystem.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.DateConverterUtil;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }
        
        //get the in time and out time as LocalDateTime to take advantage of ChronoUnit methods
        LocalDateTime inTime = DateConverterUtil.convertToLocalDateTime(ticket.getInTime());
        LocalDateTime outTime = DateConverterUtil.convertToLocalDateTime(ticket.getOutTime());
        
        //get the difference in minutes between the inTime and the outTime
        //this difference will be divided per 60 and the result will be multiplied by the rate so that we get the price
        double differenceInMinutes = ChronoUnit.MINUTES.between(inTime,outTime);
        
        //return 0 fee if differenceInMinutes less than 30
        if(differenceInMinutes < 30) {
        	ticket.setPrice(0d);
        	return;
        }
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: 
            	setCarPriceForTheTicket(ticket, differenceInMinutes);
                break;
            
            case BIKE: 
                setBikePriceForTheTicket(ticket, differenceInMinutes);
                break;
            
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }

	public void applyDiscount(Ticket ticket) {
		double price = ticket.getPrice();
		ticket.setPrice(price - (price*Fare.DISCOUNT));
	}
	
	private void setCarPriceForTheTicket(final Ticket ticket, double differenceInMinutes) {
		ticket.setPrice((differenceInMinutes/60) * Fare.CAR_RATE_PER_HOUR);
	}
	
	private void setBikePriceForTheTicket(final Ticket ticket, double differenceInMinutes) {
		ticket.setPrice((differenceInMinutes/60) * Fare.BIKE_RATE_PER_HOUR);
	}
}