package algorithms;
import graph.Edge;
import graph.Vertex;
import graph.SimpleGraph;
import java.util.LinkedList;
import java.util.Iterator;
import residualgraph.ResidualEdge;
import residualgraph.ResidualGraph;
import residualgraph.ResidualVertex;

/**
 * @author annie,zijun,weijia
 */
public final class FordFulkerson
{
	public static double maxFlow;
	public static ResidualGraph gResidual;
	public static double FordFulkerson(SimpleGraph simpleG)
	{
		gResidual = new ResidualGraph();
		initializeResidualGraph(simpleG);
		maxFlow = 0;
		LinkedList<ResidualVertex> currentPath;
		while(!(currentPath = findPath()).isEmpty())
		{
			double bottleneck = findBottleneck(currentPath);

			pushFlow(currentPath, bottleneck);
			maxFlow += bottleneck;

		}
		return maxFlow;
	}
	private static void pushFlow(LinkedList<ResidualVertex> stPath, double bottleneck)
	{
		for(int i=0; i<stPath.size()-1; i++)
		{
			ResidualVertex rv = stPath.get(i);
			ResidualVertex rw = stPath.get(i+1);
			ResidualEdge forward = gResidual.getEdge(rv, rw);
			if(forward.getCapacity() == bottleneck)
			{
				gResidual.removeEdge(forward);
			}
			else
			{
				forward.setCapacity(forward.getCapacity() - bottleneck);
				gResidual.newEdge(forward);
			}

			ResidualEdge backward = gResidual.getEdge(rw, rv);
			if(backward == null)
			{
				backward = new ResidualEdge(rw,rv,bottleneck);
				gResidual.insertEdge(rw, rv, backward);
			}
			else
			{
				backward.setCapacity(backward.getCapacity() + bottleneck);
				gResidual.newEdge(backward);
			}
		}
	}
	private static double findBottleneck(LinkedList<ResidualVertex> path)
	{
		double bottleneck = 100000;
		for(int i=0; i<path.size()-1; i++)
		{
			ResidualVertex rv = path.get(i);
			ResidualVertex rw = path.get(i+1);
			ResidualEdge re = gResidual.getEdge(rv, rw);
			double canPush = re.getCapacity();
			if(bottleneck >= canPush)
			{
				bottleneck = canPush;
			}
		}
		return bottleneck;
	}

	private static LinkedList<ResidualVertex> findPath()
	{
		LinkedList<ResidualVertex> path = new LinkedList<ResidualVertex>();
		ResidualVertex s = gResidual.getSource();
		path = gResidual.depthFirstSearch(s, path);
		if(!path.isEmpty())
		{
			for(int i=0; i<path.size();i++)
			{
			}
		}
		else
		{
			path.clear();
		}
		updateVertexVisited();
		s.unvisit();
		gResidual.newVertex(s);
		return path;
	}

	private static void initializeResidualGraph(SimpleGraph g)
	{
		Iterator vertices = g.vertices();
		while(vertices.hasNext())
		{
			Vertex v = (Vertex) vertices.next();
			ResidualVertex rv = new ResidualVertex(v.getName());
			gResidual.insertVertex(rv);
		}
		Iterator edges = g.edges();
		while(edges.hasNext())
		{
			Edge e = (Edge) edges.next();
			ResidualVertex rv = null;
			ResidualVertex rw = null;
			Vertex fVertex = e.getFirstEndpoint();
			Vertex sVertex = e.getSecondEndpoint();
			vertices = gResidual.vertices();
			while(vertices.hasNext())
			{
				ResidualVertex v= (ResidualVertex) vertices.next();
				if(v.getName().equalsIgnoreCase(fVertex.getName().toString()))
				{
					rv=v;
				}
				if(v.getName().equalsIgnoreCase(sVertex.getName().toString()))
				{
					rw=v;
				}
			}
			double edgeCapacity = (double) e.getData();
			ResidualEdge re = new ResidualEdge(rv,rw, edgeCapacity);
			gResidual.insertEdge(rv, rw, re);
		}
	}

	public static void updateVertexVisited()
	{
		Iterator i;
		for (i= gResidual.vertices(); i.hasNext(); )
		{
			ResidualVertex v = (ResidualVertex) i.next();
			if(v.isVisited())
			{
				v.unvisit();
			}
		}
	}
}


