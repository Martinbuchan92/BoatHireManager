import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HouseBoatTest {
	HouseBoat b = new HouseBoat(20, true, 500, 500, "VK-BOAT", 10, "noImage.jpg", null, 500, "TestBoat", 1000, 500);

	@Test
	public void testCalcWaterUsageCharge() {
		boolean thrown = false;

		try{
			b.calcWaterUsageCharge(true, 7);
			b.calcWaterUsageCharge(false, 0);
			b.calcWaterUsageCharge(true, -2);
		}catch(AssertionError e){
			thrown = true;
		}
		
		assertEquals(0,b.calcWaterUsageCharge(true, 0), 0);
		assertEquals(5000,b.calcWaterUsageCharge(false, 5), 0);

		assertTrue(thrown);	}

	@Test
	public void testSetFuelDiscount() {
		boolean thrown = false;

		try{
			b.setFuelDiscount(-5);
			b.setFuelDiscount(-0.9);
			b.setFuelDiscount(0.04);
			b.setFuelDiscount(0.06);
			b.setFuelDiscount(0.75);
		}catch(IllegalArgumentException e){
			thrown = true;
		}
		
		b.setFuelDiscount(0);
		assertTrue(b.getFuelDiscountRate() == 0);
		
		b.setFuelDiscount(0.05);
		assertTrue(b.getFuelDiscountRate() == 0.05);
		
		assertTrue(thrown);
	}

}
