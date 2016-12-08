package residualgraph;

/**
 *
 * @author 
 */
public class ResidualEdge {
    private double capacity;    //the capacity of the edge of residual graph
    private double flow;        //the flow amount of the edge of residual graph
    private ResidualVertex v; //one end of the edge of the residual graph
    private ResidualVertex u; //the other end of the edge of the residual graph

    /**
     * allows you to setup the vertex v, w, and the edge capacity of the residual graph
     * @param v  one end of the edge of the residual graph
     * @param u  the other end of the edge of the residual graph
     * @param capacity capacity of the edge of the residual graph
     */
    public ResidualEdge(ResidualVertex v, ResidualVertex u, double capacity)
    {
        this.flow = 0; //assigning the initial flow value be 0
        this.capacity = capacity;
        this.v = v;
        this.u = u; //assigning the value of the parameter capacity,v,w, to instance variable
    }

    public void setCapacity(double capacity)
    {
        this.capacity = capacity;

    }

    public double getCapacity()
    {
        return capacity;
    }

    public ResidualVertex getEdgeEnd()
    {
        return v;
    }
    public ResidualVertex getOtherEnd()
    {
        return u;
    }

}
