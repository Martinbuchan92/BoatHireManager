import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HouseBoat extends Boat {

	private boolean award;
	private double discount;
	private final double MAX_DISCOUNT = 0.05;

	public HouseBoat(int id, boolean available, double beam, int berths, String callSign, int channel, String imageName,
			String engine, double length, String name, double rate, int waterTankSize) {
		super(id, available, beam, berths, callSign, channel, imageName, engine, length, name, rate, waterTankSize);
	}

	@Override
	protected boolean isValid() {
		super.isValid();

		boolean valid = false;
		if (discount != 0 || discount != 0.05) {
			valid = false;
		} else {
			valid = true;
		}
		return valid;
	}

	@Test
	@Override
	public double calcWaterUsageCharge(boolean award, int numRefills) throws AssertionError {
		String msg = "Assertion Error in Class Yacht. Method: calcWaterUsageCharge(boolean, int).";
		this.award = award;
		if (award) {
			return 0;
		}
		double penalty = (2 * getWaterTankSize()) * numRefills;

		assertTrue(msg, (award == true && numRefills == 0) || (award == false && numRefills > 0));

		return penalty;
	}

	@Override
	public double getFuelDiscountRate() {
		return discount;
	}

	@Test
	@Override
	public void setFuelDiscount(double discount) throws IllegalArgumentException {
		String msg = "Illegal Arguement Exception in Class Yacht . Method: setFuelDiscount(double).";
		if (discount == MAX_DISCOUNT || discount == 0.0) {
			this.discount = discount;
		} else {
			throw new IllegalArgumentException(msg);
		}
	}

	@Override
	public String toString() {
		return "HouseBoat [MAX_DISCOUNT=" + MAX_DISCOUNT + ", award=" + award + ", discount=" + discount
				+ ", getBeam()=" + getBeam() + ", getBerths()=" + getBerths() + ", getBoatID()=" + getBoatID()
				+ ", getCallSign()=" + getCallSign() + ", getChannel()=" + getChannel() + ", getEngine()=" + getEngine()
				+ ", getImageName()=" + getImageName() + ", getLength()=" + getLength() + ", getName()=" + getName()
				+ ", getRate()=" + getRate() + ", getWaterTankSize()=" + getWaterTankSize() + ", isAvailable()="
				+ isAvailable() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + "]";
	}
	// TODO: public boolean equals(Object){}
}
