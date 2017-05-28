import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static java.lang.Math.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.jfree.ui.RefineryUtilities;

public class MyLinearRegression implements LinearRegression {
	private List<ArrayRealVector> dane=new ArrayList<ArrayRealVector>();
	private List<ArrayRealVector> uczacy=new ArrayList<ArrayRealVector>();
	private List<ArrayRealVector> testujacy=new ArrayList<ArrayRealVector>();
	private List<ArrayRealVector> teta=new ArrayList<ArrayRealVector>();
	private List<Double> srblad=new ArrayList<Double>();
	
	
	
	@Override
	public List<ArrayRealVector> getDataset() {
		List<ArrayRealVector> lista = new ArrayList <ArrayRealVector>();
		File in = new File("winequality-red.csv");
		
		try {
			CSVParser parser = CSVParser.parse(in,Charset.forName("UTF-8"), CSVFormat.newFormat(';'));
			double v=0;
			double [] a = new double[12];
			for (CSVRecord csvRecord : parser) {
				if(csvRecord.getRecordNumber()==1)
					continue;
				//System.out.println(csvRecord.toString());
				
				for(int i=0;i<csvRecord.size();i++)
				{
					//System.out.println(csvRecord.get(i));
					v=Double.parseDouble(csvRecord.get(i));
					a[i]=v;
				}
				ArrayRealVector arv=new ArrayRealVector(a);
			    lista.add(arv); 
			 }
			//System.out.println(lista.get(1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		dane=lista;
		return lista;
	}

	@Override
	public List<ArrayRealVector> getLearnDataset() {
		List<ArrayRealVector> uczacy1=new ArrayList<ArrayRealVector>();
		int i=dane.size();
		int s= (int) (i*0.9);
		uczacy1=dane.subList(0,s);
		this.uczacy=uczacy1;
//		System.out.println(dane.size());
	//	System.out.println(uczacy1.size());
		return uczacy1;
	}

	@Override
	public List<ArrayRealVector> getTestDataset() {
		List<ArrayRealVector> testujacy1=new ArrayList<ArrayRealVector>();
		int i=dane.size();
		int s= (int) (i*0.9);
		testujacy1=dane.subList(s,i);
		this.testujacy=testujacy1;
		//System.out.println(testujacy1.size());
		return testujacy1;
	}

	@Override
	public void learnRegression() {
		double alfa=0.0001;
		double y=0;
		double pm=0;
		double pm3=0;
		ArrayRealVector pm2 = new ArrayRealVector();
		ArrayRealVector od = new ArrayRealVector();
		ArrayRealVector ucz = new ArrayRealVector();
		double [] d = new double[11];
		//double [] d = {1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,};
		Random r = new Random();
		for(int i=0;i<11;i++)
		{
			d[i]= r.nextDouble();
		}
		
		ArrayRealVector teta = new ArrayRealVector(d);
		//System.out.println(teta);
		for(int i=0;i<10000;i++){
		
			for(int j=0;j<uczacy.size();j++){ 
				//teta=teta-(alfa*(teta*uczacy.get(j))-y)*uczacy.get(j)
				ucz=(ArrayRealVector) uczacy.get(j).getSubVector(0,11);
				//System.out.println(ucz);
				y=uczacy.get(j).getEntry(11);
				pm=pomnozMacierz(teta,ucz);
				pm3=(pm-y)*alfa;
				pm2=pomnozMacierz(ucz,pm3);
				od=odejmij(teta,pm2);
				teta=od;
				
				//System.out.println(teta);
			}
			this.teta.add(teta);
			
			//System.out.println(teta.getEntry(i));
		}
		//System.out.println(this.teta.size());
	}

	@Override
	public void testLearnedRegression() {
		// TODO Auto-generated method stub
		double blad=0;
		double srblad=0;
		ArrayRealVector test=new ArrayRealVector();
		for(int j=0;j<teta.size();j++)
		{
			for(int i=0;i<testujacy.size();i++){
				test=(ArrayRealVector) testujacy.get(i).getSubVector(0,11);
				blad=pow(pomnozMacierz(teta.get(j),test)-testujacy.get(i).getEntry(11),2)/2;
				srblad+=blad;
			}
			srblad=srblad/testujacy.size();	
			this.srblad.add(srblad);
			//System.out.println(srblad);
			srblad=0;
		}
		
		//System.out.println(srblad);
	}

	public double pomnozMacierz(ArrayRealVector a,ArrayRealVector b){
		ArrayRealVector c=new ArrayRealVector();
		c=a.ebeMultiply(b);		
		double s=0;
		for(int i=0;i<	c.getDimension();i++){
			s+=c.getEntry(i);
		}
		return s;
	}
	
	public ArrayRealVector pomnozMacierz(ArrayRealVector a,double b){
		
		double [] d = new double[a.getDimension()];
		for(int i=0;i<	a.getDimension();i++){
			d[i]=a.getEntry(i)*b;
		}
		ArrayRealVector c=new ArrayRealVector(d);
		return c;
	}
	
	public ArrayRealVector odejmij(ArrayRealVector a,ArrayRealVector b){
		double [] d = new double[a.getDimension()];
		for(int i=0;i<	a.getDimension();i++){
			d[i]=a.getEntry(i)-b.getEntry(i);
		}
		ArrayRealVector c=new ArrayRealVector(d);
		return c;
	}


	public List<Double> getSrblad() {
		return srblad;
	}
	
	public double wyznaczCzas(ArrayRealVector cechy){
		double czas=0;
		czas=pomnozMacierz(teta.get(teta.size()-1),cechy);
		return czas;
	}
	
	public List<ArrayRealVector> getDane() {
		return dane;
	}
}
