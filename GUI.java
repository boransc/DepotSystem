import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI extends JFrame {
    private QueueOfCustomers customerQueue;
    private ParcelMap parcelMap;
    private Log log;

    // Panels
    private JPanel parcelPanel;
    private JPanel customerQueuePanel;
    private JPanel logPanel;
    private JList<String> parcelList; // JList to display parcels
    private DefaultListModel<String> parcelListModel; // Default model for the list

    public GUI(QueueOfCustomers customerQueue, ParcelMap parcelMap, Log log) {
        this.customerQueue = customerQueue;
        this.parcelMap = parcelMap;
        this.log = log;

        // Set up the JFrame
        setTitle("Depot Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize panels
        initBasicPanels();
        populateParcelsPanel();

        // Add panels to the frame
        add(parcelPanel, BorderLayout.WEST);
        add(customerQueuePanel, BorderLayout.CENTER);
        add(logPanel, BorderLayout.EAST);

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
        parcelPanel.setBorder(BorderFactory.createTitledBorder("Parcels to be Processed"));
        customerQueuePanel.setBorder(BorderFactory.createTitledBorder("Customer Queue"));
        logPanel.setBorder(BorderFactory.createTitledBorder("Log Report"));

        parcelPanel.setPreferredSize(new Dimension(400, 700));
        logPanel.setPreferredSize(new Dimension(400, 700));
    }

    private void populateParcelsPanel() {
        // Get unprocessed parcels from the ParcelMap
        List<Parcel> unprocessedParcels = parcelMap.getUnprocessedParcels();

        // Create a list model to hold parcel information
        parcelListModel = new DefaultListModel<>();
        for (Parcel parcel : unprocessedParcels) {
            // Add formatted parcel info to the list model
            parcelListModel.addElement(parcel.getId() + " - " + parcel.getDaysInDepot() + " days in depot");
        }

        // Create a JList to display the parcels
        parcelList = new JList<>(parcelListModel);
        parcelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        parcelList.setVisibleRowCount(10);

        // Add the JList to the parcelPanel inside a scroll pane
        parcelPanel.add(new JScrollPane(parcelList), BorderLayout.CENTER);
    }
}
