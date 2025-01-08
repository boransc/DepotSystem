import javax.swing.*;
import java.awt.*;
import java.util.Queue;

public class GUI extends JFrame {
    private QueueOfCustomers customerQueue;
    private ParcelMap parcelMap;
    private Log log;

    // Panels
    private JPanel parcelPanel;
    private JPanel customerQueuePanel;
    private JPanel logPanel;

    // Table to display customer queue
    private JTable customerQueueTable;

    public GUI(QueueOfCustomers customerQueue, ParcelMap parcelMap, Log log) {
        this.customerQueue = customerQueue;
        this.parcelMap = parcelMap;
        this.log = log;

        // Set up the JFrame
        setTitle("Depot Management System");
        setSize(1800, 1200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize panels
        initBasicPanels();

        // Add panels to the frame
        add(parcelPanel, BorderLayout.WEST);
        add(customerQueuePanel, BorderLayout.CENTER);
        add(logPanel, BorderLayout.EAST);

        // Populate the panels
        populateParcelPanel();
        populateCustomerQueueTable();
        
        // Make the frame visible
        setVisible(true);
    }

    private void initBasicPanels() {
        // Initialize the panels with placeholders
        parcelPanel = new JPanel();
        customerQueuePanel = new JPanel();
        logPanel = new JPanel();

        // Set up default layout for each panel
        parcelPanel.setLayout(new BorderLayout());
        customerQueuePanel.setLayout(new BorderLayout());
        logPanel.setLayout(new BorderLayout());

        // Set titles
        parcelPanel.setBorder(BorderFactory.createTitledBorder("Parcels"));
        customerQueuePanel.setBorder(BorderFactory.createTitledBorder("Customer Queue"));
        logPanel.setBorder(BorderFactory.createTitledBorder("Log Report"));

        parcelPanel.setPreferredSize(new Dimension(600, 700));
        logPanel.setPreferredSize(new Dimension(600, 700));
    }

    // Populate parcel panel with the parcels
    private void populateParcelPanel() {
        // Get the list of unprocessed parcels from parcelMap
        var parcels = parcelMap.getUnprocessedParcels();
        
        // Initialize column names and data for JTable
        String[] columnNames = {"Parcel ID", "Days in Depot", "Weight", "Length", "Width", "Height", "Processed"};
        Object[][] rowData = new Object[parcels.size()][7];

        int index = 0;
        for (Parcel parcel : parcels) {
            rowData[index][0] = parcel.getId();
            rowData[index][1] = parcel.getDaysInDepot();
            rowData[index][2] = parcel.getWeight();
            rowData[index][3] = parcel.getLength();
            rowData[index][4] = parcel.getWidth();
            rowData[index][5] = parcel.getHeight();
            rowData[index][6] = parcel.isProcessed() ? "Yes" : "No";
            index++;
        }

        // Create and set up the table with the data
        JTable parcelTable = new JTable(rowData, columnNames);
        JScrollPane scrollPane = new JScrollPane(parcelTable);
        parcelPanel.add(scrollPane, BorderLayout.CENTER);
    }

    // Populate customer queue table
    private void populateCustomerQueueTable() {
        Queue<Customer> queue = customerQueue.getCustomerQueue();
        
        // Initialize column names and data for JTable
        String[] columnNames = {"Queue #", "Customer Name", "Parcel ID"};
        Object[][] rowData = new Object[queue.size()][3];

        int index = 0;
        for (Customer customer : queue) {
            rowData[index][0] = customer.getQueueNumber();
            rowData[index][1] = customer.getFullName();
            rowData[index][2] = customer.getId();
            index++;
        }

        // Create and set up the table with the data
        customerQueueTable = new JTable(rowData, columnNames);
        JScrollPane scrollPane = new JScrollPane(customerQueueTable);
        customerQueuePanel.add(scrollPane, BorderLayout.CENTER);
    }
}
