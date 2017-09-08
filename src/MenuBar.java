import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class MenuBar implements ActionListener {
	static ArrayList<Boat> boats = new ArrayList<Boat>();
	static ArrayList<Hire> hires = new ArrayList<Hire>();
	static Customer[] customer = new Customer[5];

	private static JMenuBar menuBar;
	private JMenuItem itmAbout, itmBoats, itmHires, itmExit, itmHireBoat, itmReturnBoat;
	private JMenu mnuFile, mnuData, mnuHelp, mnuHiresReturns;

	private static final double versionNumber = 1.0;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == itmAbout) {
			JOptionPane.showMessageDialog(null, "Boat Hires V " + versionNumber);

		} else if (e.getSource() == itmExit) {
			MainMethod.logger.info("application exited");
			System.exit(0);

		} else if (e.getSource() == itmBoats) {
			if (boats.size() == 0) {
				try {
					sqlLoadBoats();
				} catch (ClassNotFoundException f) {
					f.printStackTrace();
				}
			}
			new ViewBoatForm();

		} else if (e.getSource() == itmHires) {
			if (hires.size() == 0) {
				try {
					sqlLoadHires();
				} catch (ClassNotFoundException f) {
					f.printStackTrace();
				}
			}
			new ViewHiresForm();

		} else if (e.getSource() == itmHireBoat) {
			try {
				loadCustomers();
				if (hires.size() == 0) {
					sqlLoadHires();
				}
				if (boats.size() == 0) {
					sqlLoadBoats();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			for (int i = 0; i < customer.length; i++) {
				System.out.println(customer[i].getCustId() + " " + customer[i].getCustName());
			}
			new HireBoatForm();

		} else if (e.getSource() == itmReturnBoat) {
			try {
				loadCustomers();
				if (hires.size() == 0) {
					sqlLoadHires();
				}
				if (boats.size() == 0) {
					sqlLoadBoats();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			new ReturnBoatForm();

		}
	}

	public JMenuBar genMenu() {

		menuBar = new JMenuBar();
		mnuFile = new JMenu("File");
		mnuFile.setMnemonic('f');
		mnuData = new JMenu("Data");
		mnuData.setMnemonic('t');
		mnuHelp = new JMenu("Help");
		mnuHelp.setMnemonic('h');
		mnuHiresReturns = new JMenu("Boat Hires/ Returns");

		itmAbout = new JMenuItem("About");
		itmAbout.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		itmBoats = new JMenuItem("View Boats");
		itmBoats.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		itmHires = new JMenuItem("View Hires");
		itmHires.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
		itmExit = new JMenuItem("Exit");
		itmExit.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		itmHireBoat = new JMenuItem("Hire Boat");
		itmHireBoat.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
		itmReturnBoat = new JMenuItem("Return Boat");
		itmReturnBoat.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));

		mnuData.add(mnuHiresReturns);
		mnuData.add(itmBoats);
		mnuData.add(itmHires);
		mnuHiresReturns.add(itmHireBoat);
		mnuHiresReturns.add(itmReturnBoat);

		mnuFile.add(itmExit);
		mnuHelp.add(itmAbout);

		menuBar.add(mnuFile);
		menuBar.add(mnuData);
		menuBar.add(mnuHelp);

		itmHireBoat.addActionListener(this);
		itmReturnBoat.addActionListener(this);
		itmBoats.addActionListener(this);
		itmHires.addActionListener(this);
		itmAbout.addActionListener(this);
		itmExit.addActionListener(this);

		return menuBar;
	}

	private static void sqlLoadBoats() throws ClassNotFoundException {
		Connection conn;
		Statement statement;
		String url = "jdbc:mysql://localhost:3306/luxuriousboats";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, "root", "");
			statement = conn.createStatement();

			ResultSet rs = statement.executeQuery("SELECT * FROM boat");
			while (rs.next()) {
				if (rs.getString("boatType").equals("MotorBoat")) {
					boats.add(new MotorBoat(rs.getInt("id"), rs.getBoolean("available"), rs.getDouble("beam"),
							rs.getInt("berths"), rs.getString("callSign"), rs.getInt("channel"),
							rs.getString("imagename"), rs.getString("engine"), rs.getDouble("beam"),
							rs.getString("name"), rs.getDouble("rate"), rs.getInt("waterTankSize")));

				} else if (rs.getString("boatType").equals("Sailboat")) {
					boats.add(new Yacht(rs.getInt("id"), rs.getBoolean("available"), rs.getDouble("beam"),
							rs.getInt("berths"), rs.getString("callSign"), rs.getInt("channel"),
							rs.getString("imagename"), rs.getString("engine"), rs.getDouble("beam"),
							rs.getString("name"), rs.getDouble("rate"), rs.getInt("waterTankSize"),
							Rigging.valueOf(rs.getString("rigging").toUpperCase())));

				} else if (rs.getString("boatType").equals("HouseBoat")) {
					boats.add(new HouseBoat(rs.getInt("id"), rs.getBoolean("available"), rs.getDouble("beam"),
							rs.getInt("berths"), rs.getString("callSign"), rs.getInt("channel"),
							rs.getString("imagename"), rs.getString("engine"), rs.getDouble("beam"),
							rs.getString("name"), rs.getDouble("rate"), rs.getInt("waterTankSize")));
				} else {
					System.out.println("no boat types found");
				}
			}
			statement.close();
			conn.close();
		} catch (Exception ex) {
			System.err.println("Exception: " + ex.getMessage());
		}

	}

	public static void sqlLoadHires() throws ClassNotFoundException {
		Connection conn;
		Statement statement;
		String url = "jdbc:mysql://localhost:3306/luxuriousboats";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, "root", "");
			statement = conn.createStatement();

			ResultSet res = statement.executeQuery("SELECT * FROM hire");
			while (res.next()) {
				hires.add(new Hire(res.getInt("hireID"), res.getString("customerID"), res.getInt("boatID"),
						res.getDate("hireDate"), res.getDate("returnDate"), res.getDouble("dailyRate"),
						res.getBoolean("onHire"), res.getDouble("totalCost")));
			}

			statement.close();
			conn.close();
		} catch (Exception ex) {
			System.err.println("Exception: " + ex.getMessage());
		}
	}

	private static void loadCustomers() throws IOException {
		FileReader fr = new FileReader("src/customers.txt");
		Scanner scanner = new Scanner(fr);
		scanner.useDelimiter("\n");

		try {
			int i = 0;
			while (scanner.hasNext()) {
				String row = scanner.next();

				String[] tokens = row.split(", ", 3);
				String uid = tokens[0].trim();
				String name = tokens[1].trim();
				String phone = tokens[2].trim();

				customer[i] = new Customer(uid, name, phone);
				i++;
			}
		} catch (InputMismatchException imex) {
			System.out.println("Incorrect file format error in Class MenuBar. Method: loatCustomers()");
		}

		scanner.close();
	}
}