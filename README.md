# myFoodora - Food Delivery System

**CentraleSupélec — Object Oriented Software Engineering Project 2025**  
**Hand-out:** May 9, 2025  
**Due:** June 4, 2025  
**Programming Language:** Java  

---

## 📖 Overview
The goal of this project is to develop a software solution, called **myFoodora**, whose functionality is similar to modern **Food Delivery Systems** such as [Foodora](http://www.foodora.fr) or [Deliveroo](http://www.deliveroo.fr).

**myFoodora** is a platform giving customers access to a set of restaurants from which they can order food via:  
- **À-la-carte** selection  
- **Pre-compiled meals**  

**Core features:**
- Customers pay a total fee = **order price** (set by restaurant) + **service-fee** (set by manager)  
- **Markup percentage** retained by myFoodora from orders  
- Payout to both restaurants and couriers  
- Restaurant-managed menus and special offers  
- Courier allocation for deliveries  
- Fidelity plans for customer discounts and special menus  

**Project parts:**
1. **myFoodora core** — Java infrastructure (see Section 2)
2. **myFoodora user-interface** — Command-Line User Interface (see Section 4)

---

## 📦 System Description & Requirements

### 1. Menus and Meals
- **Menus**:
  - Three categories: *starters*, *main dishes*, *desserts*
  - Each item: price, type (*standard*, *vegetarian*), optional *gluten-free*
  - À-la-carte ordering allowed
- **Meals**:
  - Types: *standard*, *vegetarian*, *gluten-free*
  - Half-meal = 2 items (starter+main OR main+dessert)
  - Full-meal = starter + main + dessert
  - Default price = 5% discount on sum of item prices
  - One *meal-of-the-week* special (default 10% discount)

### 2. User Types
- **Restaurants** — manage menus, meals, discounts
- **Customers** — place orders, manage fidelity plans, receive offers
- **Couriers** — set duty status, update location, accept deliveries
- **Managers** — manage all users, fees, markup, delivery policies, statistics

### 3. Core System Responsibilities
- Store all users, orders, profit data  
- Allocate couriers via **delivery policies**  
- Notify customers of special offers  
- Compute income & profit: profitForOneOrder = orderPrice · markupPercentage + serviceFee − deliveryCost

- 
---

## ⚙️ Advanced Functionalities

### Policies
- **Target Profit**:
- Adjust *delivery cost*, *service fee*, or *markup* to meet targets
- **Delivery**:
- *Fastest delivery* (shortest distance)
- *Fair-occupation delivery* (fewest deliveries so far)
- **Order Sorting**:
- Most/least ordered half-meals  
- Most/least ordered à-la-carte items  

### Fidelity Cards
1. **Basic** — access to special offers  
2. **Point** — earn points (10€ = 1 point, 100 points = 10% discount)  
3. **Lottery** — chance to win free meals daily  

---

## 🖥 Command-Line User Interface (CLUI)

Commands are of the form: command-name <arg1> <arg2> ... <argN>


**Examples:**
setup 4 3 5
registerRestaurant "TourDargent" "45.1,66.2" "12345678"


**Main Commands:**
- `login <username> <password>`  
- `logout <>`  
- `registerRestaurant <name> <address> <username> <password>`  
- `registerCustomer <firstName> <lastName> <username> <address> <password>`  
- `registerCourier <firstName> <lastName> <username> <position> <password>`  
- `addDishRestauarantMenu <dishName> <dishCategory> <foodCategory> <unitPrice>`  
- `createMeal <mealName>`  
- `addDish2Meal <dishName> <mealName>`  
- `setSpecialOffer <mealName>` / `removeFromSpecialOffer <mealName>`  
- `createOrder <restaurantName> <orderName>`  
- `addItem2Order <orderName> <itemName>`  
- `endOrder <orderName> <date>`  
- `setDeliveryPolicy <delPolicyName>`  
- `setProfitPolicy <ProfitPolicyName>`  
- `associateCard <userName> <cardType>`  
- `showCourierDeliveries <>`  
- `showRestaurantTop <>`  
- `showTotalProfit <>` or `<startDate> <endDate>`  
- `runTest <testScenario-file>`  
- `help <>`  


