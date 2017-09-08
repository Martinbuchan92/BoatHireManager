import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BoatTest {
	HouseBoat b = new HouseBoat(20, true, 500, 500, "VK-BOAT", 10, "noImage.jpg", null, 500, "TestBoat", 1000, 500);

	@Test
	public void testSetChannel() {
		boolean thrown = false;

		try {
			b.setChannel(25);
		} catch (AssertionError e) {
			thrown = true;
		}

		assertTrue(thrown);

		b.setChannel(80);
		assertTrue(b.getChannel() == 80);
		
		assertTrue(thrown);
	}

	@Test
	public void testSetRate() {
		boolean thrown = false;

		try {
			b.setRate(75);
			b.setRate(500);
			b.setRate(10000);
			b.setRate(30561);
		} catch (AssertionError e) {
			thrown = true;
		}

		assertTrue(thrown);
		
			b.setRate(501);
			assertTrue(b.getRate() == 501);
			
			b.setRate(6040);
			assertTrue(b.getRate() == 6040);
	
			b.setRate(9999);
			assertTrue(b.getRate() == 9999);	
			
		assertTrue(thrown);

	}

	@Test
	public void testSetWaterTankSize() {
		boolean thrown = false;

		try {
			b.setWaterTankSize(52);
			b.setWaterTankSize(100);
			b.setWaterTankSize(2500);
			b.setWaterTankSize(56252);
		} catch (AssertionError e) {
			thrown = true;
		}

		assertTrue(thrown);

		try {
			b.setWaterTankSize(101);
			b.setWaterTankSize(1300);
			b.setWaterTankSize(2499);
		} catch (AssertionError e) {
			thrown = false;
		}

		assertTrue(thrown);
	}

}