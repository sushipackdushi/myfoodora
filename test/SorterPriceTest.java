package fr.cs.groupS.myFoodora;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class SorterPriceTest {
    private SorterPrice sorter;
    private ArrayList<FoodItem> foodItems;
    private Starter dish1;
    private MainDish dish2;
    private Dessert dish3;
    private Meal meal1;
    private Meal meal2;
    
    @Before
    public void setUp() {
        sorter = new SorterPrice();
        foodItems = new ArrayList<>();
        
        dish1 = new Starter("Salad", 8.0, "vegetarian");
        dish2 = new MainDish("Steak", 25.0, "standard");
        dish3 = new Dessert("Ice Cream", 6.0, "standard");
        
        meal1 = new FullMeal("Full Meal 1", dish2, dish1, dish3); 
        meal2 = new HalfMeal("Half Meal 1", dish2, dish3);
        
        foodItems.add(dish1);
        foodItems.add(dish2);
        foodItems.add(dish3);
        foodItems.add(meal1);
        foodItems.add(meal2);
    }
    
    @Test
    public void testMaxFoodItemNormal() {
        FoodItem maxItem = sorter.maxFoodItem(foodItems);
        assertEquals(meal1, maxItem); 
        assertEquals(37.05, maxItem.getPrice(), 0.001);
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
        assertEquals(8.0, maxItem.getPrice(), 0.001);
    }
    
    @Test
    public void testSortNormal() {
        ArrayList<FoodItem> sortedItems = sorter.sort(foodItems);
        
        assertEquals(foodItems.size(), sortedItems.size());
        
        assertEquals(meal1, sortedItems.get(0));  
        assertEquals(meal2, sortedItems.get(1)); 
        assertEquals(dish2, sortedItems.get(2)); 
        assertEquals(dish1, sortedItems.get(3));  
        assertEquals(dish3, sortedItems.get(4)); 
        
        for(int i = 0; i < sortedItems.size() - 1; i++) {
            assertTrue(sortedItems.get(i).getPrice() >= sortedItems.get(i + 1).getPrice());
        }
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
    public void testSortWithEqualPrices() {
        FoodItem equalDish1 = new Starter("Soup", 10.0, "vegetarian");
        FoodItem equalDish2 = new Dessert("Cake", 10.0, "vegetarian");
        
        ArrayList<FoodItem> equalPriceList = new ArrayList<>();
        equalPriceList.add(equalDish1);
        equalPriceList.add(equalDish2);
        
        ArrayList<FoodItem> sortedItems = sorter.sort(equalPriceList);
        assertEquals(2, sortedItems.size());
        assertEquals(10.0, sortedItems.get(0).getPrice(), 0.001);
        assertEquals(10.0, sortedItems.get(1).getPrice(), 0.001);
    }
    
    @Test
    public void testOriginalListUnchanged() {
        ArrayList<FoodItem> originalList = new ArrayList<>(foodItems);
        sorter.sort(foodItems);
        
        assertEquals(originalList.size(), foodItems.size());
        for(int i = 0; i < originalList.size(); i++) {
            assertEquals(originalList.get(i), foodItems.get(i));
            assertEquals(originalList.get(i).getPrice(), foodItems.get(i).getPrice(), 0.001);
        }
    }
}
