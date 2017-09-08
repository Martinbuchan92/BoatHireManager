/**
 * Customer class to store customer objects.
 */
public class Customer {
	private final static double ALMOST_FAMILY = 0.2;
	private final static double STD_DISCOUNT = 0.05;
	private final static double WHAT_DISCOUNT = 0; // Final discount levels.

	/**
	 * Searches for specific customer by their ID number and returns the Index
	 * value as an Int.
	 *
	 * @param custID
	 *            Holds the customer ID as input through the method references.
	 * @return returns the array index number of the customer searched for.
	 */

	static int findCustomer(String custID, Customer[] customers) {

		for (int i = 0; (i < customers.length); i++) {
			if ((customers[i].getCustId()).equals(custID)) {
				return i;
			}
		}
		System.out.println("Incorrect Customer ID");
		return -1;
	}

	private String custId, custName, phoneNo;
	private double discount = 0.0;

	private boolean hired;

	Customer(String custId, String custName, String phoneNo) { // Constructor
																// requesting
																// ID, Name and
																// Phone No
																// information.
		this.custId = custId;
		this.custName = custName;
		this.phoneNo = phoneNo;
	}

	String getCustId() {
		return this.custId;
	}

	String getCustName() {
		return this.custName;
	}

	double getDiscount() {
		return this.discount;
	}

	boolean getHired() {
		return hired;
	}

	String getPhoneNo() {
		return this.phoneNo;
	}

	/**
	 * sets the discount amount due for each customer, as input by the user.
	 *
	 * @param level
	 *            input is an int of 1, 2, or 3 depending on which level of
	 *            discount they are to receive.
	 */
	void setDiscount(int level) {
		double discount = 0.0;

		if (level == 1) {
			discount = WHAT_DISCOUNT;
		} else if (level == 2) {
			discount = STD_DISCOUNT;
		} else if (level == 3) {
			discount = ALMOST_FAMILY;
		}
		this.discount = discount;
	}

	void setHired(boolean hired) {
		this.hired = hired;
	}

	@Override
	public String toString() {
		return "Customer [custId=" + custId + ", custName=" + custName + ", phoneNo=" + phoneNo + ", hired=" + hired
				+ ", discount=" + discount + "]";
	}

}