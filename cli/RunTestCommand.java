package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;
import java.util.*;

public class RunTestCommand implements CLICommand {
    @Override
    public void execute(MyFoodoraCLI cli, String[] args) throws Exception {
        if (!(cli.getCurrentUser() instanceof Manager)) {
            throw new IllegalStateException("Only managers can run system tests");
        }
        
        if (args.length != 0) {
            throw new IllegalArgumentException("Usage: " + getSyntax());
        }

        // TODO : Make this class support test scenario file
        
        System.out.println("Running system tests...");
        System.out.println("----------------------");
        
        MyFoodora myFoodora = cli.getMyFoodora();
        boolean allTestsPassed = true;
        
        try {
            // Test 1: User Management
            System.out.println("Test 1: User Management");
            testUserManagement(myFoodora);
            System.out.println("OK - User management tests passed");
            
            // Test 2: Restaurant Operations
            System.out.println("Test 2: Restaurant Operations");
            testRestaurantOperations(myFoodora);
            System.out.println("OK - Restaurant operations tests passed");
            
            // Test 3: Order Processing
            System.out.println("Test 3: Order Processing");
            testOrderProcessing(myFoodora);
            System.out.println("OK - Order processing tests passed");
            
            // Test 4: Delivery System
            System.out.println("Test 4: Delivery System");
            testDeliverySystem(myFoodora);
            System.out.println("OK - Delivery system tests passed");
            
            // Test 5: Fidelity Cards
            System.out.println("Test 5: Fidelity Cards");
            testFidelityCards(myFoodora);
            System.out.println("OK - Fidelity card tests passed");
            
        } catch (Exception e) {
            System.out.println("NOK - Test failed: " + e.getMessage());
            allTestsPassed = false;
        }
        
        System.out.println("\nTest Summary:");
        System.out.println("-------------");
        if (allTestsPassed) {
            System.out.println("All tests passed successfully!");
        } else {
            System.out.println("Some tests failed. Please check the error messages above.");
        }
    }
    
    private void testUserManagement(MyFoodora myFoodora) throws Exception {
        // Test user creation

        Customer testCustomer = new Customer("Test", "User", "USERID-TEST", "usertest", "password",
            new Coordinate(0, 0), "123 Test St", "1234567890");
        myFoodora.addUser(testCustomer);
        
        // Verify user exists
        boolean found = false;
        for (User user : myFoodora.getUsers()) {
            if (user.getUsername().equals("testuser")) {
                found = true;
                break;
            }
        }
        
        if (!found) {
            throw new Exception("Failed to add and retrieve user");
        }
        
        // Clean up
        myFoodora.removeUser(testCustomer);
    }
    
    private void testRestaurantOperations(MyFoodora myFoodora) throws Exception {
        Restaurant testRestaurant = new Restaurant("Test Restaurant", "testrest", "RESTAURANTID-TEST", "username", 
            "password", new Coordinate(0, 0), new Menu(), new ArrayList<Meal>());
        myFoodora.addUser(testRestaurant);
        
        // Test menu operations - adding different types of dishes
        Dish starterStd = new Starter("Salad", 8.0, "standard");
        Dish mainVeg = new MainDish("Veggie Pasta", 15.0, "vegetarian");
        Dish dessertGf = new Dessert("Fruit Bowl", 6.0, "glutenFree");
        
        Menu menu = testRestaurant.getMenu();
        menu.addDish(starterStd);
        menu.addDish(mainVeg);
        menu.addDish(dessertGf);
        
        if (menu.getDishes().size() != 3) {
            throw new Exception("Failed to add dishes to menu");
        }

        // Test meal creation - half meal (vegetarian)
        Meal halfMeal = new HalfMeal("Veggie Half");
        halfMeal.addDish(new Starter("Veg Soup", 7.0, "vegetarian"));
        halfMeal.addDish(mainVeg);
        menu.addMeal(halfMeal);

        // Test meal creation - full meal (standard)
        Meal fullMeal = new FullMeal("Standard Full");
        fullMeal.addDish(starterStd);
        fullMeal.addDish(new MainDish("Steak", 25.0, "standard"));
        fullMeal.addDish(new Dessert("Ice Cream", 5.0, "standard"));
        menu.addMeal(fullMeal);

        // Test meal-of-the-week
        menu.setMealOfTheWeek(fullMeal);
        
        // Verify meal prices (with discounts)
        double expectedHalfMealPrice = (7.0 + 15.0) * 0.95; // 5% discount
        double expectedFullMealPrice = (8.0 + 25.0 + 5.0) * 0.90; // 10% as meal of the week
        
        if (Math.abs(halfMeal.getPrice() - expectedHalfMealPrice) > 0.01 ||
            Math.abs(fullMeal.getPrice() - expectedFullMealPrice) > 0.01) {
            throw new Exception("Meal price calculation with discounts failed");
        }

        // Clean up
        myFoodora.removeUser(testRestaurant);
    }
    
