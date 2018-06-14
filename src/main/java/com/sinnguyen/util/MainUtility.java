package com.sinnguyen.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainUtility {
	public static Date StringtoDate(String date, String format) {
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	public static java.sql.Date convertDate(java.util.Date date) {
	    return new java.sql.Date(date.getTime());
	}
	public static String dateToStringFormat(Date date, String formatOutput) {
		String result = "";
		try {
			DateFormat df = new SimpleDateFormat(formatOutput);
			result = df.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
              sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            
        }
        return null;
    }
}
