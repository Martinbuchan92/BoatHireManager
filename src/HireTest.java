import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.Test;

public class HireTest {
	Hire h = new Hire(0, "9012SE", 40, Date.valueOf("2017-06-06"), Date.valueOf("2017-06-06"), 500, false, 0);

	@Test
	public void testSetReturnDate() {
		boolean thrown = false;
		
		try{
			h.setReturnDate(Date.valueOf(LocalDate.now())); // Sets return date to today
			h.setReturnDate(Date.valueOf(LocalDate.now().minusDays(1)));  //Sets return date to yesterday
		}catch(AssertionError e){
			thrown = true;
		}
		
		//Sets the return date to tomorrow
		h.setReturnDate(Date.valueOf(LocalDate.now().plusDays(1)));
		//Checks if the set Date is the same.
		if(h.getReturnDate().compareTo((Date.valueOf(LocalDate.now().plusDays(1)))) == 0){
			thrown = true;
		}
		
		//Sets the return date to tomorrow
		h.setReturnDate(Date.valueOf(LocalDate.now().plusDays(3)));
		//Checks if the set Date is the same.
		if(h.getReturnDate().compareTo((Date.valueOf(LocalDate.now().plusDays(3)))) == 0){
			thrown = true;
		}
		
		assertTrue(thrown);
	}

	@Test
	public void testSetTotalDaysHired() {
		boolean thrown = false;
		try{
			h.setTotalDaysHired(-13);
			h.setTotalDaysHired(-1);
		}catch(AssertionError e){
			thrown = true;
		}
		
		h.setTotalDaysHired(0);
		assertTrue(h.getTotalDaysHired() == 0);
		
		h.setTotalDaysHired(5);
		assertTrue(h.getTotalDaysHired() == 5);
		
		assertTrue(thrown);
	}

}
