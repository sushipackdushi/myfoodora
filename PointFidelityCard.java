package fr.cs.groupS.myFoodora;

public class PointFidelityCard implements FidelityCard {
    private static final long serialVersionUID = 1L;
    private static final int POINTS_THRESHOLD = 100;
    private static final double POINTS_PER_EURO = 0.1;
    private static final double DISCOUNT_RATE = 0.10; 
    
    private int points;
    private boolean discountAvailable;

    public PointFidelityCard() {
        this.points = 0;
        this.discountAvailable = false;
    }

    @Override
    public void applyDiscount(Order order) {
        if (discountAvailable) {
            double currentPrice = order.getPrice();
            order.setPrice(currentPrice * (1 - DISCOUNT_RATE));
            discountAvailable = false;
            points = 0; 
        }
    }

    public void computeFidelityPoints(Order order) {
        double orderPrice = order.getPrice();
        points += (int)(orderPrice * POINTS_PER_EURO);
        
        if (points >= POINTS_THRESHOLD) {
            discountAvailable = true;
        }
    }

    public int getPoints() {
        return points;
    }

    public boolean isDiscountAvailable() {
        return discountAvailable;
    }

    @Override
    public String getCardType() {
        return "Point";
    }

    public void addPoints(int i) {
        points += i;
    }
} 