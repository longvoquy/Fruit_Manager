package Algth;

import control.Validation;
import data.Fruit;
import data.Order;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Method {
    private Hashtable<String, ArrayList<Order>> ht;
    private ArrayList<Fruit> lf;

    Scanner scanner = new Scanner(System.in);
    Validation validation = new Validation(scanner);


    public Method(Hashtable<String, ArrayList<Order>> ht) {
        this.ht = ht;

    }

    public Method(ArrayList<Fruit> lf) {
        this.lf = lf;
    }

    public Method(ArrayList<Fruit> lf, Hashtable<String, ArrayList<Order>> ht) {
        this.ht = ht;
        this.lf = lf;
    }

    //allow user create fruit
    public void createFruit() {
        //loop until user don't want to create fruit
        while (true) {
            System.out.print("Enter fruit id: ");
            String fruitId = validation.checkInputString();
            //check id exist
            if (!Validation.checkIdExist(lf, fruitId)) {
                System.err.println("Id exist");
                return;
            }
            System.out.print("Enter fruit name: ");
            String fruitName = validation.checkInputString();
            System.out.print("Enter price: ");
            double price = validation.checkInputDouble();
            System.out.print("Enter quantity: ");
            int quantity = validation.checkInputInt();
            System.out.print("Enter origin: ");
            String origin = validation.checkInputString();
            lf.add(new Fruit(fruitId, fruitName, price, quantity, origin));
            System.out.println("Added Successfully");
            System.out.print("Do you want to continue (Y/N)?");
            //check user want to continue or not
            if (!validation.checkInputYN()) {
                displayListFruit(lf);
                return;
            }
        }
    }

    //allow user show view order
    public void viewOrder() {
        for (String name : ht.keySet()) {
            System.out.println("Customer: " + name);
            ArrayList<Order> lo = ht.get(name);
            displayListOrder(lo);
        }
    }

    //allow user buy items
    public void shopping() {
        //check list empty user can't buy
        if (lf.isEmpty()) {
            System.err.println("No have item.");
            return;
        }
        //loop until user don't want to buy continue
        ArrayList<Order> lo = new ArrayList<>();

        do {
            displayListFruit(lf);
            System.out.print("Enter item: ");
            int item = validation.checkInputIntLimit(1, lf.size());
            Fruit fruit = getFruitByItem(lf, item);
            System.out.print("Enter quantity: ");
            int quantity = validation.checkInputIntLimit(1, fruit.getQuantity());
            fruit.setQuantity(fruit.getQuantity() - quantity);
            //check item exist or not
            if (!Validation.checkItemExist(lo, fruit.getFruitId())) {
                updateOrder(lo, fruit.getFruitId(), quantity);
            } else {
                lo.add(new Order(fruit.getFruitId(), fruit.getFruitName(),
                        quantity, fruit.getPrice()));
            }
            System.out.println("Do you want to continue (Y/N):");
        } while (validation.checkInputYN());
        displayListOrder(lo);
        System.out.print("Enter name: ");
        String name = validation.checkInputString();
        ht.put(name, lo);
        System.err.println("Add successfull");
    }

    //display list fruit in shop
    public void displayListFruit(ArrayList<Fruit> lf) {
        int countItem = 1;
        System.out.printf("%-10s%-20s%-20s%-15s\n", "Item", "Fruit name", "Origin", "Price");
        for (Fruit fruit : lf) {
            //check shop have item or not
            if (fruit.getQuantity() != 0) {
                System.out.printf("%-10d%-20s%-20s%-15.0f$\n", countItem++,
                        fruit.getFruitName(), fruit.getOrigin(), fruit.getPrice());
            }
        }
    }

    //get fruint user want to by
    public Fruit getFruitByItem(ArrayList<Fruit> lf, int item) {
        int countItem = 1;
        for (Fruit fruit : lf) {
            //check shop have item or not
            if (fruit.getQuantity() != 0) {
                countItem++;
            }
            if (countItem - 1 == item) {
                return fruit;
            }
        }
        return null;
    }

    //display list order
    public void displayListOrder(ArrayList<Order> lo) {
        double total = 0;
        System.out.printf("%15s%15s%15s%15s\n", "Product", "Quantity", "Price", "Amount");
        for (Order order : lo) {
            System.out.printf("%15s%15d%15.0f$%15.0f$\n", order.getFruitName(),
                    order.getQuantity(), order.getPrice(),
                    order.getPrice() * order.getQuantity());
            total += order.getPrice() * order.getQuantity();
        }
        System.out.println("Total: " + total + "$");
    }

    //if order exist then update order
    public void updateOrder(ArrayList<Order> lo, String id, int quantity) {
        for (Order order : lo) {
            if (order.getFruitId().equalsIgnoreCase(id)) {
                order.setQuantity(order.getQuantity() + quantity);
                return;
            }
        }
    }
}
