package com.parkit.parkingsystem.constants;

public class Fare {
	
	private Fare() {}
	
    public static final double BIKE_RATE_PER_HOUR = 1.0;
    public static final double CAR_RATE_PER_HOUR = 1.5;
    //we assume that recurring user used the parking system at least 5 times
    public static final int RECURRING_TIMES = 5;
    //the 5% discount
    public static final double DISCOUNT = 0.05;
}
