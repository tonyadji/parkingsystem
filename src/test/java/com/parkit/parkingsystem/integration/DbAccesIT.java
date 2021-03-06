/**
 * 
 */
package com.parkit.parkingsystem.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.util.DBAccessUtil;

/**
 * @author tonys
 *
 */
public class DbAccesIT {
    
    @Test
    void dbAccess() {
    	DBAccessUtil db = DBAccessUtil.loadDbAccess();
    	Assertions.assertEquals("parkit", db.getUsername());
    }
}
