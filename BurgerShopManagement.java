import java.util.Scanner;

public class BurgerShopManagement {
    final static double BURGERPRICE = 500.0;
    public static final int PREPARING = 0;
    public static final int DELIVERED = 1;
    public static final int CANCEL = 2;

    static String[] orderIDs = new String[100];
    static String[] customerIDs = new String[100];
    static String[] customerNames = new String[100];
    static int[] burgerQty = new int[100];
    static int[] orderStatus = new int[100];
    static int orderCount = 0;

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n-------------------------------");
            System.out.println("     iHungry Burger    ");
            System.out.println("-------------------------------");
            System.out.println("[1] Place Order        [2] Search Best Customer");
            System.out.println("[3] Search Order       [4] Search Customer");
            System.out.println("[5] View Orders        [6] Update Order Details");
            System.out.println("[7] Exit");
            System.out.print("\nEnter an option to continue > ");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1: placeOrder(); break;
                case 2: searchBestCustomer(); break;
                case 3: searchOrder(); break;
                case 4: searchCustomer(); break;
                case 5: viewOrders(); break;
                case 6: updateOrders(); break;
                case 7: exit(); break;
                default: System.out.println("Invalid choice! Try again.");
            }
        }
    }

    public static String generateOrderID() {
        return String.format("B%03d", orderCount + 1);
    }

    public static void placeOrder() {
        while (true) {
            String orderID = generateOrderID();
            System.out.println("\nOrder ID: " + orderID);

            String customerID;
            while (true) {
                System.out.print("Enter Customer ID (phone number): ");
                customerID = input.nextLine();
                if (customerID.matches("0\\d{9}")) break;
                System.out.println("Invalid phone number! Must start with 0 and have 10 digits.");
            }

            String customerName = null;
            boolean found = false;
            for (int i = 0; i < orderCount; i++) {
                if (customerIDs[i].equals(customerID)) {
                    customerName = customerNames[i];
                    found = true;
                    break;
                }
            }
            if (found) {
                System.out.println("Existing Customer: " + customerName);
            } else {
                System.out.print("Enter Customer Name: ");
                customerName = input.nextLine();
            }

            int qty;
            while (true) {
                System.out.print("Enter Burger Quantity: ");
                qty = input.nextInt();
                input.nextLine();
                if (qty > 0) break;
                System.out.println("Quantity must be greater than 0.");
            }

            double total = qty * BURGERPRICE;
            System.out.println("Total Bill: Rs. " + total);

            System.out.print("Confirm Order? (Y/N): ");
            String confirm = input.nextLine().toUpperCase();

            if (confirm.equals("Y")) {
                orderIDs[orderCount] = orderID;
                customerIDs[orderCount] = customerID;
                customerNames[orderCount] = customerName;
                burgerQty[orderCount] = qty;
                orderStatus[orderCount] = PREPARING;
                orderCount++;
                System.out.println(" Your order is enter to the system successfully...");
            } else {
                System.out.println("Order Cancelled!");
            }

            System.out.print("Do you want to place another order? (Y/N): ");
            String again = input.nextLine().toUpperCase();
            if (!again.equals("Y")) break;
        }
    }

    public static void searchBestCustomer() {
        if (orderCount == 0) {
            System.out.println("No orders available.");
            return;
        }

        String[] uniqueIDs = new String[orderCount];
        double[] totals = new double[orderCount];
        int uniqueCount = 0;

        for (int i = 0; i < orderCount; i++) {
            String cid = customerIDs[i];
            double bill = burgerQty[i] * BURGERPRICE;
            boolean exists = false;

            for (int j = 0; j < uniqueCount; j++) {
                if (uniqueIDs[j].equals(cid)) {
                    totals[j] += bill;
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                uniqueIDs[uniqueCount] = cid;
                totals[uniqueCount] = bill;
                uniqueCount++;
            }
        }

        for (int i = 0; i < uniqueCount - 1; i++) {
            for (int j = i + 1; j < uniqueCount; j++) {
                if (totals[j] > totals[i]) {
                    double tempTotal = totals[i]; totals[i] = totals[j]; totals[j] = tempTotal;
                    String tempID = uniqueIDs[i]; uniqueIDs[i] = uniqueIDs[j]; uniqueIDs[j] = tempID;
                }
            }
        }

        System.out.println("\nBest Customers (Descending by Total):");
        for (int i = 0; i < uniqueCount; i++) {
            System.out.println(uniqueIDs[i] + " - Total: Rs. " + totals[i]);
        }
    }

    public static void searchOrder() {
        if (orderCount == 0) {
            System.out.println("No orders available.");
            return;
        }

        while (true) {
            System.out.println("\n-------------------------------");
            System.out.println("       SEARCH ORDER DETAILS     ");
            System.out.println("-------------------------------");

            System.out.print("Enter Order ID: ");
            String id = input.nextLine();
            boolean found = false;

            for (int i = 0; i < orderCount; i++) {
                if (orderIDs[i].equals(id)) {
                    System.out.println("\n--------------------------------------------------------------");
                    System.out.printf("%-8s %-12s %-12s %-8s %-10s %-10s\n",
                            "OrderID", "CustomerID", "Name", "Qty", "OrderValue", "Status");
                    System.out.println("--------------------------------------------------------------");

                    double orderValue = burgerQty[i] * BURGERPRICE;
                    System.out.printf("%-8s %-12s %-12s %-8d %-10.2f %-10s\n",
                            orderIDs[i], customerIDs[i], customerNames[i], burgerQty[i], orderValue, statusText(orderStatus[i]));
                    System.out.println("--------------------------------------------------------------");

                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.print("Invalid Order ID. Do you want to enter again? (Y/N): ");
                String again = input.nextLine().toUpperCase();
                if (!again.equals("Y")) break;
            } else {
                System.out.print("Do you want to search another order details? (Y/N): ");
                String again = input.nextLine().toUpperCase();
                if (!again.equals("Y")) break;
            }
        }
    }

    public static void searchCustomer() {
        if (orderCount == 0) {
            System.out.println("No orders available.");
            return;
        }

        while (true) {
            System.out.println("\n-------------------------------");
            System.out.println("      SEARCH CUSTOMER DETAILS   ");
            System.out.println("-------------------------------");

            System.out.print("Enter Customer ID: ");
            String cid = input.nextLine();
            boolean found = false;
            String customerName = "";

            for (int i = 0; i < orderCount; i++) {
                if (customerIDs[i].equals(cid)) {
                    found = true;
                    customerName = customerNames[i];
                    break;
                }
            }

            if (!found) {
                System.out.println("\nThis customer ID is not added yet....");
                System.out.print("Do you want to search another customer details (Y/N): ");
                String again = input.nextLine().toUpperCase();
                if (!again.equals("Y")) break;
            } else {
                System.out.println("\nCustomer ID - " + cid);
                System.out.println("Name        - " + customerName);
                System.out.println("\nCustomer Order Details");

                System.out.println("------------------------------------------------");
                System.out.printf("%-10s %-15s %-12s\n", "Order_ID", "Order_Quantity", "Total_Value");
                System.out.println("------------------------------------------------");

                for (int i = 0; i < orderCount; i++) {
                    if (customerIDs[i].equals(cid)) {
                        double orderValue = burgerQty[i] * BURGERPRICE;
                        System.out.printf("%-10s %-15d %-12.2f\n", orderIDs[i], burgerQty[i], orderValue);
                    }
                }

                System.out.println("------------------------------------------------");

                System.out.print("Do you want to search another customer details (Y/N): ");
                String again = input.nextLine().toUpperCase();
                if (!again.equals("Y")) break;
            }
        }
    }


    public static void viewOrders() {
        if (orderCount == 0) { System.out.println("No orders available."); return; }

        while (true) {
            System.out.println("\n-------------------------------");
            System.out.println("          VIEW ORDERS           ");
            System.out.println("-------------------------------");
            System.out.println("[1] Delivered Orders");
            System.out.println("[2] Preparing Orders");
            System.out.println("[3] Cancelled Orders");
            System.out.print("Enter option > ");
            int opt = input.nextInt();
            input.nextLine();

            int status;
            if (opt == 1) status = DELIVERED;
            else if (opt == 2) status = PREPARING;
            else if (opt == 3) status = CANCEL;
            else { System.out.println("Invalid option!"); return; }

            System.out.println("\n----------------------------------------------------");
            System.out.printf("%-8s %-12s %-12s %-6s %-10s\n", "OrderID", "CustomerID", "Name", "Qty", "Value");
            System.out.println("----------------------------------------------------");

            boolean found = false;
            for (int i = 0; i < orderCount; i++) {
                if (orderStatus[i] == status) {
                    double orderValue = burgerQty[i] * BURGERPRICE;
                    System.out.printf("%-8s %-12s %-12s %-6d %-10.2f\n",
                            orderIDs[i], customerIDs[i], customerNames[i], burgerQty[i], orderValue);
                    found = true;
                }
            }
            if (!found) System.out.println("No orders found with this status.");
            System.out.println("----------------------------------------------------");

            System.out.print("Do you want to stay here? (Y/N): ");
            String stay = input.nextLine().toUpperCase();
            if (!stay.equals("Y")) break;
        }
    }

    public static void updateOrders() {
        if (orderCount == 0) {
            System.out.println("No orders available.");
            return;
        }

        while (true) {
            System.out.println("\n-------------------------------");
            System.out.println("       UPDATE ORDER DETAILS     ");
            System.out.println("-------------------------------");

            System.out.print("Enter Order ID: ");
            String id = input.nextLine();
            int index = -1;
            for (int i = 0; i < orderCount; i++) {
                if (orderIDs[i].equals(id)) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                System.out.print("Invalid Order ID. Do you want to try again? (Y/N): ");
                String again = input.nextLine().toUpperCase();
                if (!again.equals("Y")) break;
                else continue;
            }

            if (orderStatus[index] != PREPARING) {
                System.out.println("Cannot update. Order already " + statusText(orderStatus[index]));
                break;
            }

            double orderValue = burgerQty[index] * BURGERPRICE;
            System.out.println("\nOrderID     : " + orderIDs[index]);
            System.out.println("CustomerID  : " + customerIDs[index]);
            System.out.println("Name        : " + customerNames[index]);
            System.out.println("Quantity    : " + burgerQty[index]);
            System.out.println("OrderValue  : " + orderValue);
            System.out.println("OrderStatus : " + statusText(orderStatus[index]));

            System.out.println("\nWhat do you want to update?");
            System.out.println("(1) Quantity");
            System.out.println("(2) Status");
            System.out.print("Enter your option > ");
            int opt = input.nextInt();
            input.nextLine();

            if (opt == 1) {
                System.out.println("\nQuantity Update");
                System.out.println("================");

                System.out.print("Enter your quantity update value: ");
                int newQty = input.nextInt();
                input.nextLine();

                if (newQty > 0) {
                    burgerQty[index] = newQty;
                    double newValue = newQty * BURGERPRICE;
                    System.out.println("\n Order quantity updated successfully!");
                    System.out.println("New Order Quantity = " + newQty);
                    System.out.println("New Order Value    = " + newValue);
                } else {
                    System.out.println(" Invalid quantity!");
                }
            } else if (opt == 2) {
                System.out.println("\nStatus Update");
                System.out.println("==============");
                System.out.println("0. Preparing");
                System.out.println("1. Delivered");
                System.out.println("2. Cancelled");
                System.out.print("Choose status > ");
                int newStatus = input.nextInt();
                input.nextLine();

                if (newStatus >= 0 && newStatus <= 2) {
                    orderStatus[index] = newStatus;
                    System.out.println("\n Order status updated successfully!");
                    System.out.println("New Status = " + statusText(newStatus));
                } else {
                    System.out.println(" Invalid status!");
                }
            } else {
                System.out.println(" Invalid option!");
            }

            System.out.print("\nDo you want to update another order? (Y/N): ");
            String again = input.nextLine().toUpperCase();
            if (!again.equals("Y")) break;
        }
    }

    public static String statusText(int status) {
        switch (status) {
            case PREPARING: return "Preparing";
            case DELIVERED: return "Delivered";
            case CANCEL: return "Cancelled";
            default: return "Unknown";
        }
    }

    public static void exit() {
        System.out.println("\n You left the program...");
        System.exit(0);
    }
}
