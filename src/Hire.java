import java.sql.Date;
import java.time.LocalDate;

import org.junit.Test;

/**
 * Class for Hire objects, used with ArrayList to hold the details of all the
 * boats that have been hired.
 */
public class Hire {
	private static int nextHireID = 0;
	private int boatId;
	private String customerID;
	private double dailyRate;
	private Date hireDate;
	private int hireId;
	private boolean onHire;
	private Date returnDate;
	private double totalCost;
	private int totalDaysHired;

	public static long findDaysHired(Date d1, Date d2) {
		if (d1 == null || d2 == null) {
			return 0;
		} else {
			long diff = Math.abs(d1.getTime() - d2.getTime());
			long diffDays = (diff + 12 * 60 * 60 * 1000) / (24 * 60 * 60 * 1000);
			return diffDays;
		}
	}

	static int findHire(String custId, int boatId) {

		for (int i = 0; i < MenuBar.hires.size(); i++) {
			if (MenuBar.hires.get(i).getCustomerID().equals(custId) && MenuBar.hires.get(i).getBoatId() == boatId) {
				return i;
			}
		}
		return -1;
	}

	public static int getNextHireID() {
		nextHireID++;
		return nextHireID;
	}

	Hire(int hireId, String custId, int boatId, Date hireDate, Date returnDate, double dailyRate, boolean onHire,
			double totalCost) {

		if (nextHireID < hireId) {
			nextHireID = hireId;
		}
		this.hireId = hireId;
		this.customerID = custId;
		this.boatId = boatId;
		this.hireDate = hireDate;
		this.returnDate = returnDate;
		this.dailyRate = dailyRate;
		this.onHire = onHire;
		this.totalCost = totalCost;

	}

	public int getBoatId() {
		return boatId;
	}

	public String getCustomerID() {
		return customerID;
	}

	public double getDailyRate() {
		return dailyRate;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public int getHireId() {
		return hireId;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public int getTotalDaysHired() {
		return totalDaysHired;
	}

	public boolean isOnHire() {
		return onHire;
	}

	public void setBoatId(int boatId) {
		this.boatId = boatId;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public void setDailyRate(double dailyRate) {
		this.dailyRate = dailyRate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public void setHireId(int hireId) {
		this.hireId = hireId;
	}

	public void setOnHire(boolean onHire) {
		this.onHire = onHire;
	}

	/**
	 * 
	 * @param returnDate
	 *            -Input the date that the boat has been returned.
	 * @throws AssertionError
	 *             -Throws AssertionError if the return date is today or earlier
	 */
	public void setReturnDate(Date returnDate) throws AssertionError {
		String msg = "Assertion Error in Class Hire. Method: setReturnDate(Date).";

		Date today = Date.valueOf(LocalDate.now());
		// Date tomorrow = Date.valueOf(LocalDate.now().plusDays(1));
		if (returnDate.after(today)) {
			this.returnDate = returnDate;
		} else {
			throw new AssertionError(msg);
		}
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	/**
	 * 
	 * @param totalHireTime
	 *            -Total number of days the boat has been hired
	 * @throws AssertionError
	 *             -If total days hired is a negative value
	 * @return set the total days hired
	 */
	@Test
	public void setTotalDaysHired(int totalHireTime) throws AssertionError {
		String msg = "Assertion Error in Class Hire. Method: setTotalDaysHired(double).";

		if (totalHireTime >= 0) {
			this.totalDaysHired = totalHireTime;
		} else {
			throw new AssertionError(msg);
		}
	}

	protected boolean isValid() {
		boolean valid = true;
		Date today = Date.valueOf(LocalDate.now());

		if (returnDate.after(today)) {
			valid = true;
		} else if (this.totalDaysHired <= 0) {
			valid = false;
		}

		return valid;
	}

	@Override
	public String toString() {
		return "Hire [onHire=" + onHire + ", hireId=" + hireId + ", customerID=" + customerID + ", dailyRate="
				+ dailyRate + ", hireDate=" + hireDate + ", returnDate=" + returnDate + ", totalDaysHired="
				+ totalDaysHired + ", boatId=" + boatId + ", totalCost=" + totalCost + "]";
	}

}