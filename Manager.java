import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Manager {
    public static void main(String[] args) {
        QueueOfCustomers customerQueue = new QueueOfCustomers();
        ParcelMap parcelMap = new ParcelMap();
        Log log = Log.getInstance();

        try {
            loadCustomers("customers.csv", customerQueue);
            loadParcels("parcels.csv", parcelMap);
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
            return;
        }

        // Initialize GUI
        SwingUtilities.invokeLater(() -> {
            new GUI(customerQueue, parcelMap, log);
        });
    }

    private static void loadCustomers(String fileName, QueueOfCustomers queue) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                queue.addCustomer(new Customer(parts[0], parts[1], parts[2], parts[3]));
            }
        }
    }

    private static void loadParcels(String fileName, ParcelMap parcelMap) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0];
                int daysInDepot = Integer.parseInt(parts[1]);
                double weight = Double.parseDouble(parts[2]);
                int length = Integer.parseInt(parts[3]);
                int width = Integer.parseInt(parts[4]);
                int height = Integer.parseInt(parts[5]);
                boolean status = Boolean.parseBoolean(parts[6]);
                parcelMap.addParcel(new Parcel(id, daysInDepot, weight, length, width, height, status));
            }
        }
    }
}
