package com.abc.priceengine.protocol;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ResponseEnvelope<T> {
	
	private HttpStatus status;
	private String message;
	private T body;
	
	public ResponseEnvelope(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public ResponseEnvelope(HttpStatus status, String message, T body) {
		this.status = status;
		this.message = message;
		this.body = body;
	}
}
