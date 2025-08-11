package fr.cs.groupS.myFoodora;

import java.io.Serializable ;

public class FastestDeliveryPolicy implements DeliveryPolicy,Serializable {


	private static final long serialVersionUID = 1795690985946748999L;

	public FastestDeliveryPolicy() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Assigns a courier to order according to fastest delivery 
	 * @param myFoodora : MyFoodora system
	 * @param order : order which need to be allocated to a courier
	 */
	
// TODO: Create appropriate Order class to enable the below dependencies

	@Override
	public void assignCourier(MyFoodora myFoodora, Order order) {
		double xCord = order.getAddressOfDelivery().getX();
		double yCord = order.getAddressOfDelivery().getY();
		
		Courier assignedCourier = null;
		double minDist = Double.POSITIVE_INFINITY;
		
		for (User user: myFoodora.getUsers()) {
			if (user instanceof Courier) {
				Courier courier = (Courier)user;
				double x = courier.getPosition().getX();
				double y = courier.getPosition().getY(); 
				double dist = Math.pow(Math.pow(x - xCord, 2) + Math.pow(y - yCord, 2), 0.5);
				
				if (courier.isOnDuty() && (dist < minDist)) {
					assignedCourier = courier;
					minDist = dist ;
				}
			}
		}
		order.setCourier(assignedCourier);
	}
}
