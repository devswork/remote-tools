/**
 * 
 */
package com.github.devswork.util;

import java.util.UUID;

/**
 * @author devswork
 */

public class UUIDUtil {

	public static long getUUIDLong() { return Math.abs(UUID.randomUUID().getLeastSignificantBits()); }

	public static long getAbsUUIDLong() {
		return Math.abs(getUUIDLong());
	}

	public static String getUUIDString() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}

	private UUID uuID = UUID.randomUUID();

	public String getInstanceUUIDString(){
	    return uuID.toString();
	}

	public long getInstanceUUIDLong(){
        return uuID.getMostSignificantBits();
    }

}
