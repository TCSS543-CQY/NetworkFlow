package residualgraph;
import java.util.*;
/**
 *
 * @author zijun,weijia
 */
public class ResidualVertex {
    public LinkedList outgoingEdge;

    private double height;              // heights of a node
    private double excess;              // excess of a node
    private final String name;          // name of a node
    private boolean visited;            // check if a node has been visited

    /**
     *  Construct height name, and the excess of a vertex
     */
    public ResidualVertex(Object name)
    {
        this.name = String.valueOf(name);
        outgoingEdge = new LinkedList();
        height = 0;
        excess = 0;
        visited = false;
    }


    public String getName()
    {
        return name;
    }

    public double getHeight()
    {
        return height;
    }

    public double getExcess()
    {
        return excess;
    }

    public void setHeight(double height)
    {
        this.height = height;
    }

    public void setExcess(double excess)
    {
        this.excess = excess;
    }

    /**
     * check if a node has excess
     * @return  false if there are no excess.
     */
    public boolean hasExcess()
    {
        if (this.excess == 0) {
            return false;
        } else {
        return true;}
    }

    public void visit()
    {
        this.visited = true;
    }

    public void unvisit()
    {
        this.visited = false;
    }


    /**
     * check if the node has been visited
     */
    public boolean isVisited()
    {
        return this.visited;
    }


    /**
     * check the unvisited node
     * @return true if there are any
     */
    public boolean hasAdjacentUnivisited()
    {
        for(int i=0; i<this.outgoingEdge.size(); i++)
        {
            ResidualEdge e = (ResidualEdge) this.outgoingEdge.get(i);
            ResidualVertex v = e.getOtherEnd();
            if(!v.isVisited())
            {

                return true;
            }
        }
        return false;
    }


    /**
     * Return adjacent vertex if it has been visited
     */
    public ResidualVertex getAdjacentVertex()
    {
        for(int i=0; i<this.outgoingEdge.size(); i++)
        {
            ResidualEdge e = (ResidualEdge) this.outgoingEdge.get(i);
            ResidualVertex v = e.getOtherEnd();
            if(!v.isVisited())
            {
                return v;
            }
        }
        return null;
    }
}
