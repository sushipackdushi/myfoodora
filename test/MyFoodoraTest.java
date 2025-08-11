package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import java.util.Date;

public class MyFoodoraTest {
    private MyFoodora myFoodora;
    private static final double SERVICE_FEE = 2.5;
    private static final double MARKUP_PERCENTAGE = 0.1;
    private static final double DELIVERY_COST = 5.0;
    
    @Before
    public void setUp() {
        myFoodora = new MyFoodora(SERVICE_FEE, MARKUP_PERCENTAGE, DELIVERY_COST);
    }
    
    @Test
    public void testInitialization() {
        assertEquals(SERVICE_FEE, myFoodora.getServiceFee(), 0.001);
        assertEquals(MARKUP_PERCENTAGE, myFoodora.getMarkupPercentage(), 0.001);
        assertEquals(DELIVERY_COST, myFoodora.getDeliveryCost(), 0.001);
        assertNotNull(myFoodora.getUsers());
        assertTrue(myFoodora.getUsers().length > 0); 
    }
    
    @Test
    public void testUserManagement() {
        String userId = myFoodora.generateNewUserId();
        Customer customer = new Customer("John", "Doe", userId, "johndoe", "password", 
            new Coordinate(1.0f, 1.0f), "john@example.com", "1234567890");
        
        myFoodora.addUser(customer);
        try {
            User found = myFoodora.findUserById(userId);
            assertEquals(customer, found);
        } catch (UserNotFoundException e) {
            fail("User should have been found");
        }
        
        myFoodora.removeUser(customer);
        try {
            myFoodora.findUserById(userId);
            fail("User should have been removed");
        } catch (UserNotFoundException e) {
            // Expected
        }
    }
    
    @Test
    public void testLogin() {
        String userId = myFoodora.generateNewUserId();
        Customer customer = new Customer("John", "Doe", userId, "johndoe", "password", 
            new Coordinate(1.0f, 1.0f), "john@example.com", "1234567890");
        myFoodora.addUser(customer);
        
        User loggedIn = myFoodora.login("johndoe", "password");
        assertNotNull(loggedIn);
        assertEquals(customer, loggedIn);
        
        assertNull(myFoodora.login("johndoe", "wrongpassword"));
        assertNull(myFoodora.login("nonexistent", "password"));
    }
    
    @Test
    public void testCompletedOrders() {
        Order order1 = new Order(null, null);
        order1.setPrice(50.0);
        order1.setDate(new Date());
        
        Order order2 = new Order(null, null);
        order2.setPrice(30.0);
        Calendar pastDate = Calendar.getInstance();
        pastDate.add(Calendar.MONTH, -2);
        order2.setDate(pastDate.getTime());
        
        myFoodora.addCompletedOrder(order1);
        myFoodora.addCompletedOrder(order2);
        
        assertEquals(50.0, myFoodora.totalIncomeLastMonth(), 0.001);
    }
    
    @Test
    public void testDeliveryPolicyChange() {
        DeliveryPolicy fastestPolicy = new FastestDeliveryPolicy();
        DeliveryPolicy fairPolicy = new FairOccupationDeliveryPolicy();
        
        myFoodora.setDeliveryPolicy(fastestPolicy);
        assertEquals(fastestPolicy, myFoodora.getDeliveryPolicy());
        
        myFoodora.setDeliveryPolicy(fairPolicy);
        assertEquals(fairPolicy, myFoodora.getDeliveryPolicy());
    }
    
    @Test
    public void testTargetProfitPolicyChange() {
        TargetProfitPolicy serviceFeePolicy = new TargetProfitPolicyServiceFee();
        TargetProfitPolicy markupPolicy = new TargetProfitPolicyMarkup();
        
        myFoodora.setTargetProfitPolicy(serviceFeePolicy);
        assertEquals(serviceFeePolicy, myFoodora.getTargetProfitPolicy());
        
        myFoodora.setTargetProfitPolicy(markupPolicy);
        assertEquals(markupPolicy, myFoodora.getTargetProfitPolicy());
    }
    
    @Test
    public void testFindAvailableCourier() {
        String userId = myFoodora.generateNewUserId();
        Courier courier = new Courier("John", "Doe", userId, "johndoe", "password",
            new Coordinate(1.0f, 1.0f), "1234567890");
        courier.setOnDuty(true);
        myFoodora.addUser(courier);
        
        Courier found = myFoodora.findAvailableCourier();
        assertNotNull(found);
        assertEquals(courier, found);
        
        courier.setOnDuty(false);
        assertNull(myFoodora.findAvailableCourier());
    }
} 