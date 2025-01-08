import java.util.LinkedList;
import java.util.Queue;

public class QueueOfCustomers {
    private Queue<Customer> customerQueue;

    public QueueOfCustomers() {
        this.customerQueue = new LinkedList<>();
    }

    public void addCustomer(Customer customer) {
        customerQueue.add(customer);
    }

    public Customer removeCustomer() {
        return customerQueue.poll();
    }

    public boolean isEmpty() {
        return customerQueue.isEmpty();
    }

    public int size() {
        return customerQueue.size();
    }

    public void displayQueue() {
        if (customerQueue.isEmpty()) {
            System.out.println("The customer queue is empty.");
        } else {
            System.out.println("Current Customer Queue:");
            for (Customer customer : customerQueue) {
                System.out.println(customer.getQueueNumber() + " - " + customer.getFullName() + " (Parcel ID: " + customer.getId() + ")");
            }
        }
    }
}
