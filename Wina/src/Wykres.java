import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ColorModel;

import org.apache.commons.math3.distribution.CauchyDistribution;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Wykres  extends ApplicationFrame {

	private static MyLinearRegression ag=new MyLinearRegression();
	
	 public Wykres(final String title) {

	        super(title);

	        final XYDataset dataset = createDataset();
	        final JFreeChart chart = createChart(dataset);
	        final ChartPanel chartPanel = new ChartPanel(chart);
	        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	        setContentPane(chartPanel);

	    }
	   /**
	    * Tworzenie zbioru danych
	    * @return dataset 
	    */
	    private XYDataset createDataset() {
	        
	    	ag.getDataset();
			ag.getLearnDataset();
			ag.getTestDataset();
			ag.learnRegression();
			ag.testLearnedRegression();
	        final XYSeries series1 = new XYSeries("Wartoœæ b³êdu œredniokwadratowego w danej epoce");
	        
	        
	        for(int i=0;i<ag.getSrblad().size();i++)
	        {
	        	series1.add(i+1,ag.getSrblad().get(i));
	        	
	        }
	        
	        final XYSeriesCollection dataset = new XYSeriesCollection();
	        dataset.addSeries(series1); 
	        
	        return dataset;
	        
	    }
	    /**
	     * 
	     * Tworzenie wykresu
	     * @param dataset dane do strozenia wykresu
	     * @return
	     */
	    private JFreeChart createChart(final XYDataset dataset) {
	        
	        // create the chart...
	        final JFreeChart chart = ChartFactory.createScatterPlot(
	            "",      // chart title
	            "Epoka",                      // x axis label
	            "Wartoœæ b³êdu",                      // y axis label
	            dataset,                  // data
	            PlotOrientation.VERTICAL,
	            true,                     // include legend
	            true,                     // tooltips
	            false                     // urls
	        );

	        
	        chart.setBackgroundPaint(Color.white);        
	        final XYPlot plot = chart.getXYPlot();
	       // final CategoryPlot plot=chart.getCategoryPlot();
	        plot.setBackgroundPaint(Color.black);
	        plot.setDomainGridlinePaint(Color.white);
	        plot.setRangeGridlinePaint(Color.white);
	      
	               
	        return chart;
	        
	    }
	    
	    public static void main(String[] args) {
			//MyLinearRegression ag=new MyLinearRegression();
			
			final Wykres wykres = new Wykres("Wykres punktowy");
			
			
		    wykres.pack();
		    RefineryUtilities.centerFrameOnScreen(wykres);
		    wykres.setVisible(true);
		    
		    ArrayRealVector cechy=(ArrayRealVector) ag.getDane().get(0).getSubVector(0, 11);
		    double y=ag.getDane().get(0).getEntry(11);
		    double czas=ag.wyznaczCzas(cechy);
		    System.out.println("Oczekiwana wartosc: " + y +" Wartoœæ otrzymana: " + czas);
		   
		}
	
}
