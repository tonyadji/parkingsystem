package com.parkit.parkingsystem.model;

import java.util.Date;

public class Ticket {
	private int id;
	private ParkingSpot parkingSpot;
	private String vehicleRegNumber;
	private double price;
	private Date inTime;
	private Date outTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ParkingSpot getParkingSpot() {
		return parkingSpot;
	}

	public void setParkingSpot(ParkingSpot parkingSpot) {
		this.parkingSpot = parkingSpot;
	}

	public String getVehicleRegNumber() {
		return vehicleRegNumber;
	}

	public void setVehicleRegNumber(String vehicleRegNumber) {
		this.vehicleRegNumber = vehicleRegNumber;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getInTime() {
		if(this.inTime != null) {
			return new Date(this.inTime.getTime());
		}
		return null;
	}

	public void setInTime(Date inTime) {
		if (inTime != null) {
			this.inTime = new Date(inTime.getTime());
		}
	}

	public Date getOutTime() {
		if(this.outTime != null) {
			return new Date(this.outTime.getTime());
		}
		return null;
	}

	public void setOutTime(Date outTime) {
		if (outTime != null) {
			this.outTime = new Date(outTime.getTime());
		}
	}
}
