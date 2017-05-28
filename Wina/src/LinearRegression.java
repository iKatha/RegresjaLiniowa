import java.util.List;
import org.apache.commons.math3.linear.ArrayRealVector;

public interface LinearRegression {
	
		public List<ArrayRealVector> getDataset();
		
		public List<ArrayRealVector> getLearnDataset();
		
		public List<ArrayRealVector> getTestDataset();
		
		public void learnRegression();
		
		public void testLearnedRegression();

}
