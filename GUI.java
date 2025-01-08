import javax.swing.*;
import java.awt.*;
import java.util.Queue;

public class GUI extends JFrame {
    private QueueOfCustomers customerQueue;
    private ParcelMap parcelMap;
    private Log log;
    private Worker worker;

    // Panels
    private JPanel parcelPanel;
    private JPanel customerQueuePanel;
    private JPanel logPanel;
    private JPanel buttonPanel;

    // JTable for customer queue
    private JTable customerQueueTable;

    // Text area for log display
    private JTextArea logTextArea;

    // Button to process next customer
    private JButton processCustomerButton;

    public GUI(QueueOfCustomers customerQueue, ParcelMap parcelMap, Log log) {
        this.customerQueue = customerQueue;
        this.parcelMap = parcelMap;
        this.log = log;
        this.worker = new Worker(customerQueue, parcelMap);

        // Set up the JFrame
        setTitle("Depot Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize panels
        initBasicPanels();

        // Add panels to the frame
        add(parcelPanel, BorderLayout.WEST);
        add(customerQueuePanel, BorderLayout.CENTER);
        add(logPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        // Make the frame visible
        setVisible(true);

        // Populate the panels initially
        populateParcelPanel();
        populateCustomerQueueTable();
    }

    private void initBasicPanels() {
        // Initialize the panels with placeholders
        parcelPanel = new JPanel();
        customerQueuePanel = new JPanel();
        logPanel = new JPanel();
        buttonPanel = new JPanel();

        // Set up default layout for each panel
        parcelPanel.setLayout(new BorderLayout());
        customerQueuePanel.setLayout(new BorderLayout());
        logPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new FlowLayout());

        // Set titles
        parcelPanel.setBorder(BorderFactory.createTitledBorder("Parcels to be Processed"));
        customerQueuePanel.setBorder(BorderFactory.createTitledBorder("Customer Queue"));
        logPanel.setBorder(BorderFactory.createTitledBorder("Log Report"));

        parcelPanel.setPreferredSize(new Dimension(400, 700));
        logPanel.setPreferredSize(new Dimension(400, 700));

        // Initialize log text area
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logTextArea);
        logPanel.add(logScrollPane, BorderLayout.CENTER);

        // Initialize process customer button
        processCustomerButton = new JButton("Process Next Customer");
        processCustomerButton.addActionListener(e -> processNextCustomer());
        buttonPanel.add(processCustomerButton);
    }

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

        // Remove the old table and add a new one
        parcelPanel.removeAll();  // Remove old content from the panel
        JTable parcelTable = new JTable(rowData, columnNames);
        JScrollPane scrollPane = new JScrollPane(parcelTable);
        parcelPanel.add(scrollPane, BorderLayout.CENTER);

        // Revalidate and repaint the panel
        parcelPanel.revalidate();
        parcelPanel.repaint();
    }

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

        // Remove the old table and add a new one
        customerQueuePanel.removeAll();  // Remove old content from the panel
        customerQueueTable = new JTable(rowData, columnNames);
        JScrollPane scrollPane = new JScrollPane(customerQueueTable);
        customerQueuePanel.add(scrollPane, BorderLayout.CENTER);

        // Revalidate and repaint the panel
        customerQueuePanel.revalidate();
        customerQueuePanel.repaint();
    }

    public void processNextCustomer() {
        if (!customerQueue.isEmpty()) {
            // Process the next customer using the worker
            worker.processNextCustomer();

            // Re-populate the panels after processing
            populateParcelPanel();  // Update parcel panel (remove processed parcel)
            populateCustomerQueueTable();  // Update customer queue table (remove processed customer)

            // Update the log panel
            logTextArea.setText(log.getLog());
        } else {
            log.addEntry("No customers in the queue to process.");
            logTextArea.setText(log.getLog());
        }
    }
}
