package fr.cs.groupS.myFoodora;

import java.io.Serializable ;

public class DeliveryPolicyFactory implements Serializable {

	private static final long serialVersionUID = 5536315668997350002L;

	public DeliveryPolicyFactory() {
	}
	
	public DeliveryPolicy chooseDeliveryPolicy (String deliveryPolicyChoice) {
		DeliveryPolicy deliveryPolicy = null;
		switch(deliveryPolicyChoice){
			case("fastest"):
				deliveryPolicy = new FastestDeliveryPolicy();
				break;
			case("fairOccupation"):
				deliveryPolicy = new FairOccupationDeliveryPolicy();
				break;
		}
		return (deliveryPolicy);
	}
}