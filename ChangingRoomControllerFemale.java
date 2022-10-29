package Answer4;

import java.util.concurrent.Semaphore;

public class ChangingRoomControllerFemale {

    // Local variables holding the arguments as given to constructor
    private Semaphore semChangingRoomFemale;
    private int totalSeats;

    public ChangingRoomControllerFemale(Semaphore semChangingRoomFemale, int totalSeats) {
        this.semChangingRoomFemale = semChangingRoomFemale;
        this.totalSeats = totalSeats;
    }

    public synchronized boolean getNextCustomer() {
        // Return variable
        boolean rIsACustomersWaiting;

        // Check if there is a customer waiting
        int waitingCustomers = getWaitingCustomers();

        // Check if at least 1 customer is waiting
        if (waitingCustomers > 0) {
            semChangingRoomFemale.release();
            int availableSpotsA = getAvailableSeats();
            System.out.println
                    (String.format
                            ("Female Changing Room: Accepted a lady to try products. Total available rooms: [%1$d]",
                                    availableSpotsA));

            rIsACustomersWaiting = true;
        } else {
            rIsACustomersWaiting = false;
        }
        return rIsACustomersWaiting;
    }

    public synchronized int getWaitingCustomers() {
        int rWaitingCustomers = semChangingRoomFemale.getQueueLength() + (1 - semChangingRoomFemale.availablePermits());
        return rWaitingCustomers;
    }

    public synchronized int getAvailableSeats() {
        int waitingCustomers = getWaitingCustomers();
        int rAvailableSpots = totalSeats - waitingCustomers;
        return rAvailableSpots - 1;
    }
}
