import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParcelMap {
    private Map<String, Parcel> parcels;

    public ParcelMap() { this.parcels = new HashMap<>(); }

    public void addParcel(Parcel parcel) { parcels.put(parcel.getId(), parcel); }

    public Parcel removeParcel(String id) { return parcels.remove(id); }

    public Parcel getParcel(String id) { return parcels.get(id); }

    public boolean containsParcel(String id) { return parcels.containsKey(id); }

    public boolean markParcelAsProcessed(String id) {
        Parcel parcel = parcels.get(id);
        if (parcel != null) {
            parcel.markAsProcessed(); // Mark the parcel as processed
            return true;
        }
        return false; // Parcel with the given ID was not found
    }

    public List<Parcel> getUnprocessedParcels() {
        return parcels.values().stream()
                .filter(parcel -> !parcel.isProcessed())
                .collect(Collectors.toList());
    }

    public List<Parcel> getProcessedParcels() {
        return parcels.values().stream()
                .filter(Parcel::isProcessed)
                .collect(Collectors.toList());
    }

    public void displayParcels() {
        if (parcels.isEmpty()) {
            System.out.println("No parcels available.");
        } else {
            System.out.println("Current Parcels:");
            for (Parcel parcel : parcels.values()) {
                System.out.println("- " + parcel);
            }
        }
    }

    public List<String> getParcelData() {
    List<String> data = new ArrayList<>();
    for (Parcel parcel : parcels.values()) {
        data.add(String.format("%s,%d,%.2f,%d,%d,%d,%b",
                parcel.getId(),
                parcel.getDaysInDepot(),
                parcel.getWeight(),
                parcel.getLength(),
                parcel.getWidth(),
                parcel.getHeight(),
                parcel.isProcessed()));
    }
    return data;
}
}
