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
    private JPanel controlsPanel; // Controls panel for new buttons

    // JTable for customer queue
    private JTable customerQueueTable;

    // Text area for log display
    private JTextArea logTextArea;

    // Buttons
    private JButton processCustomerButton;
    private JButton addCustomerButton;
    private JButton addParcelButton;

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

        // Buttons for actions like finding a parcel and calculating the fee
        addFindParcelButton();
        addCalculateFeeButton();

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
        controlsPanel = new JPanel(); // Initialize controlsPanel

        // Set up default layout for each panel
        parcelPanel.setLayout(new BorderLayout());
        customerQueuePanel.setLayout(new BorderLayout());
        logPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new FlowLayout());
        controlsPanel.setLayout(new FlowLayout()); // Set layout for controlsPanel

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

        // Initialize add customer button
        addCustomerButton = new JButton("Add New Customer");
        addCustomerButton.addActionListener(e -> openAddCustomerDialog());
        buttonPanel.add(addCustomerButton);

        // Initialize add parcel button
        addParcelButton = new JButton("Add New Parcel");
        addParcelButton.addActionListener(e -> openAddParcelDialog());
        buttonPanel.add(addParcelButton);

        // Add controlsPanel to buttonPanel or wherever it's appropriate in your layout
        buttonPanel.add(controlsPanel);  // Make sure controlsPanel is added to the main layout
    }

    private void populateParcelPanel() {
        var parcels = parcelMap.getUnprocessedParcels();

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

        parcelPanel.removeAll();
        JTable parcelTable = new JTable(rowData, columnNames);
        JScrollPane scrollPane = new JScrollPane(parcelTable);
        parcelPanel.add(scrollPane, BorderLayout.CENTER);

        parcelPanel.revalidate();
        parcelPanel.repaint();
    }

    private void populateCustomerQueueTable() {
        Queue<Customer> queue = customerQueue.getCustomerQueue();

        String[] columnNames = {"Queue #", "Customer Name", "Parcel ID"};
        Object[][] rowData = new Object[queue.size()][3];

        int index = 0;
        for (Customer customer : queue) {
            rowData[index][0] = customer.getQueueNumber();
            rowData[index][1] = customer.getFullName();
            rowData[index][2] = customer.getId();
            index++;
        }

        customerQueuePanel.removeAll();
        customerQueueTable = new JTable(rowData, columnNames);
        JScrollPane scrollPane = new JScrollPane(customerQueueTable);
        customerQueuePanel.add(scrollPane, BorderLayout.CENTER);

        customerQueuePanel.revalidate();
        customerQueuePanel.repaint();
    }

    private void processNextCustomer() {
        if (!customerQueue.isEmpty()) {
            worker.processNextCustomer();

            // Re-populate the panels after processing
            populateParcelPanel();
            populateCustomerQueueTable();

            logTextArea.setText(log.getLog());
        } else {
            log.addEntry("No customers in the queue to process.");
            logTextArea.setText(log.getLog());
        }
    }

    // Open the dialog to add a new customer
    private void openAddCustomerDialog() {
        JDialog customerDialog = new JDialog(this, "Add New Customer", true);
        customerDialog.setLayout(new GridLayout(5, 2));

        // Customer form fields
        JTextField nameField = new JTextField();
        JTextField parcelIdField = new JTextField();

        customerDialog.add(new JLabel("Full Name:"));
        customerDialog.add(nameField);
        customerDialog.add(new JLabel("Parcel ID:"));
        customerDialog.add(parcelIdField);

        JButton addButton = new JButton("Add Customer");
        addButton.addActionListener(e -> {
            String fullName = nameField.getText();
            String parcelId = parcelIdField.getText();

            // The QueueNumber is based on the current size of the customer queue
            int queueNumber = customerQueue.size() + 1;

            // Create new customer with the input details
            Customer newCustomer = new Customer("", fullName, parcelId, String.valueOf(queueNumber));
            customerQueue.addCustomer(newCustomer);

            // Add a log entry for the new customer
            log.addEntry("New customer added: " + fullName + " with parcel " + parcelId);

            // Update GUI to reflect the newly added customer
            populateCustomerQueueTable();
            customerDialog.dispose();
        });

        customerDialog.add(addButton);
        customerDialog.setSize(300, 200);
        customerDialog.setLocationRelativeTo(this);
        customerDialog.setVisible(true);
    }

    // Open the dialog to add a new parcel
    private void openAddParcelDialog() {
        JDialog parcelDialog = new JDialog(this, "Add New Parcel", true);
        parcelDialog.setLayout(new GridLayout(8, 2));

        // Parcel form fields
        JTextField idField = new JTextField();
        JTextField daysInDepotField = new JTextField();
        JTextField weightField = new JTextField();
        JTextField lengthField = new JTextField();
        JTextField widthField = new JTextField();
        JTextField heightField = new JTextField();

        parcelDialog.add(new JLabel("Parcel ID:"));
        parcelDialog.add(idField);
        parcelDialog.add(new JLabel("Days in Depot:"));
        parcelDialog.add(daysInDepotField);
        parcelDialog.add(new JLabel("Weight:"));
        parcelDialog.add(weightField);
        parcelDialog.add(new JLabel("Length:"));
        parcelDialog.add(lengthField);
        parcelDialog.add(new JLabel("Width:"));
        parcelDialog.add(widthField);
        parcelDialog.add(new JLabel("Height:"));
        parcelDialog.add(heightField);

        JButton addButton = new JButton("Add Parcel");
        addButton.addActionListener(e -> {
            String id = idField.getText();
            int daysInDepot = Integer.parseInt(daysInDepotField.getText());
            double weight = Double.parseDouble(weightField.getText());
            int length = Integer.parseInt(lengthField.getText());
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());

            Parcel newParcel = new Parcel(id, daysInDepot, weight, length, width, height, false);
            parcelMap.addParcel(newParcel);

            log.addEntry("New parcel added: " + id);

            // Update GUI
            populateParcelPanel();
            parcelDialog.dispose();
        });

        parcelDialog.add(addButton);
        parcelDialog.setSize(300, 300);
        parcelDialog.setLocationRelativeTo(this);
        parcelDialog.setVisible(true);
    }

    private void addFindParcelButton() {
        JButton findParcelButton = new JButton("Find Parcel by ID");
        findParcelButton.addActionListener(e -> {
            String parcelId = JOptionPane.showInputDialog(this, "Enter Parcel ID:");
            if (parcelId != null && !parcelId.isEmpty()) {
                Parcel foundParcel = parcelMap.getParcel(parcelId);
                if (foundParcel != null) {
                    JOptionPane.showMessageDialog(this, "Parcel found: " + foundParcel);
                } else {
                    JOptionPane.showMessageDialog(this, "Parcel not found.");
                }
            }
        });

        controlsPanel.add(findParcelButton);
    }

    private void addCalculateFeeButton() {
        JButton calculateFeeButton = new JButton("Calculate Fee for Parcel");
        calculateFeeButton.addActionListener(e -> {
            String parcelId = JOptionPane.showInputDialog(this, "Enter Parcel ID:");
            if (parcelId != null && !parcelId.isEmpty()) {
                Parcel parcel = parcelMap.getParcel(parcelId);
                if (parcel != null) {
                    double fee = worker.calculateFee(parcel);
                    JOptionPane.showMessageDialog(this, "Fee for parcel " + parcelId + ": " + fee);
                } else {
                    JOptionPane.showMessageDialog(this, "Parcel not found.");
                }
            }
        });

        controlsPanel.add(calculateFeeButton);
    }
}
