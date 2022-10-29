package Answer4;

import java.util.concurrent.Semaphore;

public class ChangingRoomControllerMale {

    // Local variables holding the arguments as given to constructor
    private Semaphore semChangingRoomMale;
    private int totalSeats;

    public ChangingRoomControllerMale(Semaphore semChangingRoomMale, int totalSeats) {
        this.semChangingRoomMale = semChangingRoomMale;
        this.totalSeats = totalSeats;
    }

    public synchronized boolean getNextCustomer() {
        // Return variable
        boolean rIsACustomersWaiting;

        // Check if there is a customer waiting
        int waitingCustomers = getWaitingCustomers();

        // Check if at least 1 customer is waiting
        if (waitingCustomers > 0) {
            semChangingRoomMale.release();
            int availableSpotsA = getAvailableSeats();
            System.out.println
                    (String.format
                            ("Male Changing Room: Accepted a gentleman to try products. Total available rooms: [%1$d]",
                                    availableSpotsA));

            rIsACustomersWaiting = true;
        } else {
            rIsACustomersWaiting = false;
        }
        return rIsACustomersWaiting;
    }

    public synchronized int getWaitingCustomers() {
        int rWaitingCustomers = semChangingRoomMale.getQueueLength() + (1 - semChangingRoomMale.availablePermits());
        return rWaitingCustomers;
    }

    public synchronized int getAvailableSeats() {
        int waitingCustomers = getWaitingCustomers();
        int rAvailableSpots = totalSeats - waitingCustomers;
        return rAvailableSpots - 1;
    }
}
