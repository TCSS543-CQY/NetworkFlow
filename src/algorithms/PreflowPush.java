package algorithms;


import java.util.HashMap;
import java.util.Iterator;

import graph.Edge;
import graph.SimpleGraph;
import graph.Vertex;

import java.util.LinkedList;

import residualgraph.ResidualEdge;
import residualgraph.ResidualGraph;
import residualgraph.ResidualVertex;

/**
 * the PreflowPush class implements the PreoflowPush algorithm
 * @author weijia
 */
public class PreflowPush {

    private static ResidualGraph shengyutu;

    static double maxLiu;
    
    /**
     *   find the max-flow of a graph g
     */
    public static double PreflowPush(SimpleGraph g)
    {
        shengyutu = new ResidualGraph();
    	maxLiu = 0;
    	maxLiu = calculatemaxLiu(g);
        return maxLiu;
    }
    
    /**
     *  Initialise the residual graph
    */
    private static HashMap<String,ResidualVertex> initializeResidualGraph(SimpleGraph g)
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
            shengyutu.insertVertex(rv);
        }
        
        return vertexList;
        
    }
    private static void initializeResidualGraphEdge(SimpleGraph g,HashMap<String,ResidualVertex> vertexList){
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
                shengyutu.newVertex(rw);
                shengyutu.insertEdge(rw, rv, edge);
            }
            else
            {
                edge = new ResidualEdge(rv, rw, edgeCapacity);
                shengyutu.insertEdge(rv, rw, edge);
            }
        }
    }



    /**
     *  Calculate max-flow 
     */


    private static double calculatemaxLiu(SimpleGraph g)
    {
    	HashMap<String,ResidualVertex> vertexList=initializeResidualGraph(g);
        initializeResidualGraphEdge(g,vertexList);
        ResidualVertex v;
        while((v = shengyutu.getActiveNode()) != null)
        {
            
            ResidualEdge e = getMinHeightAdjacentVertex(v);
            if(e != null)
            {
            	push(v,e);
            }
            else
            {
            	relabel(v);
            }
            
        }
        
        
        return shengyutu.getExcessSink();
    }
    
    
    private static ResidualEdge getMinHeightAdjacentVertex(ResidualVertex v)
    {
        LinkedList<ResidualEdge> edges = v.outgoingEdge;
        ResidualVertex w;
        ResidualEdge fe = null;
        double minHeight = v.getHeight();
        for (ResidualEdge edge : edges) 
        {
            ResidualEdge e = edge;
            w = e.getOtherEnd();
            if(minHeight > w.getHeight())
            {
                minHeight = w.getHeight();
                fe = e;
            }
        }
        return fe;
    }
    
    /**
     *  Push flow
     */
    private static void push(ResidualVertex v, ResidualEdge forward)
    {
        ResidualVertex w = forward.getOtherEnd();
        double excessAvailable = v.getExcess();//77
        double canPush = forward.getCapacity();//40
        double pushValue = (excessAvailable < canPush) ? excessAvailable : canPush;//40
        if(pushValue == canPush)
        {
            shengyutu.removeEdge(forward);
        }
        else
        {
            forward.setCapacity(forward.getCapacity() - pushValue);
            shengyutu.newEdge(forward);
        }
        v.setExcess(v.getExcess() - pushValue);
        shengyutu.newVertex(v);
       ResidualEdge backward = shengyutu.getEdge(w, v);
        if(backward == null)
        {
            backward = new ResidualEdge(w,v,pushValue);
            shengyutu.insertEdge(w, v, backward);
        }
        else
        {
            backward.setCapacity(backward.getCapacity() + pushValue);
            shengyutu.newEdge(backward);
        }
        
        w.setExcess(w.getExcess() + pushValue);
        shengyutu.newVertex(w);
       
        
    }
    
    /**
     *  Relabel height
     */
    private  static void relabel(ResidualVertex v)
    {   
        LinkedList<ResidualEdge> outgoingEdges = v.outgoingEdge;
        double minHeight = v.getHeight();
        for(int i=0; i<outgoingEdges.size();i++)
        {
            ResidualEdge e =  outgoingEdges.get(i);
            ResidualVertex w = e.getOtherEnd();
            if(minHeight > w.getHeight())
            {
                minHeight = w.getHeight();
            }
        }
        
        v.setHeight(minHeight + 1);
        shengyutu.newVertex(v);
    }
}

    

