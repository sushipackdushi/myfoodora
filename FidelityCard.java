package fr.cs.groupS.myFoodora;

public interface FidelityCard extends java.io.Serializable {
    void applyDiscount(Order order);
    String getCardType();
} 