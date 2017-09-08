import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class ViewBoatForm extends JPanel implements ActionListener {
	private final String FILE_LOCATION = "src/images/";

	private JComboBox<String> boatType;
	private JComboBox<Rigging> rigging;
	private JLabel lblBoatImage, lblBoatType, lblRiggingType, description, labels[];
	private JButton btnLoadImage, buttons[];
	private JCheckBox available;
	private JTextField textFields[];
	private JFrame vbf;
	private JPanel cn;
	private JFileChooser jfChooser = new JFileChooser(new File(FILE_LOCATION));
	private String buttonNames[] = { "Edit", "Add", "Update", "Delete", "|<", "<<", ">>", ">|", "Cancel", "Close" };
	private String typesOfBoat[] = { "SailBoat", "HouseBoat", "MotorBoat" };
	private String imageName = "noimage.jpg";
	private String fieldNames[] = { "Boat ID", "Boat Name", "Engine & Fuel", "Beam", "Length", "Sleeps", "VF Channel",
			"Callsign", "Water Tank Capacity ", "Rate" };
	private boolean newBoat;
	private int selectedBoat;
	private boolean changes;

	ViewBoatForm() {
		vbf = new JFrame("Boat Hire Manager - View Boats");
		vbf.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		vbf.setSize(750, 650);
		vbf.setVisible(true);
		vbf.setLocation(50, 50);

		JPanel pnlCentre = CentrePanel();
		JPanel pnlSouth = SouthPanel();
		JPanel pnlEast = EastPanel();

		cn.setLayout(new BorderLayout());
		cn.add(pnlSouth, BorderLayout.SOUTH);
		cn.add(pnlEast, BorderLayout.EAST);
		cn.add(pnlCentre, BorderLayout.CENTER);
		vbf.setContentPane(cn);

		// clicks the first item button to populate the form.
		buttons[4].doClick();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0] || e.getSource() == buttons[8]) {
			editToggle();

		} else if (e.getSource() == buttons[1]) {
			editToggle();
			clearFields();
			rigging.setEnabled(true);
			newBoat = true;

		} else if (e.getSource() == buttons[2]) {
			if (newBoat == true) {
				try {
					addBoat();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				newBoat = false;
			} else {
				try {
					editText();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
			editToggle();

		} else if (e.getSource() == buttons[3]) {
			try {
				sqlDeleteBoat();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

		} else if (e.getSource() == buttons[4]) {
			navigation(0);
		} else if (e.getSource() == buttons[5]) {
			navigation(1);
		} else if (e.getSource() == buttons[6]) {
			navigation(2);
		} else if (e.getSource() == buttons[7]) {
			navigation(3);
		} else if (e.getSource() == buttons[8]) {
			setBoat();
			newBoat = false;

		} else if (e.getSource() == btnLoadImage) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
			jfChooser.setFileFilter(filter);
			jfChooser.showOpenDialog(this);
			if (jfChooser.getSelectedFile() != null) {
				imageName = jfChooser.getName(jfChooser.getSelectedFile());
				lblBoatImage.setIcon(new ImageIcon(FILE_LOCATION + imageName));
			}

		} else if (e.getSource() == buttons[9]) {
			if (changes == true) {
				JOptionPane.showMessageDialog(null, "Changes saved");
			}
			vbf.dispose();
		}
	}

	public void addBoat() throws ClassNotFoundException {
		Boat obj;
		int id = Integer.parseInt(textFields[0].getText());
		String name = (textFields[1].getText());
		String engine = (textFields[2].getText());
		Double beam = (Double.parseDouble(textFields[3].getText()));
		Double length = (Double.parseDouble(textFields[4].getText()));
		int berths = (Integer.parseInt(textFields[5].getText()));
		int channel = (Integer.parseInt(textFields[6].getText()));
		String callSign = (textFields[7].getText());
		int waterTank = (Integer.parseInt(textFields[8].getText()));
		Double rate = (Double.parseDouble(textFields[9].getText()));
		boolean avail = (available.isSelected());
		String image = imageName;
		Rigging rigg = null;
		String type = boatType.getSelectedItem().toString();

		if (boatType.getSelectedIndex() == 0) {
			rigg = (Rigging) rigging.getSelectedItem();
			obj = new Yacht(id, avail, beam, berths, callSign, channel, image, engine, length, name, rate, waterTank,
					rigg);
			MenuBar.boats.add(obj);

		} else if (boatType.getSelectedIndex() == 1) {
			obj = new HouseBoat(id, avail, beam, berths, callSign, channel, image, engine, length, name, rate,
					waterTank);
			MenuBar.boats.add(obj);

		} else if (boatType.getSelectedIndex() == 2) {
			obj = new MotorBoat(id, avail, beam, berths, callSign, channel, image, engine, length, name, rate,
					waterTank);
			MenuBar.boats.add(obj);
		}

		Connection conn;
		Statement statement;
		String url = "jdbc:mysql://localhost:3306/luxuriousboats";

		try {
			Class.forName("com.mysql.jdbc.Driver"); // Load MYSQL driver
			conn = DriverManager.getConnection(url, "root", "");
			statement = conn.createStatement();

			String query = "INSERT INTO boat" + " VALUES(" + id + ", " + avail + ", '" + engine + "', " + berths + ", "
					+ beam + ", " + length + ", '" + callSign + "', '" + image + "', '" + name + "', " + channel + ", "
					+ waterTank + ", " + rate + ", '" + type + "', '" + rigg + "', " + 0 + ");";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.executeUpdate();
			JOptionPane.showMessageDialog(cn, "Boat added");
			buttons[5].doClick();

			changes = true;
			statement.close();
			conn.close();
		} catch (Exception ex) {
			System.err.println("Exception: " + ex.getMessage());
		}
	}

	public JPanel CentrePanel() {
		cn = new JPanel();
		cn.setLayout(new BorderLayout());

		/* Centre Panel */
		JPanel pnlCentre = new JPanel();
		int numFields = fieldNames.length;
		buttons = new JButton[buttonNames.length];
		labels = new JLabel[numFields];
		textFields = new JTextField[numFields];
		pnlCentre.setLayout(new GridLayout(numFields + 1, 2));
		// Add labels and textfields to centre panel
		for (int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel(fieldNames[i]);
			textFields[i] = new JTextField(30); // Set default size to 20
			textFields[i].setEditable(false);
			pnlCentre.add(labels[i]);
			pnlCentre.add(textFields[i]);
		}
		available = new JCheckBox("available", false);
		pnlCentre.add(available);
		description = new JLabel();
		pnlCentre.add(description);
		pnlCentre.setBorder(BorderFactory.createEtchedBorder());
		return pnlCentre;

	}

	public void clearFields() {
		for (int i = 0; i < textFields.length; i++) {
			textFields[i].setText("");
		}
		lblBoatImage.setIcon(new ImageIcon(FILE_LOCATION + "noimage.jpg"));
	}

	public JPanel EastPanel() {
		JPanel pnlEast = new JPanel();
		pnlEast.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		lblBoatImage = new JLabel(new ImageIcon(FILE_LOCATION + imageName));
		pnlEast.add(lblBoatImage, c);

		btnLoadImage = new JButton("Load Image");
		btnLoadImage.addActionListener(this);
		btnLoadImage.setToolTipText("Select a boat image from your drive");
		c.gridy = 1;
		pnlEast.add(btnLoadImage, c);
		JLabel description = new JLabel(
				"<HTML>All our boats have:<ul><li>Fully equipped galleys</li><li>Hot Water</li><li>Drinking Water</li><li>Head & Shower</li></ul></HTML>");
		c.gridy = 2;
		pnlEast.add(description, c);
		lblBoatType = new JLabel("Select Boat Type:");
		boatType = new JComboBox<String>(typesOfBoat);
		c.gridy = 3;
		pnlEast.add(lblBoatType, c);
		c.gridy = 4;
		pnlEast.add(boatType, c);

		lblRiggingType = new JLabel("Select Rigging:");
		rigging = new JComboBox<Rigging>(Rigging.values());
		c.gridy = 5;
		pnlEast.add(lblRiggingType, c);
		c.gridy = 6;
		pnlEast.add(rigging, c);
		return pnlEast;
	}

	public void editText() throws ClassNotFoundException {
		Boat f = MenuBar.boats.get(selectedBoat);

		f.setName(textFields[1].getText());
		f.setEngine(textFields[2].getText());
		f.setBeam(Double.parseDouble(textFields[3].getText()));
		f.setLength(Double.parseDouble(textFields[4].getText()));
		f.setBerths(Integer.parseInt(textFields[5].getText()));
		try {
			f.setChannel(Integer.parseInt(textFields[6].getText()));
		} catch (AssertionError e) {
			String msg = e.getMessage();
			msg += "\n";
			msg += this.toString();
			MainMethod.logger.log(Level.ALL, msg);
			JOptionPane.showMessageDialog(null, "Please enter a valid Channel ( 21, 22, 27, 80, 81, 82, 88 )");
		}
		f.setCallSign(textFields[7].getText());
		try {
			f.setWaterTankSize(Integer.parseInt(textFields[8].getText()));
		} catch (AssertionError e) {
			String msg = e.getMessage();
			msg += "\n";
			msg += this.toString();
			MainMethod.logger.log(Level.ALL, msg);
			JOptionPane.showMessageDialog(null, "Please enter a valid tank size between 100L and 2500L");
		}

		try {
			f.setRate(Double.parseDouble(textFields[9].getText()));
		} catch (AssertionError e) {
			String msg = e.getMessage();
			msg += "\n";
			msg += this.toString();
			MainMethod.logger.log(Level.ALL, msg);
			JOptionPane.showMessageDialog(null, "Please enter a valid Rate");
		}
		f.setAvailability(available.isSelected());
		f.setImageName(imageName);

		Connection conn;
		PreparedStatement preparedStmt;
		String url = "jdbc:mysql://localhost:3306/luxuriousboats";

		try {
			Class.forName("com.mysql.jdbc.Driver"); // Load MYSQL driver
			conn = DriverManager.getConnection(url, "root", "");

			String query = "UPDATE boat SET" + " name = '" + f.getName() + "', available = " + f.isAvailable()
					+ ", engine = '" + f.getEngine() + "', berths = " + f.getBerths() + ", beam = " + f.getBeam()
					+ ", boatlength = " + f.getLength() + ", callsign = '" + f.getCallSign() + "', imageName = '"
					+ f.getImageName() + "', channel = " + f.getChannel() + ", watertanksize = " + f.getWaterTankSize()
					+ ", rate = " + f.getRate() + ", boatType = '" + boatType.getSelectedItem().toString() + "' ";
			if ((f.getClass().getName()).equals("Yacht")) {
				rigging.setEnabled(true);
				query += ", rigging = '" + (((Yacht) f).getRigging().toString());
			} else {
				query += ", rigging = null";
			}
			query += " WHERE id = ? ;";

			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, f.getBoatID());
			preparedStmt.executeUpdate();
			JOptionPane.showMessageDialog(cn, "Boat Updated");
			buttons[5].doClick();
			buttons[6].doClick();

			changes = true;
			preparedStmt.close();
			conn.close();
		} catch (Exception ex) {
			System.err.println("Exception: " + ex.getMessage());
			String msg = ex.getMessage();
			msg += "\n";
			msg += this.toString();
			MainMethod.logger.log(Level.ALL, msg);
		}
	}

	public void editToggle() {
		if (buttons[0].isEnabled() == true) {
			buttons[0].setEnabled(false);
			buttons[1].setEnabled(false);
			buttons[2].setEnabled(true);
			buttons[3].setEnabled(false);
			buttons[4].setEnabled(false);
			buttons[5].setEnabled(false);
			buttons[6].setEnabled(false);
			buttons[7].setEnabled(false);
			buttons[8].setEnabled(true);
			buttons[9].setEnabled(false);
			for (int i = 0; i < labels.length; i++) {
				textFields[i].setEditable(true);
			}

		} else {
			buttons[0].setEnabled(true);
			buttons[1].setEnabled(true);
			buttons[2].setEnabled(false);
			buttons[3].setEnabled(true);
			buttons[4].setEnabled(true);
			buttons[5].setEnabled(true);
			buttons[6].setEnabled(true);
			buttons[7].setEnabled(true);
			buttons[8].setEnabled(false);
			buttons[9].setEnabled(true);
			for (int i = 0; i < labels.length; i++) {
				textFields[i].setEditable(false);
			}

		}
	}

	public void navigation(int n) {
		// 0- First, 1-Back, 2-Forward, 3-Last
		if (n == 0) {
			selectedBoat = n;
		} else if (n == 1 && selectedBoat > 0) {
			selectedBoat--;
		} else if (n == 2 && selectedBoat < MenuBar.boats.size() - 1) {
			selectedBoat++;
		} else if (n == 3) {
			selectedBoat = MenuBar.boats.size() - 1;
		}
		Boat f = MenuBar.boats.get(selectedBoat);

		setBoat();

		if ((f.getClass().getName()).equals("Yacht")) {
			rigging.setSelectedItem(((Yacht) f).getRigging());
			description.setText(((Yacht) f).getRigging().getSails());
			rigging.setEnabled(true);
		} else {
			rigging.setSelectedItem("");
			description.setText("");
			rigging.setEnabled(false);
		}

		imageName = f.getImageName();
		lblBoatImage.setIcon(new ImageIcon(FILE_LOCATION + imageName));

		if (!MenuBar.boats.get(selectedBoat).isAvailable()) {
			buttons[3].setEnabled(false);
		} else
			buttons[3].setEnabled(true);

	}

	public void setBoat() {
		Boat f = MenuBar.boats.get(selectedBoat);

		textFields[0].setText(String.valueOf(f.getBoatID()));
		textFields[1].setText(f.getName());
		textFields[2].setText(f.getEngine());
		textFields[3].setText(String.valueOf(f.getBeam()));
		textFields[4].setText(String.valueOf(f.getLength()));
		textFields[5].setText(String.valueOf(f.getBerths()));
		textFields[6].setText(String.valueOf(f.getChannel()));
		textFields[7].setText(f.getCallSign());
		textFields[8].setText(String.valueOf(f.getWaterTankSize()));
		textFields[9].setText(String.valueOf(f.getRate()));
		available.setSelected(f.isAvailable());
		available.setSelected(f.isAvailable());
		boatType.setSelectedItem(String.valueOf(f.getClass().getName()));
	}

	public JPanel SouthPanel() {
		/* South Panel */
		JPanel pnlSouth = new JPanel();
		// Add buttons to south panel
		for (int i = 0; i < buttonNames.length; i++) {
			buttons[i] = new JButton(buttonNames[i]);
			pnlSouth.add(buttons[i]);
			buttons[i].addActionListener(this);
		}
		buttons[2].setEnabled(false);
		buttons[8].setEnabled(false);

		buttons[0].setToolTipText("Edit selected boat");
		buttons[1].setToolTipText("Add a new boat");
		buttons[2].setToolTipText("Update selected boat");
		buttons[3].setToolTipText("Delete selected boat");
		buttons[4].setToolTipText("First boat");
		buttons[5].setToolTipText("Previous boat");
		buttons[6].setToolTipText("Next boat");
		buttons[7].setToolTipText("Last boat");
		buttons[8].setToolTipText("Cancel Edit");
		buttons[9].setToolTipText("Close window");
		return pnlSouth;

	}

	public void sqlDeleteBoat() throws ClassNotFoundException {
		Connection conn;
		Statement statement;
		String url = "jdbc:mysql://localhost:3306/luxuriousboats";

		try {
			Class.forName("com.mysql.jdbc.Driver"); // Load MYSQL driver
			conn = DriverManager.getConnection(url, "root", "");
			statement = conn.createStatement();

			int deleteID = Integer.parseInt(textFields[0].getText());
			String query = "DELETE FROM boat WHERE id = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, deleteID);
			preparedStmt.executeUpdate();
			MenuBar.boats.remove(selectedBoat);
			JOptionPane.showMessageDialog(cn, "Boat deleted");
			buttons[5].doClick();

			statement.close();
			conn.close();
		} catch (Exception ex) {
			System.err.println("Exception: " + ex.getMessage());
		}

	}

}