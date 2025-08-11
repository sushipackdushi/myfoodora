package fr.cs.groupS.myFoodora;

public class DeactivatedAccountException extends Exception {

	private static final long serialVersionUID = -8633863178005588991L;

	public DeactivatedAccountException() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param err : message shown upon throwing of exception
	 */
	public DeactivatedAccountException(String err) {
		super(err);
		// TODO Auto-generated constructor stub
	}

}
