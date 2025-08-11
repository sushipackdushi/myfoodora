package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TargetProfitPolicyServiceFeeTest {
    private TargetProfitPolicyServiceFee policy;
    private MyFoodora myFoodora;
    private Order order1;
    private Order order2;
    
    @Before
    public void setUp() {
        policy = new TargetProfitPolicyServiceFee();
        myFoodora = new MyFoodora(2.5, 0.1, 5.0); 
        
        Coordinate customerLocation = new Coordinate(1.0f, 1.0f);
        Customer customer = new Customer("John", "Doe", "CUST1", "johndoe", "password",
            customerLocation, "john@example.com", "1234567890");
            
        Coordinate restaurantLocation = new Coordinate(2.0f, 2.0f);
        Restaurant restaurant = new Restaurant("Test Restaurant", "Name", "REST1", "testrest", "password",
            restaurantLocation, null, null);
            
        order1 = new Order(customer, restaurant);
        order1.setPrice(100.0);
        
        order2 = new Order(customer, restaurant);
        order2.setPrice(150.0);
        
        myFoodora.addCompletedOrder(order1);
        myFoodora.addCompletedOrder(order2);
    }
    
    @Test
    public void testMeetTargetProfitNormal() throws UnreachableTargetProfitException {
        double targetProfit = 30.0;
        
        // Expected service fee :
        // serviceFee = (targetProfit - totalIncome * markupPercentage) / numberOfOrders + deliveryCost
        // serviceFee = (30 - 250 * 0.1) / 2 + 5.0
        double expectedServiceFee = (30 - 250 * 0.1) / 2 + 5.0; 
        
        double actualServiceFee = policy.meetTargetProfit(myFoodora, targetProfit);
        assertEquals(expectedServiceFee, actualServiceFee, 0.001);
    }
    
    @Test(expected = UnreachableTargetProfitException.class)
    public void testMeetTargetProfitWithNoOrders() throws UnreachableTargetProfitException {
        MyFoodora emptyFoodora = new MyFoodora(2.5, 0.1, 5.0);
        policy.meetTargetProfit(emptyFoodora, 30.0);
    }
    
    @Test(expected = UnreachableTargetProfitException.class)
    public void testMeetTargetProfitWithNegativeMarkup() throws UnreachableTargetProfitException {
        myFoodora = new MyFoodora(2.5, -0.1, 5.0); 
        policy.meetTargetProfit(myFoodora, 30.0);
    }
    
    @Test
    public void testMeetTargetProfitWithHighTarget() throws UnreachableTargetProfitException {
        double targetProfit = 50.0;
        double serviceFee = policy.meetTargetProfit(myFoodora, targetProfit);
        
        assertTrue(serviceFee > myFoodora.getServiceFee());
    }
    
    @Test
    public void testMeetTargetProfitWithDifferentDeliveryCost() throws UnreachableTargetProfitException {
        MyFoodora foodora = new MyFoodora(2.5, 0.1, 7.0); 
        
        foodora.addCompletedOrder(order1);
        foodora.addCompletedOrder(order2);
        
        double targetProfit = 30.0;
        double serviceFee = policy.meetTargetProfit(foodora, targetProfit);
        
        assertTrue(serviceFee > policy.meetTargetProfit(myFoodora, targetProfit));
    }
    
    @Test
    public void testMeetTargetProfitWithDifferentMarkup() throws UnreachableTargetProfitException {
        MyFoodora foodora = new MyFoodora(2.5, 0.2, 5.0); 
        
        foodora.addCompletedOrder(order1);
        foodora.addCompletedOrder(order2);
        
        double targetProfit = 30.0;
        double serviceFee = policy.meetTargetProfit(foodora, targetProfit);
        
        assertTrue(serviceFee < policy.meetTargetProfit(myFoodora, targetProfit));
    }
} 