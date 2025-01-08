public class Parcel {
    private String parcelid;
    private int daysInDepot;
    private double weight;
    private int length;
    private int width;
    private int height;
    private boolean processed;

    public Parcel(String parcelid, int daysInDepot, double weight, int length, int width, int height, boolean processed) {
        this.parcelid = parcelid;
        this.daysInDepot = daysInDepot;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.processed = processed;
    }

    public String getId() { return parcelid; }

    public int getDaysInDepot() { return daysInDepot; }

    public double getWeight() { return weight; }

    public int getLength() {  return length; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public int getVolume() { return length * width * height; }

    public boolean isProcessed() { return processed; }

    public void markAsProcessed() { this.processed = true; }

    @Override
    public String toString() {
        return String.format("Parcel ID: %s, Days in Depot: %d, Weight: %.2f, Dimensions: %d x %d x %d",
                parcelid, daysInDepot, weight, length, width, height);
    }
}
