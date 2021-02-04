package com.parkit.parkingsystem.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }
        
        //get the in time and out time as LocalDateTime to take advantage of ChronoUnit methods
        LocalDateTime inTime = convertToLocalDateTimeViaInstant(ticket.getInTime());
        LocalDateTime outTime = convertToLocalDateTimeViaInstant(ticket.getOutTime());
        //get the difference in minutes between the inTime and the outTime
        //this difference will be divided per 60 and the result will be multiplied by the rate so that we get the price
        double differenceInMinutes = ChronoUnit.MINUTES.between(inTime,outTime);
        
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice((differenceInMinutes/60) * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice((differenceInMinutes/60) * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
    
    private LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDateTime();
    }

	public void applyDiscount(Ticket ticket) {
		double price = ticket.getPrice();
		ticket.setPrice(price - (price*Fare.DISCOUNT));
	}
}