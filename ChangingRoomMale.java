package Answer4;

import java.util.concurrent.Semaphore;

public class ChangingRoomMale extends Thread {
    // Time constants
    private static final int FIVE_SECOND_MS = 10 * 1000;
    private static final int ONE_SECOND_MS = 3 * 1000;

    // Changing room controller object as given by the constructor
    private ChangingRoomControllerMale changingRoomControllerMale;
    private Semaphore semRegisterQueue;

    // Constructor needs its controller and the semaphore for the register
    public ChangingRoomMale(ChangingRoomControllerMale changingRoomControllerMale, Semaphore semRegisterQueue) {
        this.changingRoomControllerMale = changingRoomControllerMale;
        this.semRegisterQueue = semRegisterQueue;
    }

    @Override
    public void run() {
        // Infinite loop simulating the process of the customer tries products in the changing room
        // or waiting for a customer to arrive
        while (true) {
            // Asks the changing room controller if there is a customer available to enter in a room
            // and accepts the customer if there is one.
            boolean isThereANextCustomer = changingRoomControllerMale.getNextCustomer();
            if (isThereANextCustomer) {
                try {
                    //Simulates product trying, will last for 5 seconds
                    Thread.sleep(FIVE_SECOND_MS);
                    System.out.println
                            (String.format
                                    ("Male Changing Room: A gentleman completed trying products, goes now to checkout."));
                    semRegisterQueue.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                // No customer waits; changing room waits 1 second and then will check again if another customer has arrived
                System.out.println
                        (String.format
                                ("Male Changing Room: No customers right now, wait for 1 second."));
                try {
                    Thread.sleep(ONE_SECOND_MS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
