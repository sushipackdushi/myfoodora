package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;

public class OrderTest {
    private Order order;
    private Customer customer;
    private Restaurant restaurant;
    private MainDish dish1;
    private Dessert dish2;
    private Meal meal;
    private MyFoodora myFoodora;
    private Courier courier;
    
    @Before
    public void setUp() {
        // Create test objects
        Coordinate customerLocation = new Coordinate(1.0f, 1.0f);
        customer = new Customer("John", "Doe", "CUST1", "johndoe", "password", 
            customerLocation, "john@example.com", "1234567890");
            
        Coordinate restaurantLocation = new Coordinate(2.0f, 2.0f);
        restaurant = new Restaurant("Restaurant", "Name", "REST1", "restname", "password",
            restaurantLocation, null, null);
            
        // Create the order
        order = new Order(customer, restaurant);
        
        // Create dishes and meals
        dish1 = new MainDish("Steak", 15.0, "standard");
        dish2 = new Dessert("Ice Cream", 5.0, "standard");
        
        meal = new HalfMeal("Half Meal", dish1, dish2);
        
        // Setup MyFoodora
        myFoodora = new MyFoodora(2.5, 0.1, 5.0);
        
        // Setup courier
        Coordinate courierLocation = new Coordinate(1.5f, 1.5f);
        courier = new Courier("Bob", "Smith", "COUR1", "bobsmith", "password",
            courierLocation, "9876543210");
    }
    
    @Test
    public void testOrderInitialization() {
        assertNotNull(order.getUniqueID());
        assertEquals(customer, order.getCustomer());
        assertEquals(restaurant, order.getRestaurant());
        assertNotNull(order.getDate());
        assertEquals(0, order.getDishes().size());
        assertEquals(0, order.getMeals().size());
        assertEquals(restaurant.getLocation(), order.getAddressOfDelivery());
        assertNull(order.getCourier());
    }
    
    @Test
    public void testAddAndRemoveDishes() {
        order.addDish(dish1);
        order.addDish(dish2);
        
        assertEquals(2, order.getDishes().size());
        assertTrue(order.getDishes().contains(dish1));
        assertTrue(order.getDishes().contains(dish2));
        
        order.removeDish(dish1);
        assertEquals(1, order.getDishes().size());
        assertFalse(order.getDishes().contains(dish1));
        assertTrue(order.getDishes().contains(dish2));
    }
    
    @Test
    public void testAddAndRemoveMeals() {
        order.addMeal(meal);
        
        assertEquals(1, order.getMeals().size());
        assertTrue(order.getMeals().contains(meal));
        
        order.removeMeal(meal);
        assertEquals(0, order.getMeals().size());
        assertFalse(order.getMeals().contains(meal));
    }
    
    @Test
    public void testComputePrice() {
        order.addDish(dish1);
        order.addDish(dish2);
        order.addMeal(meal); 
        
        double expectedPrice = dish1.getPrice() + dish2.getPrice() + meal.getPrice();
        assertEquals(expectedPrice, order.computePrice(), 0.001);
    }
    
    @Test
    public void testSetAndGetDeliveryAddress() {
        Coordinate newAddress = new Coordinate(3.0f, 3.0f);
        order.setAddressOfDelivery(newAddress);
        assertEquals(newAddress, order.getAddressOfDelivery());
    }
    
    @Test
    public void testSetAndGetCourier() {
        order.setCourier(courier);
        assertEquals(courier, order.getCourier());
    }
    
    @Test
    public void testSubmitOrder() {
        order.addDish(dish1);
        order.addDish(dish2);
        courier.setOnDuty(true);
        myFoodora.addUser(courier);
        
        // Test without discount
        order.submit(false, myFoodora);
        
        assertNotNull(order.getCourier());
        assertEquals(20.0, order.getPrice(), 0.001); // 15.0 + 5.0
        
        // Verify counters increased
        assertEquals(1, dish1.getCounter());
        assertEquals(1, dish2.getCounter());
        assertEquals(1, restaurant.getCounter());
    }
    
    @Test
    public void testValidateOrderByCourier() {
        order.setCourier(courier);
        int initialDeliveries = courier.getDeliveredOrders();
        
        order.validateOrderByCourier(myFoodora);
        
        assertEquals(initialDeliveries + 1, courier.getDeliveredOrders());
        assertTrue(myFoodora.getCompletedOrders().contains(order));
    }
    
    @Test
    public void testSetDate() {
        Date newDate = new Date();
        order.setDate(newDate);
        assertEquals(newDate, order.getDate());
    }
    
} 