import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class ViewHiresForm extends JPanel implements ActionListener {
	JButton btnClose;
	String[] columnNames = { "Hire ID", "On Hire", "Customer ID", "Boat ID", "Daily Rate", "Hire Date", "Return Date",
			"Total Cost" };
	Object[][] object = new Object[20][8];
	private JComboBox<String> sortBy;
	JFrame vhf;

	public ViewHiresForm() {
		vhf = new JFrame();
		vhf.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		vhf.setTitle("Boat Hire Manager - View Hires");
		vhf.setSize(750, 650);
		vhf.setVisible(true);
		JPanel cn = new JPanel();
		cn.setLayout(new BorderLayout());

		sortBy = new JComboBox<String>(columnNames);
		cn.add(sortBy, BorderLayout.NORTH);
		sortBy.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 2) {
					sort(sortBy.getSelectedIndex());
				}
			}
		});

		for (int i = 0; i < MenuBar.hires.size(); i++) {
			object[i][0] = (String.valueOf(MenuBar.hires.get(i).getHireId()));
			object[i][1] = (String.valueOf(MenuBar.hires.get(i).isOnHire()));
			object[i][2] = (String.valueOf(MenuBar.hires.get(i).getCustomerID()));
			object[i][3] = (String.valueOf(MenuBar.hires.get(i).getBoatId()));
			object[i][4] = (String.valueOf(MenuBar.hires.get(i).getDailyRate()));
			object[i][5] = (String.valueOf(MenuBar.hires.get(i).getHireDate()));
			object[i][6] = (String.valueOf(MenuBar.hires.get(i).getReturnDate()));
			object[i][7] = (String.valueOf(MenuBar.hires.get(i).getTotalCost()));
		}

		JTable table = new JTable(object, columnNames);
		table.setEnabled(false);

		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		cn.add(scrollPane, BorderLayout.CENTER);
		btnClose = new JButton("Close");
		btnClose.addActionListener(this);
		cn.add(btnClose, BorderLayout.SOUTH);
		vhf.setContentPane(cn);

	}

	// Not implemented

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnClose) {
			vhf.dispose();
		}

	}

	public void sort(int itemToSortBy) {
		if (itemToSortBy == 0) {
			// sort by if on Hire or not
		} else if (itemToSortBy == 2) {
			// CustomerID
		} else if (itemToSortBy == 3) {
			// Boat ID
		} else if (itemToSortBy == 4) {
			// Daily Rate
		} else if (itemToSortBy == 5) {
			// Hire Date
		} else if (itemToSortBy == 6) {
			// Return Date
		} else if (itemToSortBy == 7) {
			// Total Cost
		} else {
			// Error
		}

		for (int i = 0; i < MenuBar.hires.size(); i++) {
			if (MenuBar.hires.get(i).getBoatId() == 8) {
				System.out.println("test");
			}
		}
	}

}
