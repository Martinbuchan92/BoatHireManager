import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import org.junit.Test;

public abstract class Boat {

	// class fields
	public static final String DEFAULT_CALLSIGN = "VK-BoatHire";
	private static final int ID_INCREMENT = 8;
	public static final double MIN_DISCOUNT = 0.0;
	public static int nextBoatID = 8; // this is public to aid id synchronising
										// with database id's
	public static final String NOIMAGENAME = "noimage.jpg";

	public static final double WATER_PRICE_PER_LITRE = 2.0;

	/**
	 * This method finds a boat in an array of boats using boat id
	 * 
	 * @param boats
	 *            an ArrayList<Boat> of boats
	 * @param boatID
	 *            hire boat ID
	 * @return boat position in the array or -1 if not found
	 */
	public static int findBoat(ArrayList<Boat> boats, int boatID) {

		int index = 0;

		for (Boat b : boats) {
			if (b.getBoatID() == boatID) {
				return index;
			}
			index++;
		}
		return -1;
	}

	// instance fields
	private boolean available; // convert to Y N in toString
	private double beam; // in metres
	private int berths; // sleeps this many
	private String callSign; // marine radio boat call sign, e.g. VK#Foxx
	private int channel; // marine radio channel number, e.g. 88, 27 for boat
							// contact
	private String engine; // e.g. Yanmar 16hp IB Diesel
	private int id; // unique id
	private String imageName; // boats image filename.ext only - no path info
	private double length; // in metres
	private String name; // boat name e.g. Firecracker
	private double rate; // money value

	private int waterTankSize; // in litres

	/* constructor */
	public Boat(int id, boolean available, double beam, int berths, String callSign, int channel, String imageName,
			String engine, double length, String name, double rate, int waterTankSize) {

		if (id == -1) { // new boat not from database
			// nextBoatID should have been updated by ViewBoats - some
			// assertion!
			id = nextBoatID;
			nextBoatID += ID_INCREMENT;
		}
		this.id = id;

		this.setAvailability(available);
		this.setBeam(beam);
		this.setBerths(berths);
		this.setCallSign(callSign);
		try {
			this.setChannel(channel);
		} catch (AssertionError e) {
			String msg = e.getMessage();
			msg += "\n";
			msg += this.toString();
			MainMethod.logger.log(Level.ALL, msg);
			JOptionPane.showMessageDialog(null, "Please enter a valid Channel ( 21, 22, 27, 80, 81, 82, 88 )");
		}
		this.setEngine(engine);
		this.imageName = imageName;
		this.setLength(length);
		this.setName(name);
		try {
			this.setRate(rate);
		} catch (AssertionError e) {
			String msg = e.getMessage();
			msg += "\n";
			msg += this.toString();
			MainMethod.logger.log(Level.ALL, msg);
			JOptionPane.showMessageDialog(null, "Please enter a valid Rate");
		}
		try {
			this.setWaterTankSize(waterTankSize);
		} catch (AssertionError e) {
			String msg = e.getMessage();
			msg += "\n";
			msg += this.toString();
			MainMethod.logger.log(Level.ALL, msg);
			JOptionPane.showMessageDialog(null, "Please enter a valid tank size between 100L and 2500L");
		}
	}

	public abstract double calcWaterUsageCharge(boolean award, int numRefills);

	/* end abstract methods */
	/* begin Comparable interface implementation */
	public int compareTo(Boat b) {
		int test = this.getName().compareToIgnoreCase(b.getName());
		System.out.println(test);
		return test;
	}

	/* end Comparable interface implementation */
	/* begin Object Overrides */
	@Override
	public boolean equals(Object o) {
		if (this.getBoatID() == (this.getBeam())) {
			return true;
		}

		return false;
	}

