package fr.cs.groupS.myFoodora;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

public class MyFoodora implements Serializable {
    private static final long serialVersionUID = -6956532311204306476L;

    private int userLastID;
    private ArrayList<User> users;
    private ArrayList<Order> completedOrders;
    
    private double serviceFee;
    private double markupPercentage;
    private double deliveryCost;
    
    private TargetProfitPolicy targetProfitPolicy = new TargetProfitPolicyServiceFee();
    private DeliveryPolicy deliveryPolicy = new FastestDeliveryPolicy();
    private SorterFoodItem shippedOrderPolicy = new SorterCounter();
    private UserFactory userFactory;
    
    public MyFoodora(double serviceFee, double markupPercentage, double deliveryCost) {
        this.users = new ArrayList<>();
        this.completedOrders = new ArrayList<>();
        this.serviceFee = serviceFee;
        this.markupPercentage = markupPercentage;
        this.deliveryCost = deliveryCost;
        this.userFactory = new UserFactory();
        this.userLastID = 0;
        
        // Add default manager
        users.add(new Manager("Admin", "Admin", "admin", "admin", "admin123"));
    }

    public double totalIncomeLastMonth() {
        double total = 0;
        Calendar oneMonthAgo = Calendar.getInstance();
        oneMonthAgo.add(Calendar.MONTH, -1);
        
        for (Order order : completedOrders) {
            if (order.getDate().after(oneMonthAgo.getTime())) {
                total += order.getPrice();
            }
        }
        return total;
    }

    public double getMarkupPercentage() {
        return markupPercentage;
    }

    public void setMarkupPercentage(double markupPercentage) {
        this.markupPercentage = markupPercentage;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public User[] getUsers() {
        return users.toArray(new User[0]);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public User findUserById(String userId) throws UserNotFoundException {
        for (User user : users) {
            if (user.getUserID().equals(userId)) {
                return user;
            }
        }
        throw new UserNotFoundException("User with ID " + userId + " not found");
    }

    public String generateNewUserId() {
        return String.format("USER%06d", ++userLastID);
    }

    public void setDeliveryPolicy(DeliveryPolicy policy) {
        this.deliveryPolicy = policy;
    }

    public void setTargetProfitPolicy(TargetProfitPolicy policy) {
        this.targetProfitPolicy = policy;
    }

    public void setSorterFoodItemPolicy(SorterFoodItem policy) {
        this.shippedOrderPolicy = policy;
    }

    public void addCompletedOrder(Order order) {
        completedOrders.add(order);
    }

    public ArrayList<Order> getCompletedOrders() {
        return completedOrders;
    }

    public DeliveryPolicy getDeliveryPolicy() {
        return deliveryPolicy;
    }

    public TargetProfitPolicy getTargetProfitPolicy() {
        return targetProfitPolicy;
    }

    public SorterFoodItem getShippedOrderPolicy() {
        return shippedOrderPolicy;
    }

    public UserFactory getUserFactory() {
        return userFactory;
    }

    public Courier findAvailableCourier() {
        for (User user : users) {
            if (user instanceof Courier) {
                Courier courier = (Courier) user;
                if (courier.isOnDuty()) {
                    return courier;
                }
            }
        }
        return null;
    }

    public User login(String username, String password) {
        for (User user : users) {
            // in real life should be hashed !
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void setProfitPolicy(TargetProfitPolicy policy) {
        this.targetProfitPolicy = policy;
    }

    public void notifySpecialOffer(Restaurant restaurant, Meal meal) {
        // TODO Auto-generated constructor stub
    }
}
