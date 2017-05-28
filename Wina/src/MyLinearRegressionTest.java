import static org.junit.Assert.*;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.junit.Before;
import org.junit.Test;

public class MyLinearRegressionTest {
	MyLinearRegression myLinearRegression;

	@Before
	public void setUp() throws Exception {
		myLinearRegression = new MyLinearRegression();
	}

	@Test
	public void testInitializeClass() {
		assert (myLinearRegression != null);
	}

	@Test
	public void testLoadWineDataset() {

		ArrayRealVector ref = new ArrayRealVector(
				new double[] { 7.4, 0.7, 0, 1.9, 0.076, 11, 34, 0.9978, 3.51, 0.56, 9.4, 5 });

		assertEquals(ref, myLinearRegression.getDataset().get(0));
		assertTrue(myLinearRegression.getDataset().size() == 1599);
	}

	@Test
	public void testLearningAndTestDataset() {
		//o dziwo nie przechodzi tego testu pomimo iz jak wyswietlam wielkosc danych to sie zgadza :/
		
		assertTrue(myLinearRegression.getLearnDataset().size() > myLinearRegression.getTestDataset().size());
	}

}
