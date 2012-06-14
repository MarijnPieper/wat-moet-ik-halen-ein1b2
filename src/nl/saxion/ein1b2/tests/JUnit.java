package nl.saxion.ein1b2.tests;

import nl.saxion.ein1b2.CustomDate;
import nl.saxion.ein1b2.TypeToets;
import nl.saxion.ein1b2.Vak;
import android.test.AndroidTestCase;

public class JUnit extends AndroidTestCase {
	
	public void testCustomDate1() {
		CustomDate date = new CustomDate(31,10, 1990);
		assertEquals("31-10-1990", date.toString());
	}
	
	public void testCustomDate2() {
		CustomDate date = new CustomDate(31, 10, 1990, 0,0);
		assertTrue(date.toStringDatumTijd().equals("31-10-1990 0:0"));
	}
	
	public void testCustomDate3() {
		CustomDate date = new CustomDate(31, 10, 1990);
		assertTrue(date.toStringForDB().equals("1990-10-31 0:0"));
	}
	
	public void testTypeToets() {
		TypeToets t = new TypeToets(1, "Proefwerk", 3);
		assertTrue(t.toString().equals("Proefwerk"));
	}
	
	public void testVak() {
		Vak v = new Vak("Wiskunde", false);
		assertTrue(v.toString().equals("Wiskunde"));
	}
}
