package com.sinnguyen.model;

import java.io.Serializable;

public class ForgotDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String code;
	private String timestamp;

	public ForgotDTO() {
		super();
	}

	public ForgotDTO(int id, String code, String timestamp) {
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
