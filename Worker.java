public class Worker {
    private QueueOfCustomers customerQueue;
    private ParcelMap parcelMap;
    private Log log;

    public Worker(QueueOfCustomers customerQueue, ParcelMap parcelMap) {
        this.customerQueue = customerQueue;
        this.parcelMap = parcelMap;
        this.log = Log.getInstance();
    }

    public void processParcel(String parcelId) {
        boolean success = parcelMap.markParcelAsProcessed(parcelId);
        if (!success) {
            Log.getInstance().addEntry("Parcel with ID " + parcelId + " marked as processed.");
        } else {
            Log.getInstance().addEntry("Failed to find parcel with ID " + parcelId + ".");
        }
    }

    public void processNextCustomer() {
        if (customerQueue.isEmpty()) {
            System.out.println("No customers in the queue.");
            return;
        }

        Customer customer = customerQueue.removeCustomer();
        String parcelId = customer.getId();

        if (parcelMap.containsParcel(parcelId)) {
            Parcel parcel = parcelMap.removeParcel(parcelId);
            double fee = calculateFee(parcel);
            log.addEntry("Customer " + customer.getFullName() + " collected parcel " + parcelId + " (Fee: £" + fee + ")");
            System.out.println("Processed " + customer.getFullName() + " | Parcel: " + parcel + " | Fee: £" + fee);
            processParcel(parcelId);
        } else {
            log.addEntry("Customer " + customer.getFullName() + " attempted to collect non-existent parcel " + parcelId);
            System.out.println("Parcel " + parcelId + " not found for " + customer.getFullName());
        }
    }

    public double calculateFee(Parcel parcel) {
        int baseFee = 2;
        double fee = baseFee + (parcel.getVolume() * 0.005 + parcel.getWeight() * 0.5 + parcel.getDaysInDepot() * 0.75 );
        if (parcel.getId().equals("D025")) {
            return fee * 0.75;
        } else if (parcel.getId().equals("D050")) {
            return fee * 0.5;
        } else if (parcel.getId().equals("D025")) {
            return fee * 0;
        } else {
            return fee;
        }
    }
}
