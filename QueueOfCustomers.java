import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueOfCustomers {
    private Queue<Customer> customerQueue;

    public QueueOfCustomers() { this.customerQueue = new LinkedList<>(); }

        public List<Customer> getQueue() {
        return new ArrayList<>(customerQueue);
    }

    public Queue<Customer> getCustomerQueue() { return customerQueue; }

    public void addCustomer(Customer customer) { customerQueue.add(customer); }

    public Customer removeCustomer() { return customerQueue.poll(); }

    public boolean isEmpty() { return customerQueue.isEmpty(); }

    public int size() { return customerQueue.size(); }

    public Object[][] getCustomerData() {
        Object[][] data = new Object[customerQueue.size()][3];
        int index = 0;
        for (Customer customer : customerQueue) {
            data[index][0] = customer.getQueueNumber();
            data[index][1] = customer.getFullName();
            data[index][2] = customer.getId(); // Parcel ID
            index++;
        }
        return data;
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