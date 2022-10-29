package Answer4;

import java.util.concurrent.Semaphore;

public class ShopController {

    // Local variables holding the arguments as given to constructor
    private Semaphore semShop;
    private int totalSeats;

    public ShopController(Semaphore semShop, int totalSeats) {
        this.semShop = semShop;
        this.totalSeats = totalSeats;
    }

    public synchronized boolean getNextCustomer() {
        // Return variable
        boolean rIsACustomerWaiting;

        // Check if there is a customer waiting
        int waitingCustomers = getWaitingCustomers();

        // Check if at least 1 customer is waiting
        if (waitingCustomers > 0) {
            rIsACustomerWaiting = true;
        } else {
            rIsACustomerWaiting = false;
        }
        return rIsACustomerWaiting;
    }

    public synchronized int getWaitingCustomers() {
        int rWaitingCustomers = semShop.getQueueLength() + (1 - semShop.availablePermits());
        return rWaitingCustomers;
    }

    public synchronized int getAvailableSeats() {
        int waitingCustomers = getWaitingCustomers();
        int rAvailableSeats = totalSeats - waitingCustomers;
        return rAvailableSeats;
    }
}
