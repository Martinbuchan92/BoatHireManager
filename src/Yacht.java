import org.junit.Test;

/**
 * Yacht class to store boat objects.
 */
public class Yacht extends Boat {

	private boolean award;
	private double discount;
	private double MAX_DISCOUNT = 0.10;
	private Rigging rigging;

	public Yacht(int id, boolean available, double beam, int berths, String callSign, int channel, String imageName,
			String engine, double length, String name, double rate, int waterTankSize, Rigging rigging) {
		super(id, available, beam, berths, callSign, channel, imageName, engine, length, name, rate, waterTankSize);
		this.rigging = rigging;
	}

	@Override
	protected boolean isValid() {
		super.isValid();

		boolean valid = false;
		if (discount != 0 || discount != 0.1) {
			valid = false;
		} else {
			valid = true;
		}
		return valid;
	}

	/**
	 * @param award
	 *            -Boolean if an award is to be applied or not.
	 * @param int
	 *            numRefills -number of refills that the have happened.
	 * @return double penalty - the penalty cost of refilling the water tank.
	 * @throws AssertionError
	 *             if award is true and the number of refills > 0.
	 */
	@Test
	@Override
	public double calcWaterUsageCharge(boolean award, int numRefills) throws AssertionError {
		String msg = "Assertion Error in Class Yacht. Method: calcWaterUsageCharge(boolean, int).";
		if((award == true && numRefills > 0 )|| (award == false && numRefills <= 0)){
			throw new AssertionError(msg);
		}
		this.award = award;
		if (award) {
			return 0;
		}
		double penalty = (2 * getWaterTankSize()) * numRefills;


		return penalty;
	}

	@Override
	public double getFuelDiscountRate() {
		return discount;
	}

	public Rigging getRigging() {
		return rigging;
	}

	/**
	 * @param discount
	 *            - Level of discount to be applied to the boat.
	 * @return Sets discount level if one of the correct values
	 * @throws IllegalArgumentException
	 *             if not one of the allowable values
	 */
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
		return "Yacht [MAX_DISCOUNT=" + MAX_DISCOUNT + ", award=" + award + ", discount=" + discount + ", rigging="
				+ rigging + ", getBeam()=" + getBeam() + ", getBerths()=" + getBerths() + ", getBoatID()=" + getBoatID()
				+ ", getCallSign()=" + getCallSign() + ", getChannel()=" + getChannel() + ", getEngine()=" + getEngine()
				+ ", getImageName()=" + getImageName() + ", getLength()=" + getLength() + ", getName()=" + getName()
				+ ", getRate()=" + getRate() + ", getWaterTankSize()=" + getWaterTankSize() + ", isAvailable()="
				+ isAvailable() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + "]";
	}
}
