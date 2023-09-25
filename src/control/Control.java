package control;

import Algth.Method;
import data.Fruit;
import data.Order;
import view.Menu;

import java.util.ArrayList;
import java.util.Hashtable;

public class Control extends Menu<String> {
    ArrayList<Fruit> lf = new ArrayList<>();

    Hashtable<String, ArrayList<Order>> ht = new Hashtable<>();


    //--------------------------------------------------------
    static String[] menu = {"Create Fruit", "View orders", "Shopping (for buyer)", "EXIT (0)"};

    public Control() {
        super("\n----------!!Control System!!----------", menu);

    }

    @Override
    public void execute(int n) {
//        lf.add(new Fruit("1","Tao", 10000, 10, "Siu"));
//        lf.add(new Fruit("2","Oi", 15000, 20, "pờ"));
//        lf.add(new Fruit("3","Nho", 30000, 15, "mak"));
//        lf.add(new Fruit("4","Cam", 20000, 5, "két"));
        switch (n) {
            case 1 -> createFruit();
            case 2 -> Ordersview();
            case 3 -> shopping();
        }
    }

    private void Ordersview() {
        Method methodInstance = new Method(ht); // Create an instance of Method
        methodInstance.viewOrder();
    }


    private void createFruit() {
        Method methodInstance = new Method(lf);
        methodInstance.createFruit();
    }


    private void shopping() {
        Method methodInstance = new Method(lf,ht);
        methodInstance.shopping();
    }
}
