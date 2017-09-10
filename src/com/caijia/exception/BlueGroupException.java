package com.caijia.exception;

/**
 * blue group exception
 * @author dg
 *
 */
public class BlueGroupException extends Exception{

	public BlueGroupException(String message,Throwable t){
		super(message, t);
	}
	
	public BlueGroupException(String message){
		super(message);
	}
	
}
