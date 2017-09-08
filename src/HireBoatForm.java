import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

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
public class HireBoatForm extends JPanel implements ActionListener {
	private JComboBox<String> boatBox;
	private String buttonNames[] = { "Clear", "Update", "Close" };
	private JButton buttons[];
	private JComboBox<String> customersBox;
	private JCheckBox greenCheck;
	private JFrame hbf;
	private JTextField hireDateInput;
	private JPanel pnlSouth, pnlCentre;
	private JTextField returnDateInput;
	private Date sqlHireDate;
	private Date sqlReturnDate;

	public HireBoatForm() {
		hbf = new JFrame();
		hbf.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		hbf.setTitle("Boat Hire Manager - Hire Boat");
		hbf.setSize(750, 650);
		hbf.setVisible(true);
		hbf.setLocation(50, 50);

		JPanel cn = new JPanel();
		cn.setLayout(new BorderLayout());

		panelCentre();
		panelSouth();

		cn.add(pnlCentre, BorderLayout.CENTER);
		cn.add(pnlSouth, BorderLayout.SOUTH);
		hbf.setContentPane(cn);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0]) {

			customersBox.setSelectedIndex(0);
			boatBox.setSelectedIndex(0);
			hireDateInput.setText("");
			returnDateInput.setText("");
			greenCheck.setSelected(false);

		} else if (e.getSource() == buttons[1]) {
			try {
				newHire();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(hbf, "Boat Hired");
		} else if (e.getSource() == buttons[2]) {
			hbf.dispose();
		}

	}

	public void newHire() throws ClassNotFoundException {
		int hireID = Hire.getNextHireID();
		String customerID = MenuBar.customer[customersBox.getSelectedIndex()].getCustId();
		int boatID = MenuBar.boats.get(boatBox.getSelectedIndex()).getBoatID();

		sqlHireDate = Date.valueOf((hireDateInput.getText()));
		sqlReturnDate = Date.valueOf((returnDateInput.getText()));

		double dailyRate = MenuBar.boats.get(boatBox.getSelectedIndex()).getRate();
		boolean onHire = true;
		Double totalCost = 0.0;

		MenuBar.hires
				.add(new Hire(hireID, customerID, boatID, sqlHireDate, sqlReturnDate, dailyRate, onHire, totalCost));

		Connection conn;
		Statement statement;
		String url = "jdbc:mysql://localhost:3306/luxuriousboats";

		try {
			Class.forName("com.mysql.jdbc.Driver"); // Load MYSQL driver
			conn = DriverManager.getConnection(url, "root", "");
			statement = conn.createStatement();

			// Hire(hireID, customerID, boatID, dailyRate, onHire, hireDate,
			// returnDate, totalCost)
			String query = "INSERT INTO hire VALUES(" + hireID + ", '" + customerID + "', " + boatID + ", " + dailyRate
					+ ", " + onHire + ", '" + sqlHireDate + "', '" + sqlReturnDate + "', " + totalCost + ");";
			System.out.println(query);
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.executeUpdate();

			statement.close();
			conn.close();
		} catch (Exception ex) {
			System.err.println("Exception: " + ex.getMessage());
		}
	}

	public JPanel panelCentre() {
		pnlCentre = new JPanel();
		pnlCentre.setLayout(new GridLayout(5, 2));

		customersBox = new JComboBox<String>();
		for (int i = 0; i < MenuBar.customer.length; i++) {
			customersBox.addItem((MenuBar.customer[i].getCustId() + " " + MenuBar.customer[i].getCustName()));
		}
		JLabel customerInput = new JLabel("Select Customer");
		pnlCentre.add(customerInput);
		pnlCentre.add(customersBox);

		boatBox = new JComboBox<String>();
		if (MenuBar.boats.size() <= 0) {
			boatBox.addItem("Boat Array not loaded");
		} else {
			for (int i = 0; i < MenuBar.boats.size(); i++) {
				boatBox.addItem((MenuBar.boats.get(i).getBoatID() + " " + MenuBar.boats.get(i).getName()));
			}
		}
		JLabel boatInput = new JLabel("Select Boat");
		pnlCentre.add(boatInput);
		pnlCentre.add(boatBox);

		JLabel inputDate = new JLabel("Hire Date (YYYY-MM-DD)");
		hireDateInput = new JTextField();
		pnlCentre.add(inputDate);
		pnlCentre.add(hireDateInput);

		JLabel returnDate = new JLabel("Return Date(YYYY-MM-DD)");
		returnDateInput = new JTextField();
		pnlCentre.add(returnDate);
		pnlCentre.add(returnDateInput);

		JLabel greenInit = new JLabel("Informed of Green Initiative?");
		greenCheck = new JCheckBox();
		pnlCentre.add(greenInit);
		pnlCentre.add(greenCheck);

		pnlCentre.setBorder(BorderFactory.createEtchedBorder());
		return pnlCentre;

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

		return pnlCentre;

	}
}
