package residualgraph;

/**
 *
 * @author zijun,weijia,annie
 */
public class ResidualEdge {
    private double capacity;    //the capacity of the edge of residual graph
    private double flow;        //the flow amount of the edge of residual graph
    private ResidualVertex v; //one end of the edge of the residual graph
    private ResidualVertex u; //the other end of the edge of the residual graph

    /**
     * allows you to setup the vertex v, w, and the edge capacity of the residual graph
     * @param v  one end of the edge of the residual graph
     * @param w  the other end of the edge of the residual graph
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

    public void setFlow(double flow)
    {
        this.flow = flow;
    }

    public double getFlow()
    {
        return flow;
    }
    public ResidualVertex getEdgeEnd()
    {
        return v;
    }
    public ResidualVertex getOtherEnd()
    {
        return u;
    }

    /**
     * check if the edge is filled
     * @return true if edge is filled
     */
    public boolean ifFilled()
    {
        if (capacity == flow) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * check how much capacity room left of the edge
     * @return
     */
    public double capacityLeft() {
        return getCapacity() - getFlow();
    }
}
