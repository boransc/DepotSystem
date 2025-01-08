import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Tester {
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

        Worker worker = new Worker(customerQueue, parcelMap);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Process next customer");
            System.out.println("2. Add new customer");
            System.out.println("3. Add new parcel");
            System.out.println("4. Display customer queue");
            System.out.println("5. Display parcels");
            System.out.println("6. Exit");
            System.out.println("7. Calculate Fee");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> worker.processNextCustomer();
                case 2 -> addNewCustomer(scanner, customerQueue);
                case 3 -> addNewParcel(scanner, parcelMap);
                case 4 -> customerQueue.displayQueue();
                case 5 -> parcelMap.displayParcels();
                case 6 -> {
                    System.out.println("Exiting program.");
                    running = false;
                }
                case 7 -> {
                    System.out.print("Enter the Parcel ID to calculate the fee: ");
                    String parcelId = scanner.nextLine();
                
                    if (parcelMap.containsParcel(parcelId)) {
                        Parcel parcel = parcelMap.getParcel(parcelId); // Retrieve the parcel
                        double fee = worker.calculateFee(parcel);     // Calculate the fee
                        System.out.printf("The fee for parcel %s is: %.2f%n", parcelId, fee);
                    } else {
                        System.out.println("Parcel ID not found.");
                    }
                }
                

                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
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

    private static void addNewCustomer(Scanner scanner, QueueOfCustomers queue) {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter customer ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Queue Number: ");
        String queueNo = scanner.nextLine();
        
        Customer customer = new Customer(firstName, lastName, id, queueNo);
        queue.addCustomer(customer);
        System.out.println("Customer added successfully.");
    }

    private static void addNewParcel(Scanner scanner, ParcelMap parcelMap) {
        System.out.print("Enter parcel ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter days in depot: ");
        int daysInDepot = scanner.nextInt();
        System.out.print("Enter weight: ");
        double weight = scanner.nextDouble();
        System.out.print("Enter length: ");
        int length = scanner.nextInt();
        System.out.print("Enter width: ");
        int width = scanner.nextInt();
        System.out.print("Enter height: ");
        int height = scanner.nextInt();
        scanner.nextLine(); //newline
        System.out.print("Enter status: ");
        boolean status = scanner.nextBoolean();
        scanner.nextLine(); //newline


        Parcel parcel = new Parcel(id, daysInDepot, weight, length, width, height,status);
        parcelMap.addParcel(parcel);
        System.out.println("Parcel added successfully.");
    }
}


    

