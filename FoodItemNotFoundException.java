package fr.cs.groupS.myFoodora;

public class FoodItemNotFoundException extends Exception {


	private static final long serialVersionUID = -9070158922691940906L;


	public FoodItemNotFoundException() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param err :
	 */
	public FoodItemNotFoundException(String err) {
		super(err);
	}
}