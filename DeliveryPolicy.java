package fr.cs.groupS.myFoodora;

public interface DeliveryPolicy {
	
	/**
	 * Assigns courier to order based one fastest delivery or fair-occupation delivery policy
	 * @param myFoodora : MyFoodora system
	 * @param order : order to be assigned
	 */
	public abstract void assignCourier(MyFoodora myFoodora, Order order);

}
