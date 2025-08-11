package fr.cs.groupS.myFoodora;

import java.io.Serializable ;

public class FairOccupationDeliveryPolicy implements DeliveryPolicy,Serializable {

	private static final long serialVersionUID = 7601014307289324431L;

	public FairOccupationDeliveryPolicy() {
	}

	@Override
	public void assignCourier(MyFoodora myFoodora, Order order) {

		Courier assignedCourier = null;
		int minCompletedDeliveries = 999999999;
		
		for (User user: myFoodora.getUsers()) {
			if (user instanceof Courier) {
				Courier courier = (Courier) user;
				int numCompletedDeliveries = courier.getCounter();
				
				if (courier.isOnDuty() && (numCompletedDeliveries < minCompletedDeliveries)) {
					assignedCourier = courier;
					minCompletedDeliveries = numCompletedDeliveries ;
				}
			}
		}
		order.setCourier(assignedCourier);
	}
}
