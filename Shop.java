package Answer4;

import java.util.concurrent.Semaphore;

public class Shop extends Thread {
    // Time constants
    private static final int FIND_PRODUCTS = 2 * 1000;

    // Shop object as given by the constructor
    private ShopController shopController;
    private Semaphore semChangingRoomMale;
    private Semaphore semChangingRoomFemale;

    public Shop(ShopController shopController, Semaphore semChangingRoomMale, Semaphore semChangingRoomFemale) {
        this.shopController = shopController;
        this.semChangingRoomMale = semChangingRoomMale;
        this.semChangingRoomFemale = semChangingRoomFemale;
    }

    @Override
    public void run() {

        boolean gender = true;

        // Infinite loop simulating the process customer searches the products he needs in the store
        // or waiting for a customer to arrive
        while (true) {
            boolean isThereANextCustomer = shopController.getNextCustomer();
            if (isThereANextCustomer) {
                try {
                    //Simulate finding products, lasts 2 seconds
                    Thread.sleep(FIND_PRODUCTS);
                    String gendercustomer;
                    if (gender) {
                        gendercustomer = "gentleman";
                        semChangingRoomMale.acquire();
                    } else {
                        gendercustomer = "lady";
                        semChangingRoomFemale.acquire();
                    }
                    gender = !gender;
                    System.out.println
                            (String.format
                                    ("Shop: A " + gendercustomer + " customer selected products, goes now to changing rooms."));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
