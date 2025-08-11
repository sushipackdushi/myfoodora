package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class CustomerTest {
    private Customer customer;
    private Coordinate address;
    private Order order1;
    private Order order2;
    private Restaurant restaurant;
    
    @Before
    public void setUp() {
        address = new Coordinate(1.0f, 2.0f);
        customer = new Customer("John", "Doe", "CUST001", "johndoe", "password",
            address, "john@example.com", "1234567890");
            
        // Setup restaurant for orders
        Coordinate restLocation = new Coordinate(2.0f, 3.0f);
        restaurant = new Restaurant("Test Restaurant", "Name", "REST001",
            "testrest", "password", restLocation, null, null);
            
        // Create test orders
        order1 = new Order(customer, restaurant);
        order2 = new Order(customer, restaurant);
    }
    
    @Test
    public void testCustomerInitialization() {
        assertEquals("John", customer.getName());
        assertEquals("Doe", customer.getSurname());
        assertEquals("CUST001", customer.getUserID());
        assertEquals("johndoe", customer.getUsername());
        assertEquals("password", customer.getPassword());
        assertEquals(address, customer.getAddress());
        assertEquals("john@example.com", customer.getEmail());
        assertEquals("1234567890", customer.getPhoneNumber());
        assertEquals("Customer", customer.getUserType());
        assertFalse(customer.isReceivingNotifications());
        assertTrue(customer.getFidelityCard() instanceof BasicFidelityCard);
    }
    
    @Test
    public void testNotificationPreference() {
        assertFalse(customer.isReceivingNotifications());
        
        customer.toggleNotificationPreference(true);
        assertTrue(customer.isReceivingNotifications());
        
        customer.toggleNotificationPreference(false);
        assertFalse(customer.isReceivingNotifications());
    }
    
    @Test
    public void testFidelityCardOperations() {
        // Test initial state
        assertTrue(customer.getFidelityCard() instanceof BasicFidelityCard);
        
        // Test registering new fidelity card
        FidelityCard pointCard = new PointFidelityCard();
        customer.registerToFidelityCard(pointCard);
        assertEquals(pointCard, customer.getFidelityCard());
        
        // Test unregistering
        customer.unregisterFidelityCard();
        assertTrue(customer.getFidelityCard() instanceof BasicFidelityCard);
        
        // Test setting directly
        FidelityCard lotteryCard = new LotteryFidelityCard();
        customer.setFidelityCard(lotteryCard);
        assertEquals(lotteryCard, customer.getFidelityCard());
    }
    
    @Test
    public void testOrderHistory() {
        // Test initial state
        assertTrue(customer.getOrderHistory().isEmpty());
        
        // Test adding orders
        customer.addOrderToHistory(order1);
        assertEquals(1, customer.getOrderHistory().size());
        assertTrue(customer.getOrderHistory().contains(order1));
        
        customer.addOrderToHistory(order2);
        assertEquals(2, customer.getOrderHistory().size());
        assertTrue(customer.getOrderHistory().contains(order2));
        
        // Test getting specific order
        assertEquals(order1, customer.getOrder(order1.getName()));
        assertEquals(order2, customer.getOrder(order2.getName()));
        assertNull(customer.getOrder("NonexistentOrder"));
        
        // Test removing order
        customer.removeOrder(order1);
        assertEquals(1, customer.getOrderHistory().size());
        assertFalse(customer.getOrderHistory().contains(order1));
        assertTrue(customer.getOrderHistory().contains(order2));
    }
    
    @Test
    public void testInheritance() {
        assertTrue(customer instanceof User);
    }
} 