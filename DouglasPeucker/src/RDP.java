import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class RDP{
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
			int n = 1;
			p = p.getLink();
			while(p!=null){
				n++;
				System.out.print(p.getx()+","+p.gety()+" -> ");
				p = p.getLink();
			}
			System.out.println("\nNumber of points: "+n);
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
	
	public static void main(String[] args) throws IOException{
		long startTime = System.currentTimeMillis();
		
		BufferedReader reader = new BufferedReader(new FileReader("input_file_location"));
		String line = null;
		String [] xy = null;
		while((line = reader.readLine()) != null){
			xy = line.split(",");
			System.out.println(xy[0]+" "+xy[1]);
			insertAtEnd(Double.parseDouble(xy[0]),Double.parseDouble(xy[1]));
		}
		//initial data
		System.out.println("Line initial points: ");
		display();
		
		//call Douglas Peucker with epsilon value
		DouglasPeucker(head, tail, 9.5f);
		
		//data after line simplification
		System.out.println("\nFinal points: ");
		display();
		
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		Runtime runtime = Runtime.getRuntime();
        runtime.gc();
		System.out.println("\nTime: "+totalTime);
		System.out.println("Used memory is " + (runtime.totalMemory() - runtime.freeMemory()) + " bytes");
	}
}