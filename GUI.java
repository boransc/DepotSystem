import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    // Panels
    private JPanel parcelPanel;
    private JPanel customerQueuePanel;
    private JPanel logPanel;

    public GUI(QueueOfCustomers customerQueue, ParcelMap parcelMap, Log log) {


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
}
