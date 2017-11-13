import org.jfree.chart.*;
import org.jfree.data.xy.*;
import org.jfree.chart.plot.PlotOrientation;
import java.io.IOException;
import java.io.File;

public class Douglas_Peucker{
	static Node head = null;
	static Node tail = null;
	static int size = 0;
	
	
	public static void insertAtEnd(double x1, double y1){
		Node nptr = new Node(x1, y1, null);    
        size++;
        if(head == null) 
        {
            head = nptr;
            tail = head;
        }
        else 
        {
            tail.setLink(nptr);
            tail = nptr;
        }
	}
	
	public static void display(){
		if(head == null){
			System.out.println("empty!");
			return;
		}
		else{
			System.out.print(head.getx()+","+head.gety()+" -> ");
			Node p = head;
			p = p.getLink();
			while(p!=null){
				System.out.print(p.getx()+","+p.gety()+" -> ");
				p = p.getLink();
			}
		}
	}
	
	public static double distance(Node linestart, Node lineend, Node point){
		double x1 = linestart.getx(), y1 = linestart.gety(), x2 = lineend.getx(), y2 = lineend.gety();
		double x0 = point.getx(), y0 = point.gety();
		double n = Math.abs(((y2 - y1)*x0) - ((x2 - x1)*y0) + (x2*y1) - (y2*x1));
		double ysq = (y2 - y1)*(y2 - y1), xsq = (x2 - x1)*(x2 - x1);
		double d = (float)Math.sqrt(ysq + xsq);
		System.out.println("Perpendicular dist: "+ (n/d));
		return n/d;
	}
	
	public static void deleteNodes(Node st, Node en){
		Node s = st;
		while(s.getLink()!=en){
			Node p = s.getLink();
			s.setLink(p.getLink());
			p.setLink(null);
		}
	}
	
	public static void DouglasPeucker(Node start, Node end, double epsilon){
		double dmax = 0;
		Node p = start, maxnode = null;
		p = p.getLink();
		if(p == end||p == null){
			System.out.println("p==end");
			display();
			System.out.println("end");
			return;
		}
		else{
			while(p!=end){
				double d = distance(start, end, p); //distance between line and point
				if(d > dmax){
					maxnode = p;
					dmax = d;
				}
				p = p.getLink();
			}
			System.out.println("dmax: "+dmax);
			if(dmax > epsilon){
				DouglasPeucker(start, maxnode, epsilon); //divide and conquer
				DouglasPeucker(maxnode, end, epsilon);
			}
			else{
				deleteNodes(start, end);
				display();
			}
		}
	}
	
	public static void createChart(String s){
		XYSeries series = new XYSeries("XYGraph");
		Node p = head;
		while(p!=null){
			series.add(p.getx(), p.gety());
			p = p.getLink();
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		JFreeChart chart = ChartFactory.createXYLineChart(
		"Douglas Peucker", // Title
		"x-axis", // x-axis Label
		"y-axis", // y-axis Label
		dataset, // Dataset
		PlotOrientation.VERTICAL, // Plot Orientation
		true, // Show Legend
		true, // Use tooltips
		false // Configure chart to generate URLs?
		);
		try {
			ChartUtilities.saveChartAsJPEG(new File(s), chart, 900, 500);
		} catch (IOException e) {
		System.err.println("Problem occurred creating chart.");
		}
	}
	
	public static void main(String[] args){
		//Line x, y co-ordinates
		insertAtEnd(554,93);
		insertAtEnd(532,72);
		insertAtEnd(482,48);
		insertAtEnd(455,45);
		insertAtEnd(409,45);
		insertAtEnd(377,54);
		insertAtEnd(331,78);
		insertAtEnd(316,88);
		insertAtEnd(314,106);
		insertAtEnd(339,139);
		insertAtEnd(392,158);
		insertAtEnd(431,174);
		insertAtEnd(454,181);
		insertAtEnd(491,189);
		insertAtEnd(497,201);
		insertAtEnd(497,211);
		insertAtEnd(481,220);
		insertAtEnd(440,221);
		insertAtEnd(380,222);
		insertAtEnd(346,219);
		insertAtEnd(256,216);
		insertAtEnd(240,214);
		insertAtEnd(183,215);
		insertAtEnd(155,219);
		insertAtEnd(108,238);
		insertAtEnd(93,262);
		insertAtEnd(82,285);
		insertAtEnd(107,304);
		insertAtEnd(143,313);
		insertAtEnd(188,321);
		insertAtEnd(274,337);
		insertAtEnd(341,351);
		insertAtEnd(437,372);
		insertAtEnd(489,380);
		insertAtEnd(435,436);
		insertAtEnd(372,449);
		insertAtEnd(341,461);
		insertAtEnd(307,467);
		insertAtEnd(261,479);
		insertAtEnd(188,488);
		insertAtEnd(141,493);
		insertAtEnd(73,502);

		System.out.println("Line initial points: ");
		display();
		//initial data
		createChart("E:\\chart.jpg");
		
		//call Douglas Peucker with epsilon value
		DouglasPeucker(head, tail, 5.0f);
		System.out.println("\nFinal points: ");
		display();
		
		//data after line simplification
		createChart("E:\\finalchart.jpg");
	}
}