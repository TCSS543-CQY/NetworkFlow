
package algorithms;

import graph.Edge;
import graph.SimpleGraph;
import graph.Vertex;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import residualgraph.ResidualEdge;
import residualgraph.ResidualGraph;
import residualgraph.ResidualVertex;

/**
 *
 * @author zijun,weijia,annie 
 */
public class PreflowPush 
{
    private static ResidualGraph gResidual;
    static double maxFlow;
    
    /**
     *   find the max-flow of a graph g
     */
    public static double PreflowPush(SimpleGraph g)
    {
        gResidual = new ResidualGraph();
    	maxFlow = 0;
        calculateMaxFlow(g);
        return maxFlow;
    }
    
    /**
     *  Calculate max-flow of a given graph g
     */
    private static void calculateMaxFlow(SimpleGraph g)
    {
        initializeResidualGraph(g);
        ResidualVertex v;
        while((v = gResidual.getActiveNode()) != null)
        {
            
            ResidualEdge e = getMinimumHeightAdjacentVertex(v);
            if(e != null)
            {
            	push(v,e);
            }
            else
            {
            	relabel(v);
            }
            
        }
        
        maxFlow = gResidual.getExcessSink();
    }
    private static ResidualEdge getMinimumHeightAdjacentVertex(ResidualVertex v)
    {
        LinkedList edges = v.outgoingEdge;
        ResidualVertex w;
        ResidualEdge fe = null;
        double minimumHeight = v.getHeight();
        for (Object edge : edges) 
        {
            ResidualEdge e = (ResidualEdge) edge;
            w = e.getOtherEnd();
            if(minimumHeight > w.getHeight())
            {
                minimumHeight = w.getHeight();
                fe = e;
            }
        }
        return fe;
    }
    
    /**
     *  Push flow between from a vertex along an edge
     */
    private static void push(ResidualVertex v, ResidualEdge forward)
    {
        ResidualVertex w = forward.getOtherEnd();
        double excessAvailable = v.getExcess();//77
        double canPush = forward.getCapacity();//40
        double pushValue = (excessAvailable < canPush) ? excessAvailable : canPush;//40
        if(pushValue == canPush)
        {
            gResidual.removeEdge(forward);
        }
        else
        {
            forward.setCapacity(forward.getCapacity() - pushValue);
            gResidual.newEdge(forward);
        }
        v.setExcess(v.getExcess() - pushValue);
        gResidual.newVertex(v);
       ResidualEdge backward = gResidual.getEdge(w, v);
        if(backward == null)
        {
            backward = new ResidualEdge(w,v,pushValue);
            gResidual.insertEdge(w, v, backward);
        }
        else
        {
            backward.setCapacity(backward.getCapacity() + pushValue);
            gResidual.newEdge(backward);
        }
        
        w.setExcess(w.getExcess() + pushValue);
        gResidual.newVertex(w);
       
        
    }
    
    /**
     *  Relabel height of a vertex.
     */
    private  static void relabel(ResidualVertex v)
    {   
        LinkedList outgoingEdges = v.outgoingEdge;
        double minimumHeight = v.getHeight();
        for(int i=0; i<outgoingEdges.size();i++)
        {
            ResidualEdge e = (ResidualEdge) outgoingEdges.get(i);
            ResidualVertex w = e.getOtherEnd();
            if(minimumHeight > w.getHeight())
            {
                minimumHeight = w.getHeight();
            }
        }
        
        v.setHeight(minimumHeight + 1);
        gResidual.newVertex(v);
    }
    
    /**
     *  Initialise the residual graph using a given graph g
     *  Initialise all vertex heights to be 0 except source. Source height is n. All vertex excess is 0.
     *  Initialise edges to have flow 0 and given capacity
     */
    private static void initializeResidualGraph(SimpleGraph g)
    {
        Iterator vertices = g.vertices();
        HashMap<String,ResidualVertex> vertexList = new HashMap<String,ResidualVertex>();
        int noOfVertices = g.numVertices();
        while(vertices.hasNext())
        {
            Vertex v = (Vertex) vertices.next();
            ResidualVertex rv = new ResidualVertex(v.getName());
            vertexList.put(String.valueOf(rv.getName()), rv);
            if(rv.getName().equalsIgnoreCase("s"))
            {
                
                rv.setHeight(noOfVertices);
                }
            else
            {
                rv.setHeight(0);
            }
            gResidual.insertVertex(rv);
        }
        Iterator edges = g.edges();
        while(edges.hasNext())
        {
            Edge e = (Edge) edges.next();
            Vertex v = e.getFirstEndpoint();
            Vertex w = e.getSecondEndpoint();
            ResidualVertex rv = vertexList.get(v.getName());
            ResidualVertex rw = vertexList.get(w.getName());
            double edgeCapacity = (double) e.getData();
            ResidualEdge edge;
            if(rv.getName().equalsIgnoreCase("s"))
            {
                edge = new ResidualEdge(rw, rv, edgeCapacity);
                rw.setExcess(edge.getCapacity());
                gResidual.newVertex(rw);
                gResidual.insertEdge(rw, rv, edge);
            }
            else
            {
                edge = new ResidualEdge(rv, rw, edgeCapacity);
                gResidual.insertEdge(rv, rw, edge);
            }
        }
    }
}
