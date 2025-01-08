public class Customer {
    private String firstName;
    private String lastName;
    private String parcelid;
    private String queueNo; 

    public Customer(String firstName, String lastName, String parcelid, String queueNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.parcelid = parcelid;
        this.queueNo = queueNo;
    }

    public String getFullName() { return firstName + " " + lastName; }

    public String getId() { return parcelid; }

    public String getQueueNumber() { return queueNo; }
}
