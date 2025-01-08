import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Manager {
    public static void main(String[] args) {
        QueueOfCustomers customerQueue = new QueueOfCustomers();
        ParcelMap parcelMap = new ParcelMap();

        try {
            loadCustomers("customers.csv", customerQueue);
            loadParcels("parcels.csv", parcelMap);
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
            return;
        }
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

    public static void saveParcelsToFile(String fileName, ParcelMap parcelMap) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : parcelMap.getParcelData()) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}