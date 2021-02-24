package com.parkit.parkingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

public class TicketDAO {

	private static final Logger logger = LogManager.getLogger("TicketDAO");

	private static DataBaseConfig dataBaseConfig = new DataBaseConfig();

	public boolean saveTicket(Ticket ticket) {
		Connection con = null;
		try {
			con = dataBaseConfig.getConnection();
			// use try with-resources to ensure the stream will be closed
			try (PreparedStatement ps = con.prepareStatement(DBConstants.SAVE_TICKET)) {
				// ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
				ps.setInt(1, ticket.getParkingSpot().getId());
				ps.setString(2, ticket.getVehicleRegNumber());
				ps.setDouble(3, ticket.getPrice());
				ps.setTimestamp(4, new Timestamp(ticket.getInTime().getTime()));
				ps.setTimestamp(5,
						(ticket.getOutTime() == null) ? null : (new Timestamp(ticket.getOutTime().getTime())));
				return ps.execute();
			}

		} catch (Exception ex) {
			logger.error("Error fetching next available slot", ex);
			return false;
		} finally {
			dataBaseConfig.closeConnection(con);
		}
	}

	public Ticket getTicket(String vehicleRegNumber) {
		Connection con = null;
		Ticket ticket = null;
		try {
			con = dataBaseConfig.getConnection();
			try (PreparedStatement ps = con.prepareStatement(DBConstants.GET_TICKET)) {

				// ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
				ps.setString(1, vehicleRegNumber);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					ticket = new Ticket();
					ParkingSpot parkingSpot = new ParkingSpot(rs.getInt(1), ParkingType.valueOf(rs.getString(6)),
							false);
					ticket.setParkingSpot(parkingSpot);
					ticket.setId(rs.getInt(2));
					ticket.setVehicleRegNumber(vehicleRegNumber);
					ticket.setPrice(rs.getDouble(3));
					ticket.setInTime(rs.getTimestamp(4));
					ticket.setOutTime(rs.getTimestamp(5));
				}
				dataBaseConfig.closeResultSet(rs);
				dataBaseConfig.closePreparedStatement(ps);
			}

		} catch (Exception ex) {
			logger.error("Error fetching next available slot", ex);
		} finally {
			dataBaseConfig.closeConnection(con);
		}
		return ticket;
	}

	public boolean updateTicket(Ticket ticket) {
		Connection con = null;
		try {
			con = dataBaseConfig.getConnection();
			try (PreparedStatement ps = con.prepareStatement(DBConstants.UPDATE_TICKET)) {
				ps.setDouble(1, ticket.getPrice());
				ps.setTimestamp(2, new Timestamp(ticket.getOutTime().getTime()));
				ps.setInt(3, ticket.getId());
				ps.execute();
				return true;
			}
		} catch (Exception ex) {
			logger.error("Error saving ticket info", ex);
		} finally {
			dataBaseConfig.closeConnection(con);
		}
		return false;
	}

	public int countOccurrences(String vehicleRegNumber) {
		int occurences = 0;
		try (Connection con = dataBaseConfig.getConnection()){
			PreparedStatement ps = con.prepareStatement(DBConstants.COUNT_OCCURENCES);
				ps.setString(1, vehicleRegNumber);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					occurences = rs.getInt(1);
				}
				dataBaseConfig.closeResultSet(rs);
				dataBaseConfig.closePreparedStatement(ps);
			
		} catch (Exception ex) {
			logger.error("Error occurs while counting", ex);
		} 
		return occurences;
	}
	
	public static void setDataBaseConfig(DataBaseConfig newConfig) {
		dataBaseConfig = newConfig;
	}
}
