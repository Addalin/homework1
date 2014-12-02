package homework1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GeoSegmentTest {

	private static final double tolerance = 0.01;

	private GeoPoint gpZivSquare = null;
	private GeoPoint gpWest = null;
	private GeoPoint gpEast = null;
	private GeoPoint gpNorth = null;

	private GeoSegment gsEast = null;
	private GeoSegment gsWest = null;
	private GeoSegment gsNorth = null;
	private GeoSegment gsEast2 = null;
	private GeoSegment gsWest2 = null;
	private GeoSegment gsDiag = null;

	// JUnit calls setUp() before each test__ method is run
	@Before
	public void setUp() {
		gpZivSquare = new GeoPoint(32781944, 35012778);
		gpWest = new GeoPoint(32781944, 35002081);
		gpEast = new GeoPoint(32781944, 35023475);
		gpNorth = new GeoPoint(32790938, 35012778);

		gsEast = new GeoSegment("East", gpZivSquare, gpEast);
		gsWest = new GeoSegment("West", gpZivSquare, gpWest);
		gsNorth = new GeoSegment("North", gpZivSquare, gpNorth);
		gsEast2 = new GeoSegment("East", gpWest, gpZivSquare);
		gsWest2 = new GeoSegment("West", gpWest, gpZivSquare);
		gsDiag = new GeoSegment("NE", gpWest, gpNorth);
	}

	@Test
	public void testEquals() {
		assertEquals("Self equality", gsNorth, gsNorth);
		assertEquals("Equal to copy", gsNorth, new GeoSegment("North",
				gpZivSquare, gpNorth));
		assertTrue("totally different objects are equal.",
				!gsNorth.equals(gsEast));
		assertTrue("same points, different name are equal.",
				!gsEast2.equals(gsWest2));
		assertTrue("same name, different points are equal.",
				!gsEast.equals(gsEast2));
	}

	@Test
	public void testReverse() {
		assertTrue("Reversed segment is not equal same segment reversed.",
				gsEast.reverse().equals(gsEast.reverse()));
		assertTrue("Twice reversed segment is not same as initial.", gsEast
				.reverse().reverse().equals(gsEast));
		assertTrue("New reversed item is not equal to its reversal.",
				gsWest.equals(gsWest2.reverse()));
		assertTrue("New reversed item is not equal to its reversal.", gsWest
				.reverse().equals(gsWest2));
		assertTrue("Segment equal to its reversal.",
				!gsEast.reverse().equals(gsEast));
		assertTrue("Segment reversed twice is equal to its reversal.", !gsEast
				.reverse().reverse().equals(gsEast.reverse()));
		assertTrue("Reversed segment reversed equals reversed.", !gsWest
				.reverse().equals(gsWest2.reverse()));
	}

	@Test
	public void testName() {
		assertEquals("name() doesn't work.", "East", gsEast.getName());
	}

	@Test
	public void testP1() {
		assertEquals(gpZivSquare, gsEast.getP1());
	}

	@Test
	public void testP2() {
		assertEquals(gpZivSquare, gsEast2.getP2());
	}

	@Test
	public void testLength() {
		assertEquals("East 1 km", 1.0, gsEast.getLength(), tolerance);
		assertEquals("West 1 km", 1.0, gsWest.getLength(), tolerance);
		assertEquals("North 1 km", 1.0, gsNorth.getLength(), tolerance);
		assertEquals("1.414 km", 1.414, gsDiag.getLength(), tolerance);
	}

	@Test
	public void testHeading() {
		assertEquals("East should be 90", 90.0, gsEast.getHeading(), tolerance);
		assertEquals("West should be 270", 270.0, gsWest.getHeading(),
				tolerance);
		double nh = gsNorth.getHeading();
		assertTrue("North heading (" + nh + ") is less than zero.", !(nh < 0.0));
		assertTrue("North heading (" + nh + ") is greater or equal to 360.",
				!(nh >= 360.0));
		if (nh > tolerance) {
			// we know nh is in [0..360); maybe it's just under 360, which is
			// okay too
			double delta = Math.abs(360.0 - nh);
			if (delta > tolerance)
				fail("North heading expected: 0 or 359.999 but got " + nh);
		}
		assertEquals("South heading should be 180", 180.0, gsNorth.reverse()
				.getHeading(), tolerance);
	}

	@Test
	public void testEquals2() {
		// make segment components which are equal by value, but which are
		// not the same object
		String north = "Nor";
		north += "th";
		GeoPoint gpZivSquare2 = new GeoPoint(32781944, 35012778);
		GeoPoint gpNorth2 = new GeoPoint(32790938, 35012778);
		GeoSegment gsNorth2 = new GeoSegment(north, gpZivSquare2, gpNorth2);

		assertTrue(
				"Segment equality should use value equality, not reference equality",
				gsNorth.equals(gsNorth2));

		assertTrue("equals(non-GeoSegment) should be false",
				!gsNorth2.equals("aString"));

		assertTrue("equals(null) should be false", !gsNorth2.equals(null));
	}

	@Test
	public void testHashCode() {
		GeoPoint gpZivSquare2 = new GeoPoint(32781944, 35012778);
		GeoPoint gpNorth2 = new GeoPoint(32790938, 35012778);
		GeoSegment gsNorth2 = new GeoSegment("North", gpZivSquare2, gpNorth2);

		assertTrue(".equals() objects must have the same .hashCode()",
				gsNorth.hashCode() == gsNorth2.hashCode());
	}

}