	protected boolean isValid() {
		boolean valid = true;

		int validChannel[] = { 21, 22, 27, 80, 81, 82, 88 };

		for (int i = 0; i < validChannel.length; i++) {
			if (validChannel[i] == channel) {
				valid = true;
			} else {
				valid = false;
			}
		}
		if (this.beam <= 0) {
			valid = false;
		} else if (this.berths <= 0) {
			valid = false;
		} else if (this.callSign.length() <= 0 || this.callSign.length() > 50) {
			valid = false;
		} else if (imageName.length() <= 0 || imageName.length() > 100 || !(imageName.endsWith(".jpg"))) {
			if (imageName.equals(null)) {
				valid = true;
			} else {
				valid = false;
			}
		} else if (this.engine.length() <= 0 && this.engine.length() >= 100) {
			valid = false;
		} else if (this.length <= 3 || this.length >= 30) {
			valid = false;
		} else if (this.name == null) {
			valid = false;
		} else if (this.rate <= 500 || this.rate >= 10000) {
			valid = false;
		} else if (this.waterTankSize <= 100 || this.waterTankSize >= 2500) {
			valid = false;
		}

		return valid;
	}

	public double getBeam() {
		return this.beam;
	}

	public int getBerths() {
		return this.berths;
	}

	public int getBoatID() {
		return this.id;
	}

	public String getCallSign() {
		return this.callSign;
	}

	public int getChannel() {
		return this.channel;
	}

	public String getEngine() {
		return this.engine;
	}

	/* abstract method(s) */
	public abstract double getFuelDiscountRate();

	public String getImageName() {
		return this.imageName;
	}

	public double getLength() {
		return this.length;
	}

	public String getName() {
		return this.name;
	}

	public double getRate() {
		return this.rate;
	}

	public int getWaterTankSize() {
		return this.waterTankSize;
	}

	public boolean isAvailable() {
		return this.available;
	}

	public void setAvailability(boolean available) {
		this.available = available;
	}

	public void setBeam(double beam) {
		this.beam = beam;
	}

	public void setBerths(int berths) {
		this.berths = berths;
	}

	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}

	/**
	 * 
	 * @param channel
	 *            input number for setting as channel
	 * @Throws assertionError If not one of the valid channels
	 * @return sets the correct channel Sets the channel if it's valid,
	 *         otherwise sets to default of 27
	 */
	@Test
	public void setChannel(int channel) throws AssertionError {
		String msg = "Assertion Error in class Boat.  Method: setChannel(int)";

		Integer validChannel[] = { 21, 22, 27, 80, 81, 82, 88 };

		this.channel = 21; // sets default in-case input is incorrect

		if (Arrays.asList(validChannel).contains(channel)) {
			this.channel = channel;
		} else {
			throw new AssertionError(msg);
		}

	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public abstract void setFuelDiscount(double discount);

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @param rate
	 *            Daily rate for renting the boat
	 * @return Sets the rate if it's within correct bounds, otherwise it rounds
	 *         up or down to the nearest allowable value.
	 * @throws assertionError
	 *             if rate is not within allowable values.
	 */
	@Test
	public void setRate(double rate) throws AssertionError {
		String msg = "Assertion Error in class Boat.  Method: setRate(double)";

		if (rate > 500 && rate < 10000) {
			this.rate = rate;
		} else {
			throw new AssertionError(msg);
		}
	}

	/**
	 * @param waterTankSize
	 * 
	 * @return Sets the water tank size if it's within correct bounds, otherwise
	 *         it rounds up or down to nearest allowable value.
	 * 
	 * @throws assertionError
	 *             if rate is not within allowable values.
	 */
	@Test
	public void setWaterTankSize(int waterTankSize) throws AssertionError {
		String msg = "Assertion Error in class Boat.  Method: setWaterTankSize(int)";

		if (waterTankSize > 100 && waterTankSize < 2500) {
			this.waterTankSize = waterTankSize;
		} else {
			throw new AssertionError(msg);
		}
	}

	@Override
	public String toString() {
		return "Boat [available=" + available + ", beam=" + beam + ", berths=" + berths + ", callSign=" + callSign
				+ ", channel=" + channel + ", engine=" + engine + ", id=" + id + ", imageName=" + imageName
				+ ", length=" + length + ", name=" + name + ", rate=" + rate + ", waterTankSize=" + waterTankSize + "]";
	}

}