package residualgraph;
import java.util.*;
/**
 * @author zijun,weijia,annie
 */
public class ResidualGraph {
    LinkedList vertexList;
    LinkedList edgeList;


    /**
     * construct list of vertices and edges of residual graph
     */
    public ResidualGraph()
    {
       vertexList = new LinkedList();
        edgeList = new LinkedList();
    }
    public Iterator vertices() {
        return vertexList.iterator();
    }
    public Iterator edges() {
        return edgeList.iterator();
    }
    public Iterator outgoingEdges(ResidualVertex vertex) {
        return vertex.outgoingEdge.iterator();
    }

    /**
     * insert a vertex
     * @param aV the vertex
     */

    public void insertVertex(ResidualVertex aV)
    {
        vertexList.addLast(aV);
    }

    /**
     * insert an edge to the Edge List
     * @param v one end of the edge
     * @param u the other end of the edge
     * @param e the edge
     */

    public void insertEdge(ResidualVertex v, ResidualVertex u, ResidualEdge e)
    {
        this.edgeList.addLast(e);
        v.outgoingEdge.addLast(e);
    }

    /**
     * Return an arbitrary vertex
     * @return  some vertex of this graph
     */
    public ResidualVertex aVertex()
    {
        if (vertexList.size() > 0)
            return (ResidualVertex) vertexList.getFirst();
        else
            return null;
    }


    public void newEdge(ResidualEdge e)
    {
        int index = edgeList.indexOf(e);
        edgeList.remove(index);
        edgeList.addLast(e);
    }

    public void newVertex(ResidualVertex v)
    {
        int index = vertexList.indexOf(v);
        vertexList.remove(index);
        vertexList.addLast(v);
    }

    /**
     * Given a vertex and an edge, if the vertex is one of the endpoints
     * of the edge, return the other endpoint of the edge.
     * @param v  vertex
     * @param e  edge
     */
    public ResidualVertex opposite(ResidualVertex v, ResidualEdge e)
    {
        ResidualVertex w;

        if (e.getEdgeEnd() == v) {
            w = e.getOtherEnd();
        }
        else if (e.getOtherEnd() == v) {
            w = e.getEdgeEnd();
        }
        else
            w = null;

        return w;
    }


    public int numVertices()
    {
        return vertexList.size();
    }


    public int numEdges()
    {
        return edgeList.size();
    }



    /**
     * Returns active node with excess from residual graph
     * @return  an active node
     */
    public ResidualVertex getActiveNode()
    {
        for (int i = 0; i<vertexList.size(); i++)
        {
            ResidualVertex v = (ResidualVertex) vertexList.get(i);
            if(!v.getName().equalsIgnoreCase("s") && !v.getName().equalsIgnoreCase("t") && v.getExcess() > 0)
            {
                return v;
            }
        }
        return null;
    }


    /**
     * Computes the edge between 2 vertices
     * @return  the edge between two vertices
     */
    public ResidualEdge getEdge(ResidualVertex startNode, ResidualVertex endNode)
    {
        ResidualEdge e;
        Iterator itr = this.outgoingEdges(startNode);
        while(itr.hasNext())
        {
            e = (ResidualEdge) itr.next();
            if(e.getOtherEnd().getName().equalsIgnoreCase(endNode.getName()))
            {
                return e;
            }
        }
        return null;
    }

    public ResidualVertex getSource()
    {
        LinkedList vertices = this.vertexList;
        ResidualVertex s;
        for(int i =0; i<vertices.size(); i++)
        {
            s = (ResidualVertex) vertices.get(i);
            if(s.getName().equalsIgnoreCase("s"))
            {
                return s;
            }
        }
        return null;
    }


    public void removeEdge(ResidualEdge e)
    {
        this.edgeList.remove(e);
        ResidualVertex v =  e.getEdgeEnd();
        v.outgoingEdge.remove(e);
        this.newVertex(v);
    }

    /**
     * Get excess at sink
     * @return  the value of excess at the sink
     */
    public double getExcessSink()
    {
        LinkedList vertices = this.vertexList;
        double maxFlow = 0;
        for(int i=0; i<vertices.size(); i++)
        {
            ResidualVertex v = (ResidualVertex) vertices.get(i);
            if(v.getName().equalsIgnoreCase("t"))
            {
                maxFlow =  v.getExcess();
            }
        }
        return maxFlow;
    }


    /**
     * Gets s-t path
     * @return  path from s to t
     */
    public LinkedList<ResidualVertex> depthFirstSearch(ResidualVertex v, LinkedList<ResidualVertex> path)
    {
        if(!v.isVisited())
        {
            v.visit();
            path.addLast(v);
            this.newVertex(v);
        }

        if(v.hasAdjacentUnivisited())
        {
            ResidualVertex w = v.getAdjacentVertex();

            if(!w.getName().equalsIgnoreCase("t"))
            {
                this.depthFirstSearch(w,path);
            }
            else
            {
                path.addLast(w);
            }
        }
        else
        {
            path.remove(v);

            if(!path.isEmpty())
            {
                v = (ResidualVertex) path.getLast();
                this.depthFirstSearch(v,path);
            }
        }

        return path;
    }

}