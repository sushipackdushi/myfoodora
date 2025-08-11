package fr.cs.groupS.myFoodora;

import java.io.Serializable ;

public class FidelityCardFactory implements Serializable {

	private static final long serialVersionUID = 6388508288500957700L;

	public FidelityCardFactory() {
	}
	
	/**
	 * creates Fidelity Card of given type : BasicFidelityCard, PointFidelityCard, LotteryFidelityCard or Null to unregister
	 * @param cardType : "basic", "point", "lottery" or "none" to unregister
	 * @return card : Fidelity Card created
	 */
	public FidelityCard createFidelityCard (String cardType){
		FidelityCard card = null;
		
		switch(cardType){
			case("basic"):
				card = new BasicFidelityCard();
				break;
			case("point"):
				card = new PointFidelityCard();
				break;
			case("lottery"):
				card = new LotteryFidelityCard();
				break;
			default: break;
		}
		return card;
	}
}

