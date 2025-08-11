package fr.cs.groupS.myFoodora;

public class Courier extends User {
    private Coordinate position;
    private String phoneNumber;
    private int deliveredOrders;
    private boolean onDuty;

    public Courier(String name, String surname, String userID, String username, String password,
                   Coordinate position, String phoneNumber) {
        super(name, surname, userID, username, password);
        this.position = position;
        this.phoneNumber = phoneNumber;
        this.deliveredOrders = 0;
        this.onDuty = false;
    }

    public void setOnDuty(boolean onDuty) {
        this.onDuty = onDuty;
    }

    public void updatePosition(Coordinate newPosition) {
        this.position = newPosition;
    }

    public void incrementDeliveredOrders() {
        this.deliveredOrders++;
    }

    public int getDeliveredOrders() {
        return deliveredOrders;
    }

    public boolean isOnDuty() {
        return onDuty;
    }

    @Override
    public String getUserType() {
        return "Courier";
    }

    public int getCounter() {
        return deliveredOrders;
    }

    public Coordinate getPosition() {
        return position;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}