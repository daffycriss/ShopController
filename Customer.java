package Answer4;

import java.util.concurrent.Semaphore;

public class Customer extends Thread {

    private ShopController shopController;
    private Semaphore semShop;

    public Customer(ShopController shopController, Semaphore semShop) {
        this.shopController = shopController;
        this.semShop = semShop;
    }

    @Override
    public void run() {
        // Get the number of availability in the store by asking the store controller.
        int availableSeatsBefore = shopController.getAvailableSeats();

        // If the store has not reached capacity, the customer enters otherwise leaves.
        if (availableSeatsBefore > 0) {
            try {
                System.out.println
                        (String.format
                                ("Customer: A new customer arrived at the Shop, spots found free: [%1$d]. Free spots now [%2$d]",
                                        availableSeatsBefore,
                                        availableSeatsBefore - 1));
                // Customer gets a seat
                semShop.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            // The store is full and the customer leaves.
            System.out.println("Customer: Shop is full. Customer left.");
        }
    }
}
