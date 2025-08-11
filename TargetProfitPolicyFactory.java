package fr.cs.groupS.myFoodora;

import java.io.Serializable ;

public class TargetProfitPolicyFactory implements Serializable {

	private static final long serialVersionUID = -3780557684207260282L;

	public TargetProfitPolicyFactory() {
	}
	
	public TargetProfitPolicy chooseTargetProfitPolicy (String profitPolicy) {
		TargetProfitPolicy targetProfitPolicy = null;
		switch(profitPolicy){
			case("deliveryCost"):
				targetProfitPolicy = new TargetProfitPolicyDeliveryCost();
				break;
			case("markup"):
				targetProfitPolicy = new TargetProfitPolicyMarkup();
				break;
			case("serviceFee"):
				targetProfitPolicy = new TargetProfitPolicyServiceFee();
				break;
		}
		return (targetProfitPolicy);
	}
}
