class Node
{
    protected double x;
    protected double y;
    protected Node link;
 
    public Node()
    {
        link = null;
        x = 0;
        y = 0;
    }    
    public Node(double x1,double y1,Node n)
    {
        x = x1;
        y = y1;
        link = n;
    }    
    public void setLink(Node n)
    {
        link = n;
    }    
    public void setData(double x1, double y1)
    {
        x = x1;
        y = y1;
    }    
    public Node getLink()
    {
        return link;
    }
    public double getx()
    {
        return x;
    }
    public double gety(){
    	return y;
    }
}