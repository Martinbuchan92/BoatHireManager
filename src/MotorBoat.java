
public class MotorBoat extends Boat {

	private boolean award;
	private double discount;
	private final double MAX_DISCOUNT = 0.15;

	public MotorBoat(int id, boolean available, double beam, int berths, String callSign, int channel, String imageName,
			String engine, double length, String name, double rate, int waterTankSize) {
		super(id, available, beam, berths, callSign, channel, imageName, engine, length, name, rate, waterTankSize);
	}

	@Override
	public double calcWaterUsageCharge(boolean award, int numRefills) {
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

	@Override
	public void setFuelDiscount(double discount) {
		if (discount != MAX_DISCOUNT || discount != 0) {
			this.discount = MAX_DISCOUNT;
		}
		this.discount = discount;
	}

	@Override
	public String toString() {
		return "MotorBoat [MAX_DISCOUNT=" + MAX_DISCOUNT + ", award=" + award + ", discount=" + discount
				+ ", getBeam()=" + getBeam() + ", getBerths()=" + getBerths() + ", getBoatID()=" + getBoatID()
				+ ", getCallSign()=" + getCallSign() + ", getChannel()=" + getChannel() + ", getEngine()=" + getEngine()
				+ ", getImageName()=" + getImageName() + ", getLength()=" + getLength() + ", getName()=" + getName()
				+ ", getRate()=" + getRate() + ", getWaterTankSize()=" + getWaterTankSize() + ", isAvailable()="
				+ isAvailable() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + "]";
	}
	// TODO: public boolean equals(Object){}
}
