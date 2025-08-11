package fr.cs.groupS.myFoodora;

import java.io.Serializable;

public class UnreachableTargetProfitException extends Exception implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9070158922691940906L;


	public UnreachableTargetProfitException() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param arg0 : 
	 */
	public UnreachableTargetProfitException(String arg0) {
		super(arg0);
	}

}
