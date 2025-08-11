package fr.cs.groupS.myFoodora.cli;

import fr.cs.groupS.myFoodora.*;
import java.util.*;

/**
 * MyFoodoraCLI: bootstraps with sample data on first run.
 */
public class MyFoodoraCLI {
    private MyFoodora myFoodora;
    private User currentUser;
    private Scanner scanner;
    private boolean running;
    private Map<String, CLICommand> commands;

    public MyFoodoraCLI() {
        // NOTE: serviceFee = 0.05, markupPercentage = 0.10, deliveryCost = 0.05
        this.myFoodora = new MyFoodora(0.05, 0.10, 0.05);

        // Immediately pre-load “sample data” (managers, restaurants, customers, couriers, meals).
        loadSampleData();

        this.scanner = new Scanner(System.in);
        this.running = true;
        this.commands = new HashMap<>();
        initializeCommands();
    }

    /**
     * Create exactly:
     *  1) Two Manager accounts (CEO and deputy),
     *  2) Five Restaurant accounts (each with four FullMeals),
     *  3) Two Courier accounts,
     *  4) Seven Customer accounts,
     *  5) Send a “special offer” alert to any customer who opted in.
     */
    private void loadSampleData() {
        // ── 1) MANAGERS ──────────────────────────────────────────────────
        Manager ceo = new Manager("Alice", "CEO", "MGR001", "alice.ceo", "ceopass");
        Manager deputy = new Manager("Bob", "Deputy", "MGR002", "bob.deputy", "deputypass");
        myFoodora.addUser(ceo);
        myFoodora.addUser(deputy);

        // ── 2) RESTAURANTS ───────────────────────────────────────────────
        String[] restaurantNames = {
            "PastaPalace", "SushiStation", "BurgerBarn", "CurryCorner", "TacoTown"
        };
        for (int i = 0; i < restaurantNames.length; i++) {
            String baseName  = restaurantNames[i];
            String mgrID     = "RST00" + (i + 1);
            String username  = baseName.toLowerCase();
            String password  = "pass" + (i + 1);
            Coordinate restLoc = new Coordinate(1.0f + i, 2.0f + i);

            Restaurant r = new Restaurant(
                baseName,
                baseName + "Owner",
                mgrID,
                username,
                password,
                restLoc,
                new Menu(),
                new ArrayList<Meal>()
            );
            myFoodora.addUser(r);

            Menu menu = r.getMenu();
            for (int m = 1; m <= 4; m++) {
                Starter  s = new Starter("Starter" + i + "_" + m, 5.0 + m, (m % 2 == 0 ? "vegetarian" : "standard"));
                MainDish md = new MainDish("Main" + i + "_" + m, 10.0 + 2*m, (m % 2 == 1 ? "standard" : "glutenFree"));
                Dessert  d = new Dessert("Dessert" + i + "_" + m, 3.0 + m, "standard");

                menu.addDish(s);
                menu.addDish(md);
                menu.addDish(d);

                FullMeal fm = new FullMeal("FullMeal" + i + "_" + m);
                try {
                    fm.addDish(s);
                    fm.addDish(md);
                    fm.addDish(d);
                } catch (NoPlaceInMealException ex) {
                    // should not happen here
                }
                menu.addMeal(fm);
            }
        }

        // ── 3) COURIERS ───────────────────────────────────────────────────
        Courier c1 = new Courier("sushant", "patil", "COU001", "sushant.patil", "courier1pass",
                                 new Coordinate(3.5f, 4.0f), "06 12 34 56 78");
        Courier c2 = new Courier("ayman", "merzouk", "COU002", "ayman.merzouk", "courier2pass",
                                 new Coordinate(4.5f, 5.0f), "07 98 76 54 32");
        myFoodora.addUser(c1);
        myFoodora.addUser(c2);

        // ── 4) CUSTOMERS ──────────────────────────────────────────────────
        for (int i = 1; i <= 7; i++) {
            String cid       = "CUST00" + i;
            String uname     = "customer" + i;
            String pwd       = "custpass" + i;
            Coordinate loc   = new Coordinate(5.0f + i, 5.5f + i);
            String email     = "cust" + i + "@example.com";
            String phone     = "07 98 76 54 3" + i;

            Customer cust = new Customer(
                "Customer" + i,
                "Surname" + i,
                cid,
                uname,
                pwd,
                loc,
                email,
                phone
            );
            myFoodora.addUser(cust);

            // If i is odd, opt in for special-offer notifications
            if ((i % 2) == 1) {
                cust.toggleNotificationPreference(true);
            }
        }

        // ── 5) SEND SPECIAL‐OFFER ALERTS ──────────────────────────────────
        System.out.println("=== Sending special‐offer alerts to opted‐in customers ===");
        for (User u : myFoodora.getUsers()) {
            if (u instanceof Customer) {
                Customer cust = (Customer) u;
                if (cust.isReceivingNotifications()) {
                    System.out.println("Alert sent to " + cust.getUsername()
                        + " (“" + cust.getName() + " " + cust.getSurname() + "”), email: "
                        + cust.getEmail() + ")");
                }
            }
        }
        System.out.println("=== Done bootstrapping sample data ===\n");
    }

