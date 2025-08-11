package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PointFidelityCardTest {
    private PointFidelityCard card;
    private Order order;
    private Customer customer;
    private Restaurant restaurant;
    
    @Before
    public void setUp() {
        card = new PointFidelityCard();
        
        // Setup test order
        Coordinate customerLocation = new Coordinate(1.0f, 1.0f);
        customer = new Customer("John", "Doe", "CUST1", "johndoe", "password",
            customerLocation, "john@example.com", "1234567890");
            
        Coordinate restaurantLocation = new Coordinate(2.0f, 2.0f);
        restaurant = new Restaurant("Test Restaurant", "Name", "REST1", "testrest", "password",
            restaurantLocation, null, null);
            
        order = new Order(customer, restaurant);
        order.setPrice(100.0); // Set a test price
    }
    
    @Test
    public void testInitialization() {
        assertEquals(0, card.getPoints());
        assertFalse(card.isDiscountAvailable());
        assertEquals("Point", card.getCardType());
    }
    
    @Test
    public void testComputeFidelityPoints() {
        // Test adding points for a 100€ order
        // Should add 10 points (0.1 points per euro)
        card.computeFidelityPoints(order);
        assertEquals(10, card.getPoints());
        assertFalse(card.isDiscountAvailable()); // Not enough points for discount
        
        // Add more points to reach threshold (100 points)
        order.setPrice(900.0); // Should add 90 points
        card.computeFidelityPoints(order);
        assertEquals(100, card.getPoints());
        assertTrue(card.isDiscountAvailable()); // Should now be available
    }
    
    @Test
    public void testApplyDiscount() {
        // First get enough points for a discount
        order.setPrice(1000.0); // Will give 100 points
        card.computeFidelityPoints(order);
        assertTrue(card.isDiscountAvailable());
        
        // Test applying discount to a new order
        Order newOrder = new Order(customer, restaurant);
        newOrder.setPrice(50.0);
        card.applyDiscount(newOrder);
        
        // Price should be reduced by 10%
        assertEquals(45.0, newOrder.getPrice(), 0.001);
        
        // Points and discount availability should be reset
        assertEquals(0, card.getPoints());
        assertFalse(card.isDiscountAvailable());
    }
    
    @Test
    public void testApplyDiscountWhenNotAvailable() {
        double initialPrice = order.getPrice();
        card.applyDiscount(order);
        
        // Price should not change when discount is not available
        assertEquals(initialPrice, order.getPrice(), 0.001);
    }
    
    @Test
    public void testAddPoints() {
        card.addPoints(50);
        assertEquals(50, card.getPoints());
        assertFalse(card.isDiscountAvailable());
        
        card.addPoints(50);
        assertEquals(100, card.getPoints());
        assertFalse(card.isDiscountAvailable());
    }
    
    @Test
    public void testMultipleDiscounts() {
        // Get enough points for first discount
        card.addPoints(100);
        assertFalse(card.isDiscountAvailable());
        
        // Apply first discount
        order.setPrice(100.0);
        card.applyDiscount(order);
        assertEquals(100.0, order.getPrice(), 0.001);
        assertFalse(card.isDiscountAvailable());
        assertEquals(100, card.getPoints());
        
        // Get points for second discount
        card.addPoints(100);
        assertFalse(card.isDiscountAvailable());
        
        // Apply second discount
        Order secondOrder = new Order(customer, restaurant);
        secondOrder.setPrice(200.0);
        card.applyDiscount(secondOrder);
        assertEquals(200.0, secondOrder.getPrice(), 0.001);
    }
} 