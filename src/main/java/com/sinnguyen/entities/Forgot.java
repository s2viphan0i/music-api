package com.sinnguyen.entities;

import java.io.Serializable;

public class Forgot implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String code;
	private String timestamp;

	public Forgot() {
		super();
	}

	public Forgot(int id, String code, String timestamp) {
		super();
		this.id = id;
		this.code = code;
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