    private void initializeCommands() {
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("registerRestaurant", new RegisterRestaurantCommand());
        commands.put("registerCustomer", new RegisterCustomerCommand());
        commands.put("registerCourier", new RegisterCourierCommand());
        commands.put("addDishRestaurantMenu", new AddDishRestaurantMenuCommand());
        commands.put("createMeal", new CreateMealCommand());
        commands.put("addDish2Meal", new AddDish2MealCommand());
        commands.put("showMeal", new ShowMealCommand());
        commands.put("saveMeal", new SaveMealCommand());
        commands.put("setSpecialOffer", new SetSpecialOfferCommand());
        commands.put("removeFromSpecialOffer", new RemoveFromSpecialOfferCommand());
        commands.put("createOrder", new CreateOrderCommand());
        commands.put("addItem2Order", new AddItem2OrderCommand());
        commands.put("endOrder", new EndOrderCommand());
        commands.put("onDuty", new OnDutyCommand());
        commands.put("offDuty", new OffDutyCommand());
        commands.put("findDeliverer", new FindDelivererCommand());
        commands.put("setDeliveryPolicy", new SetDeliveryPolicyCommand());
        commands.put("setProfitPolicy", new SetProfitPolicyCommand());
        commands.put("associateCard", new AssociateCardCommand());
        commands.put("showCourierDeliveries", new ShowCourierDeliveriesCommand());
        commands.put("showRestaurantTop", new ShowRestaurantTopCommand());
        commands.put("showCustomers", new ShowCustomersCommand());
        commands.put("showMenuItem", new ShowMenuItemCommand());
        commands.put("showTotalProfit", new ShowTotalProfitCommand());
        commands.put("runTest", new RunTestCommand());
        // Register the two new commands:
        commands.put("showRestaurants", new ShowRestaurantsCommand());
        commands.put("showCouriers", new ShowCouriersCommand());
        commands.put("help", new HelpCommand());
    }

    public void start() {
        System.out.println("Welcome to MyFoodora CLI!");
        System.out.println("Type 'help' for a list of available commands.\n");

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+");
            String commandName = parts[0];

            CLICommand command = commands.get(commandName);
            if (command != null) {
                try {
                    command.execute(this, Arrays.copyOfRange(parts, 1, parts.length));
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("Unknown command. Type 'help' for available commands.");
            }
        }
    }

    public void stop() {
        running = false;
    }

    public MyFoodora getMyFoodora() {
        return myFoodora;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public Map<String, CLICommand> getCommands() {
        return commands;
    }

    public static void main(String[] args) {
        MyFoodoraCLI cli = new MyFoodoraCLI();
        cli.start();
    }
}
