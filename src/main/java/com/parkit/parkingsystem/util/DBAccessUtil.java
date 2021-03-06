/**
 * 
 */
package com.parkit.parkingsystem.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author tonys
 *
 */
public class DBAccessUtil {

	private String username;
	
	private String password;
	
	public static DBAccessUtil loadDbAccess() {
		final DBAccessUtil dbaccess = new DBAccessUtil();
		try {
			final List<String> access = Files.readAllLines(Paths.get("src/main/resources/dbaccess.txt"));
			dbaccess.setUsername(access.get(0).split(":")[1]);
			dbaccess.setPassword(access.get(1).split(":")[1]);
		} catch (IOException e) {
			System.out.println("Unable to read config file and retrieve dbaccess !");
		}
		return dbaccess;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
