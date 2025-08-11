package fr.cs.groupS.myFoodora;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Order implements Serializable {

    private static final long serialVersionUID = -372769376339718757L;
    
    protected static int lastID = 1;
    
    private int uniqueID;
    private Calendar dateOfOrder;
    private Customer customer;
    private Restaurant restaurant;
    private ArrayList<Dish> dishes;
    private ArrayList<Meal> meals;
    private double price;
    private Courier courier;
    private Coordinate addressOfDelivery;
    
    /**
     * creates a new Order object given a customer and a target restaurant
     * @param customer : the customer making an order
     * @param restaurant : the target restaurant
     */
    public Order(Customer customer, Restaurant restaurant) {
        this.uniqueID = lastID;
        lastID++;
        
        this.customer = customer;
        this.restaurant = restaurant;
        this.dishes = new ArrayList<>();
        this.meals = new ArrayList<>();
        this.dateOfOrder = Calendar.getInstance();
        this.courier = null;
        
        // Set delivery address only if restaurant is not null
        if (restaurant != null) {
            this.addressOfDelivery = restaurant.getLocation();
        } else {
            this.addressOfDelivery = null;
        }
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
    
    public Date getDate() {
        return dateOfOrder.getTime();
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }
    
    public void addDish(Dish dish) {
        this.dishes.add(dish);
    }
    
    public void removeDish(Dish dish) {
        this.dishes.remove(dish);
    }
    
    public ArrayList<Meal> getMeals() {
        return meals;
    }
    
    public void addMeal(Meal meal) {
        this.meals.add(meal);
    }
    
    public void removeMeal(Meal meal) {
        this.meals.remove(meal);
    }

    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }

    public double computePrice() {
        double total = 0;
        for (Dish dish : this.dishes) {
            total += dish.getPrice();
        }
        for (Meal meal : this.meals) {
            total += meal.getPrice();
        }
        return total;
    }
    
    public Coordinate getAddressOfDelivery() {
        return addressOfDelivery;
    }

    public void setAddressOfDelivery(Coordinate addressOfDelivery) {
        this.addressOfDelivery = addressOfDelivery;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    /**
     * when the order is validated
     *      we allocate a courier and put the order on his board
     *      we increase the counter of the restaurant
     *      we increase all the counters of the picked items
     *      we compute the price of the order according to a eventual reduction
     *      if necessary we add the fidelity points to the card of the customer
     * @param applyDiscount : "true" if the customer wants to apply a reduction using his fidelity card
     * @param myFoodora : myFoodora core
     */
    public void submit(boolean applyDiscount, MyFoodora myFoodora) {
        // Assign courier based on delivery policy
        myFoodora.getDeliveryPolicy().assignCourier(myFoodora, this);
        
        // Update counters
        if (restaurant != null) {
            restaurant.increaseCounter();
        }
        for (Dish dish : dishes) {
            dish.increaseCounter();
        }
        for (Meal meal : meals) {
            meal.increaseCounter();
        }
        
        // Calculate price
        this.price = this.computePrice();
        
        // Apply fidelity card benefits
        if (customer != null) {
            FidelityCard fidelityCard = customer.getFidelityCard();
            if (applyDiscount) {
                fidelityCard.applyDiscount(this);
            }
            if (fidelityCard instanceof PointFidelityCard) {
                ((PointFidelityCard) fidelityCard).computeFidelityPoints(this);
            }
        }
    }
    
    /**
     * indicate that the courier has accepted the delivery call of this order
     *      the order is then completed
     * @param myFoodora : myFoodora system
     */
    public void validateOrderByCourier(MyFoodora myFoodora) {
        if (this.courier != null) {
            this.courier.incrementDeliveredOrders();
            myFoodora.addCompletedOrder(this);
        }
    }

    @Override
    public String toString() {
        return "Order [ID: " + this.uniqueID + "\n"
                + "Date: " + dateOfOrder.get(Calendar.DAY_OF_MONTH) + " " 
                + dateOfOrder.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) + " " 
                + dateOfOrder.get(Calendar.YEAR) + "\n" 
                + "Customer: " + customer + "\n" 
                + "Restaurant: " + restaurant + "\n" 
                + "Dishes: " + dishes + "\n"
                + "Meals: " + meals + "\n"
                + "Price: " + price + "\n"
                + "Courier: " + courier + "\n"
                + "Delivery Address: " + addressOfDelivery + "]\n";
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Order) {
            Order order = (Order)o;
            return this.uniqueID == order.getUniqueID();
        }
        return false;
    }

    public String getName() {
        return "Order " + uniqueID;
    }

    public void setDate(Date orderDate) {
        this.dateOfOrder.setTime(orderDate);
    }
}
