package Answer4;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class ShopMain {
    // Constant holding the store's capacity
    private static final int TOTAL_CAPACITY = 40;

    // Constant holding the number of available spots in the register
    private static final int TOTAL_QUEUE_SPOTS = 10;

    // Constant holding the number of available rooms in the changing rooms
    private static final int TOTAL_CHANGING_ROOMS = 5;

    // Constants defining the minimum and maximum amount of time a customer might arrive.
    private static final int MIN_TIME_FOR_CUSTOMER_ARRIVAL = 2 * 1000;
    private static final int MAX_TIME_FOR_CUSTOMER_ARRIVAL = 5 * 1000;

    // Initiating gender for customer
    private static boolean gender = true;

    public static void main(String[] args) throws InterruptedException {

        Semaphore semRegisterQueue = new Semaphore(1, true);
        Semaphore semChangingRoomMale = new Semaphore(1, true);
        Semaphore semChangingRoomFemale = new Semaphore(1, true);
        Semaphore semShop = new Semaphore(1, true);

        // Instantiating the register, changing room and shop controllers that will handle the waiting customers
        QueueRegisterController queueRegisterController = new QueueRegisterController(semRegisterQueue, TOTAL_QUEUE_SPOTS, semShop);
        ChangingRoomControllerMale changingRoomControllerMale = new ChangingRoomControllerMale(semChangingRoomMale, TOTAL_CHANGING_ROOMS);
        ChangingRoomControllerFemale changingRoomControllerFemale = new ChangingRoomControllerFemale(semChangingRoomFemale, TOTAL_CHANGING_ROOMS);
        ShopController shopController = new ShopController(semShop, TOTAL_CAPACITY);


        // Instantiation of register, changing room and shop threads.
        Shop shop = new Shop(shopController, semChangingRoomMale, semChangingRoomFemale);
        ChangingRoomMale changingRoomMale = new ChangingRoomMale(changingRoomControllerMale, semRegisterQueue);
        ChangingRoomFemale changingRoomFemale = new ChangingRoomFemale(changingRoomControllerFemale, semRegisterQueue);
        Register register = new Register(queueRegisterController);
        // Start of threads
        shop.start();
        changingRoomMale.start();
        changingRoomFemale.start();
        register.start();

        // Infinite loop, simulating the infinite coming of customers to the store.
        while (true) {
            // Get a random time to wait to simulate different time intervals each customer arrive
            int randomTimeUntilCustomerArrives =
                    ThreadLocalRandom.current().nextInt(MIN_TIME_FOR_CUSTOMER_ARRIVAL, MAX_TIME_FOR_CUSTOMER_ARRIVAL + 1);

            // Define a new Customer thread
            Customer customer = new Customer(shopController, semShop);

            // Start the new Customer thread
            customer.start();

            // Wait for the random time calculated until next iteration/next customer
            try {
                Thread.sleep(randomTimeUntilCustomerArrives);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
