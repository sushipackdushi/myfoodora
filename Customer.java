package fr.cs.groupS.myFoodora;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private Coordinate address;
    private String email;
    private String phoneNumber;
    private boolean receivesNotifications;
    private FidelityCard fidelityCard;
    private List<Order> orderHistory;

    public Customer(String name, String surname, String userID, String username, String password,
                    Coordinate address, String email, String phoneNumber) {
        super(name, surname, userID, username, password);
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.receivesNotifications = false;
        this.orderHistory = new ArrayList<>();
        this.fidelityCard = new BasicFidelityCard();
    }
    
    public void toggleNotificationPreference(boolean consent) {
        this.receivesNotifications = consent;
    }
    
    public boolean isReceivingNotifications() {
        return receivesNotifications;
    }
    
    @Override
    public String getUserType() {
        return "Customer";
    }

    public void registerToFidelityCard(FidelityCard card) {
        this.fidelityCard = card;
    }

    public void unregisterFidelityCard() {
        this.fidelityCard = new BasicFidelityCard(); 
    }

    public void addOrderToHistory(Order order) {
        this.orderHistory.add(order);
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public FidelityCard getFidelityCard() {
        return fidelityCard;
    }

    public Coordinate getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Order getOrder(String orderName) {
        for (Order order : orderHistory) {
            if (order.getName().equals(orderName)) {
                return order;
            }
        }
        return null;
    }

    public void setFidelityCard(FidelityCard card) {
        this.fidelityCard = card;
    }

    public void addOrder(String orderName, Order order) {
        this.orderHistory.add(order);
    }

    public void removeOrder(Order order) {
        this.orderHistory.remove(order);
    }
}