    private void testOrderProcessing(MyFoodora myFoodora) throws Exception {
        // Create test restaurant with menu
        Restaurant testRestaurant = new Restaurant("Test Restaurant2", "testrest2", "RESTAURANTID-TEST2", "username", 
            "password", new Coordinate(0, 0), new Menu(), new ArrayList<Meal>());
        Customer testCustomer = new Customer("Test2", "User2", "USERID-TEST2", "usertest2", "password",
            new Coordinate(0, 0), "123 Test St", "1234567890");
        
        myFoodora.addUser(testRestaurant);
        myFoodora.addUser(testCustomer);

        // Setup menu
        Menu menu = testRestaurant.getMenu();
        Dish starter = new Starter("Soup", 8.0, "standard");
        Dish main = new MainDish("Fish", 20.0, "standard");
        menu.addDish(starter);
        menu.addDish(main);

        // Test à la carte ordering
        Order testOrder = new Order(testCustomer, testRestaurant);
        testOrder.addDish(starter);
        testOrder.addDish(main);
        
        double expectedPrice = 28.0; // No discount for à la carte
        if (Math.abs(testOrder.getPrice() - expectedPrice) > 0.01) {
            throw new Exception("À la carte order price calculation failed");
        }

        // Clean up
        myFoodora.removeUser(testRestaurant);
        myFoodora.removeUser(testCustomer);
    }
    
    private void testDeliverySystem(MyFoodora myFoodora) throws Exception {
        // String name, String surname, String userID, String username, String password,
                   // Coordinate position, String phoneNumber
        Courier testCourier = new Courier("COURIER", "COURIER", "USERID-COURIER2", "courier", "courier", new Coordinate(0, 0), "1234567890");
        myFoodora.addUser(testCourier);
        
        testCourier.setOnDuty(true);
        if (!testCourier.isOnDuty()) {
            throw new Exception("Failed to set courier duty status");
        }
        
        // Clean up
        myFoodora.removeUser(testCourier);
    }
    
    private void testFidelityCards(MyFoodora myFoodora) throws Exception {
        Customer testCustomer = new Customer("Test3", "User3", "USERID-TEST3", "usertest3", "password",
            new Coordinate(0, 0), "123 Test St", "1234567890");
        myFoodora.addUser(testCustomer);
        
        // Test each card type
        testCustomer.setFidelityCard(new BasicFidelityCard());
        if (!(testCustomer.getFidelityCard() instanceof BasicFidelityCard)) {
            throw new Exception("Failed to set basic fidelity card");
        }
        
        testCustomer.setFidelityCard(new PointFidelityCard());
        if (!(testCustomer.getFidelityCard() instanceof PointFidelityCard)) {
            throw new Exception("Failed to set point fidelity card");
        }
        
        testCustomer.setFidelityCard(new LotteryFidelityCard());
        if (!(testCustomer.getFidelityCard() instanceof LotteryFidelityCard)) {
            throw new Exception("Failed to set lottery fidelity card");
        }
        
        // Clean up
        myFoodora.removeUser(testCustomer);
    }

    @Override
    public String getDescription() {
        return "Run system tests to verify functionality (manager only)";
    }

    @Override
    public String getSyntax() {
        return "runTest";
    }
} 