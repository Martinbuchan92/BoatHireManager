import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class ReturnBoatForm extends JPanel implements ActionListener {
	private String buttonNames[] = { "Clear", "Update", "Close" };
	private JButton buttons[];
	private String reportFields[] = { "Boat Type:", "Daily Rate:", "Number of Days Hired:", "Subtotal:",
			"Fuel Usage Discount:", "Water Usage Award", "Water Usage Penalty", "Total:" };
	private JComboBox<String> hiresBox;
	private JPanel pnlSouth, pnlNorth;
	private JFrame rbf;
	private Date returnDate;
	private int selectedIndex;
	private String customerId;
	private int boatId;
	private Hire h;
	private Boat b;
	private JCheckBox waterRefill;
	private JCheckBox greenDiscount;
	private JPanel pnlCentre;
	private JLabel[] labels;
	private JTextField[] textFields;
	private double waterAward;
	private double waterPenalty;

	public ReturnBoatForm() {
		rbf = new JFrame();
		rbf.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		rbf.setTitle("Boat Hire Manager - Return Boat");
		rbf.setSize(750, 650);
		rbf.setVisible(true);
		rbf.setLocation(50, 50);

		JPanel cn = new JPanel();
		cn.setLayout(new BorderLayout());

		panelNorth();
		panelSouth();
		panelCentre();

		cn.add(pnlNorth, BorderLayout.NORTH);
		cn.add(pnlCentre, BorderLayout.CENTER);
		cn.add(pnlSouth, BorderLayout.SOUTH);
		rbf.setContentPane(cn);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == buttons[0]) {
			hiresBox.setSelectedIndex(0);
			greenDiscount.setSelected(false);
			waterRefill.setSelected(false);
		} else if (e.getSource() == buttons[1]) {
			returnHire();
			JOptionPane.showMessageDialog(rbf, "Boat Returned");
		} else if (e.getSource() == buttons[2]) {
			rbf.dispose();
		}

	}

	public JPanel panelCentre() {
		pnlCentre = new JPanel();

		int numFields = reportFields.length;
		labels = new JLabel[numFields];
		textFields = new JTextField[numFields];
		pnlCentre.setLayout(new GridLayout(numFields + 1, 2));

		for (int i = 0; i < reportFields.length; i++) {
			labels[i] = new JLabel(reportFields[i]);
			textFields[i] = new JTextField(30);
			textFields[i].setEditable(false);
			pnlCentre.add(labels[i]);
			pnlCentre.add(textFields[i]);
		}

		return pnlCentre;
	}

	public JPanel panelNorth() {
		pnlNorth = new JPanel();
		pnlNorth.setLayout(new GridLayout(5, 2));

		hiresBox = new JComboBox<String>();
		if (MenuBar.hires.size() <= 0) {
			hiresBox.addItem("Hires Array not loaded");
		} else {
			for (int i = 0; i < MenuBar.hires.size(); i++) {
				hiresBox.addItem(("" + MenuBar.hires.get(i).getHireId()));

			}
		}

		JLabel lblHire = new JLabel("Select Hire ID: ");
		pnlNorth.add(lblHire);
		pnlNorth.add(hiresBox);
		hiresBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 2) {
					invoice();
				}
			}
		});

		JLabel lblGreenDiscount = new JLabel("Green Discount");
		pnlNorth.add(lblGreenDiscount);
		greenDiscount = new JCheckBox();
		pnlNorth.add(greenDiscount);

		JLabel lblWaterRefill = new JLabel("Water Tank refilled?");
		pnlNorth.add(lblWaterRefill);
		waterRefill = new JCheckBox();
		pnlNorth.add(waterRefill);

		pnlNorth.setBorder(BorderFactory.createEtchedBorder());
		return pnlNorth;

	}

	public JPanel panelSouth() {
		pnlSouth = new JPanel();
		buttons = new JButton[buttonNames.length];
		for (int i = 0; i < buttonNames.length; i++) {
			buttons[i] = new JButton(buttonNames[i]);
			pnlSouth.add(buttons[i]);
			buttons[i].addActionListener(this);
		}
		buttons[0].setToolTipText("Clear fields");
		buttons[1].setToolTipText("Save new Hire");
		buttons[2].setToolTipText("Close Window");

		return pnlNorth;

	}

	public void invoice() {
		selectedIndex = hiresBox.getSelectedIndex();
		customerId = MenuBar.hires.get(selectedIndex).getCustomerID();
		boatId = MenuBar.hires.get(selectedIndex).getBoatId();

		h = MenuBar.hires.get(Hire.findHire(customerId, boatId));
		b = MenuBar.boats.get(Boat.findBoat(MenuBar.boats, boatId));

		returnDate = h.getReturnDate();
		int totalHireTime = (int) Hire.findDaysHired(h.getHireDate(), returnDate);
		try {
			h.setTotalDaysHired(totalHireTime);
		} catch (AssertionError e) {
			String msg = e.getMessage();
			msg += "\n";
			msg += this.toString();
			MainMethod.logger.log(Level.ALL, msg);
			JOptionPane.showMessageDialog(null, "Water usage error");
		}
		double totalCost = totalHireTime * h.getDailyRate();
		h.setTotalCost(totalCost);
		try {
			if (waterRefill.isSelected()) {
				waterPenalty = b.calcWaterUsageCharge(false, 1);
				waterAward = 0.0;

			} else {
				waterAward = b.calcWaterUsageCharge(true, 0);
				waterPenalty = 0.0;
			}
		} catch (AssertionError e) {
			String msg = e.getMessage();
			msg += "\n";
			msg += this.toString();
			MainMethod.logger.log(Level.ALL, msg);
			JOptionPane.showMessageDialog(null, "Water usage error");
		}
		try {
			if (greenDiscount.isSelected()) {
				if (b.getClass().getName().equals("Yacht")) {

					b.setFuelDiscount(0.10);

				} else if (b.getClass().getName().equals("HouseBoat")) {
					b.setFuelDiscount(0.05);
				} else if (b.getClass().getName().equals("MotorBoat")) {
					b.setFuelDiscount(0.15);
				}
			}
			if (!greenDiscount.isSelected()) {
				b.setFuelDiscount(0.0);
			}
		} catch (IllegalArgumentException e) {
			String msg = e.getMessage();
			msg += "\n";
			msg += this.toString();
			MainMethod.logger.log(Level.ALL, msg);
			JOptionPane.showMessageDialog(null, "Please enter a valid Discount");
		}

		String boatType = b.getClass().toString();
		Double dailyRate = h.getDailyRate();
		int daysHired = h.getTotalDaysHired();
		Double subTotal = h.getTotalCost();
		Double discountPercentage = b.getFuelDiscountRate();

		Double total = (subTotal - (subTotal * discountPercentage)) + waterPenalty - waterAward;

		textFields[0].setText(boatType);
		textFields[1].setText(dailyRate.toString());
		textFields[2].setText(String.valueOf(daysHired));
		textFields[3].setText(String.valueOf(subTotal));
		textFields[4].setText(String.valueOf(discountPercentage));
		textFields[5].setText(String.valueOf(waterAward));
		textFields[6].setText(String.valueOf(waterPenalty));
		textFields[7].setText(String.valueOf(total));

	}

	private void returnHire() {
		invoice();
		int returnedBoat = Hire.findHire(customerId, boatId);
		int returnHireID = h.getHireId();

		boolean onHire = true;
		double totalCost = h.getTotalCost();

		if (returnedBoat >= 0) {
			h.setOnHire(false);
			h.setTotalCost(totalCost);
			onHire = false;
		} else {
			JOptionPane.showMessageDialog(rbf, "Enter valid hire");
		}

		Connection conn;
		Statement statement;
		String url = "jdbc:mysql://localhost:3306/luxuriousboats";

		try {
			Class.forName("com.mysql.jdbc.Driver"); // Load MYSQL driver
			conn = DriverManager.getConnection(url, "root", "");
			statement = conn.createStatement();

			// Hire(hireID, customerID, boatID, dailyRate, onHire, hireDate,
			// returnDate, totalCost)
			String query = "UPDATE hire SET" + " onHire = " + onHire + ", returnDate = '" + returnDate
					+ "', totalCost = " + totalCost + " WHERE hireID = ? ;";

			System.out.println(query);
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, returnHireID);
			preparedStmt.executeUpdate();

			statement.close();
			conn.close();
		} catch (Exception ex) {
			System.err.println("Exception: " + ex.getMessage());
		}
	}
}
