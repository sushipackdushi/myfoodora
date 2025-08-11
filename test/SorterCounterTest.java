package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class SorterCounterTest {
    private SorterCounter sorter;
    private ArrayList<FoodItem> foodItems;
    private Starter dish1;
    private MainDish dish2;
    private Dessert dish3;
    private Meal meal1;
    private Meal meal2;
    
    @Before
    public void setUp() {
        sorter = new SorterCounter();
        foodItems = new ArrayList<>();
        
        dish1 = new Starter("Salad", 5.0, "standard");
        dish2 = new MainDish("Steak", 15.0, "standard");
        dish3 = new Dessert("Ice Cream", 5.0, "standard");
        
        ArrayList<Dish> mealDishes1 = new ArrayList<>();
        mealDishes1.add(dish1);
        mealDishes1.add(dish2);
        meal1 = new FullMeal("Full Meal 1", dish2, dish1, dish3);
        
        ArrayList<Dish> mealDishes2 = new ArrayList<>();
        mealDishes2.add(dish2);
        mealDishes2.add(dish3);
        meal2 = new HalfMeal("Half Meal 1", dish2, dish3);
        
        // Set different counters
        for(int i = 0; i < 5; i++) dish1.increaseCounter();
        for(int i = 0; i < 3; i++) dish2.increaseCounter();
        for(int i = 0; i < 4; i++) dish3.increaseCounter(); 
        for(int i = 0; i < 2; i++) meal1.increaseCounter(); 
        for(int i = 0; i < 6; i++) meal2.increaseCounter(); 
        
        // Add items to the list
        foodItems.add(dish1);
        foodItems.add(dish2);
        foodItems.add(dish3);
        foodItems.add(meal1);
        foodItems.add(meal2);
    }
    
    @Test
    public void testMaxFoodItemNormal() {
        FoodItem maxItem = sorter.maxFoodItem(foodItems);
        assertEquals(meal2, maxItem); 
        assertEquals(6, maxItem.getCounter());
    }
    
    @Test
    public void testMaxFoodItemEmptyList() {
        ArrayList<FoodItem> emptyList = new ArrayList<>();
        FoodItem maxItem = sorter.maxFoodItem(emptyList);
        assertNull(maxItem);
    }
    
    @Test
    public void testMaxFoodItemSingleItem() {
        ArrayList<FoodItem> singleItemList = new ArrayList<>();
        singleItemList.add(dish1);
        FoodItem maxItem = sorter.maxFoodItem(singleItemList);
        assertEquals(dish1, maxItem);
    }
    
    @Test
    public void testSortNormal() {
        ArrayList<FoodItem> sortedItems = sorter.sort(foodItems);
        
        // Check size
        assertEquals(foodItems.size(), sortedItems.size());
        
        // Check order (descending by counter)
        assertEquals(meal2, sortedItems.get(0));
        assertEquals(dish1, sortedItems.get(1));
        assertEquals(dish3, sortedItems.get(2));
        assertEquals(dish2, sortedItems.get(3));
        assertEquals(meal1, sortedItems.get(4));
    }
    
    @Test
    public void testSortEmptyList() {
        ArrayList<FoodItem> emptyList = new ArrayList<>();
        ArrayList<FoodItem> sortedItems = sorter.sort(emptyList);
        assertTrue(sortedItems.isEmpty());
    }
    
    @Test
    public void testSortSingleItem() {
        ArrayList<FoodItem> singleItemList = new ArrayList<>();
        singleItemList.add(dish1);
        ArrayList<FoodItem> sortedItems = sorter.sort(singleItemList);
        assertEquals(1, sortedItems.size());
        assertEquals(dish1, sortedItems.get(0));
    }
    
    
    @Test
    public void testOriginalListUnchanged() {
        ArrayList<FoodItem> originalList = new ArrayList<>(foodItems);
        sorter.sort(foodItems);
        
        // Verify original list is unchanged
        assertEquals(originalList.size(), foodItems.size());
        for(int i = 0; i < originalList.size(); i++) {
            assertEquals(originalList.get(i), foodItems.get(i));
        }
    }
} 