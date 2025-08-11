package fr.cs.groupS.myFoodora;

import java.util.Random;

public class LotteryFidelityCard implements FidelityCard {
    private static final long serialVersionUID = 1L;
    private static final double WIN_PROBABILITY = 0.01; // 1% chance to win
    private final Random random;

    public LotteryFidelityCard() {
        this.random = new Random();
    }

    @Override
    public void applyDiscount(Order order) {
        if (random.nextDouble() < WIN_PROBABILITY) {
            // Free meal!
            order.setPrice(0.0);
        }
    }

    @Override
    public String getCardType() {
        return "Lottery";
    }
} 