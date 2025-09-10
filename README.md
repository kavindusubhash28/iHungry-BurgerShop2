# 🍔 iHungry Burger Shop – Assignment 2

This is my **Assignment 2** project for the iHungry Burger Shop application.  
It extends **Assignment 1** and implements the **complete Burger Shop Management System** with all required features.

## 📌 Features (Assignment 2 Scope)
- **Main Menu** with all 7 functional options:
  1. **Place Order**
     - Auto-generated Order IDs (`B001`, `B002`, ...).
     - Customer ID validation (phone number).
     - Reuse existing customer name if ID exists, or add new.
     - Enter burger quantity (>0).
     - Calculate and display **total bill**.
     - Confirm before saving order.
     - Option to place multiple orders.
  2. **Search Best Customer**
     - Find customers with highest total purchase.
     - Display results sorted in descending order.
  3. **Search Order**
     - Search order by **Order ID**.
     - Display details in a formatted **table** (OrderID, CustomerID, Name, Qty, Value, Status).
     - Invalid ID handling with retry option.
  4. **Search Customer**
     - Search customer by **Customer ID**.
     - Display all their orders in a **table format**.
     - If not found, display proper message and retry option.
  5. **View Orders**
     - View orders under 3 categories:
       - Delivered
       - Preparing
       - Cancelled
     - Display results in **tabular format**.
     - Option to stay or return to main menu.
  6. **Update Order Details**
     - Search order by Order ID.
     - Update **Quantity** (with validation).
     - Update **Status** (Preparing, Delivered, Cancelled).
     - Only possible if status = **PREPARING**.
     - Display updated values after modification.
  7. **Exit**
     - Graceful exit message.

## 🛠️ Technologies
- Java
- Console-based (CLI)
- Arrays for data storage (Orders, Customers, Status)
- Methods for modular programming

## 🎯 Learning Outcomes
- Building a **complete CLI application** with multiple features.
- Implementing **searching, updating, and sorting logic** in Java.
- Designing **user-friendly console menus** with formatted output.
- Practicing **structured programming concepts**.

