package Answer4;

public class Register extends Thread {
    // Time constants
    private static final int FIVE_SECOND_MS = 5 * 1000;

    // Waiting room controller object as given by the constructor
    private QueueRegisterController queueRegisterController;

    public Register(QueueRegisterController queueRegisterController) {
        this.queueRegisterController = queueRegisterController;
    }

    @Override
    public void run() {
        //Infinite loop simulating the process of register checking out customers or waiting for a customer to arrive
        while (true) {
            // Asks the register controller if there is a customer available for check out (after changing room)
            // and accepts the customer if there is one.
            boolean isThereANextCustomer = queueRegisterController.getNextCustomer();
            if (isThereANextCustomer) {
                try {
                    // Simulate the check out and payment, will last for 5 seconds
                    Thread.sleep(FIVE_SECOND_MS);
                    System.out.println
                            (String.format
                                    ("Register: CheckOut completed, the customer has left, register ready for next customer."));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                // No customer waits; register will wait 1 second and then will check again if another customer has arrived
                System.out.println(String.format("Register: No customers right now, wait for 1 second."));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

