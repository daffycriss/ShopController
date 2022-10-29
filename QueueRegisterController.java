package Answer4;

import java.util.concurrent.Semaphore;

public class QueueRegisterController {

    // Local variables holding the arguments as given to constructor
    private Semaphore semRegisterQueue;
    private int totalSeats;
    Semaphore semShop;

    public QueueRegisterController(Semaphore semRegisterQueue, int totalSeats, Semaphore semShop) {
        this.semRegisterQueue = semRegisterQueue;
        this.totalSeats = totalSeats;
        this.semShop = semShop;
    }

    public synchronized boolean getNextCustomer() {
        // Return variable
        boolean rIsACustomerWaiting;

        // Check if there is a customer waiting
        int waitingCustomers = getWaitingCustomers();

        // Check if at least 1 customer is waiting
        if (waitingCustomers > 0) {
            // Release register and store simultaneously, as customer leaves store as long as they pay.
            semRegisterQueue.release();
            semShop.release();
            int availableSpotsA = getAvailableSeats();
            System.out.println
                    (String.format
                            ("Register: Accepted 1 customer for checkout, 1 spot is now released. Total available spots: [%1$d]",
                                    availableSpotsA));

            rIsACustomerWaiting = true;
        } else {
            rIsACustomerWaiting = false;
        }
        return rIsACustomerWaiting;
    }

    public synchronized int getWaitingCustomers() {
        int rWaitingCustomers = semRegisterQueue.getQueueLength() + (1 - semRegisterQueue.availablePermits());
        return rWaitingCustomers;
    }

    public synchronized int getAvailableSeats() {
        int waitingCustomers = getWaitingCustomers();
        int rAvailableSpots = totalSeats - waitingCustomers;
        return rAvailableSpots;
    }
}
