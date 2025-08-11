package fr.cs.groupS.myFoodora;

public class BasicFidelityCard implements FidelityCard {
    private static final long serialVersionUID = 1L;

    @Override
    public void applyDiscount(Order order) {
        // basic card only allows access to special offers
        // No discount is applied
    }

    @Override
    public String getCardType() {
        return "Basic";
    }
} 