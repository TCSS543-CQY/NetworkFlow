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
public final class ScalingFordFulkerson
{
	public static double maxFlow;
	public static ResidualGraph gResidual;
	public static double ScalingFordFulkerson(SimpleGraph simpleG)
	{
		gResidual = new ResidualGraph();
		addVerticesToResidual(simpleG);
		maxFlow = 0;
		double maxSource = findMaxSourceCapacity(simpleG);
		int delta = calculateDelta(maxSource);
		while(delta >= 1)
		{
			addEdgesToResidual(simpleG,delta);
			LinkedList<ResidualVertex> currentPath;
			while(!(currentPath = findPath()).isEmpty())
			{
				double bottleneck = findminCapacity(currentPath);
				pushFlow(currentPath, bottleneck);
				maxFlow += bottleneck;
			}
			delta = delta / 2;
		}
		return maxFlow;
	}
	private static void pushFlow(LinkedList<ResidualVertex> stPath, double minCapacity)
	{
		for(int i=0; i<stPath.size()-1; i++)
		{
			ResidualVertex rv = stPath.get(i);
			ResidualVertex rw = stPath.get(i+1);
			ResidualEdge forward = gResidual.getEdge(rv, rw);
			if(forward.getCapacity() == minCapacity)
			{
				gResidual.removeEdge(forward);
			}
			else
			{
				forward.setCapacity(forward.getCapacity() - minCapacity);
				gResidual.newEdge(forward);
			}

			ResidualEdge backward = gResidual.getEdge(rw, rv);
			if(backward == null)
			{
				backward = new ResidualEdge(rw,rv,minCapacity);
				gResidual.insertEdge(rw, rv, backward);
			}
			else
			{
				backward.setCapacity(backward.getCapacity() + minCapacity);
				gResidual.newEdge(backward);
			}
		}
	}

	private static double findminCapacity(LinkedList<ResidualVertex> path)
	{
		double minCapacity = 100000;
		for(int i=0; i<path.size()-1; i++)
		{
			ResidualVertex rv = path.get(i);
			ResidualVertex rw = path.get(i+1);
			ResidualEdge re = gResidual.getEdge(rv, rw);
			double canPush = re.getCapacity();
			if(minCapacity >= canPush)
			{
				minCapacity = canPush;
			}
		}
		return minCapacity;
	}

	/** define the minCapactiy as the minimum capacity of edges in path
	 * @return the minCapacity of the edges
	 */
	public static void newVertexVisited()
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
	private static void addEdgesToResidual(SimpleGraph g, int delta)
	{
		Iterator edges = g.edges();
		while(edges.hasNext())
		{
			Edge e = (Edge) edges.next();
			if((double)e.getData() >= delta && (double)e.getData() < delta*2)
			{
				ResidualVertex rv = null;
				ResidualVertex rw = null;
				Vertex fVertex = e.getFirstEndpoint();
				Vertex sVertex = e.getSecondEndpoint();
				Iterator vertices = gResidual.vertices();
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
//                System.out.println(re.getCapacity());

				//System.out.println(gResidual.getEdge(rv, rw).getCapacity());
			}
		}
	}
	private static int calculateDelta(double maxSource)
	{
		int i = 1;
		for(; i<maxSource; i=i*2){}
		return i;
	}
	private static double findMaxSourceCapacity(SimpleGraph g)
	{
		Iterator vertices = g.vertices();
		double maximum = 0;
		while(vertices.hasNext())
		{
			Vertex v = (Vertex) vertices.next();
			if(v.getName().toString().equalsIgnoreCase("s"))
			{
				LinkedList edges = v.incidentEdgeList;
				for(int i = 0; i<edges.size(); i++)
				{
					Edge e = (Edge) edges.get(i);
					if(maximum < (double) e.getData())
					{
						maximum = (double) e.getData();
					}
				}
			}
		}
		return maximum;
	}
	private static void addVerticesToResidual(SimpleGraph simpleG)
	{
		// TODO Auto-generated method stub
		Iterator vertices = simpleG.vertices();
		while(vertices.hasNext())
		{
			Vertex v = (Vertex) vertices.next();
			ResidualVertex rv = new ResidualVertex(v.getName());
			gResidual.insertVertex(rv);
		}
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
		newVertexVisited();
		s.unvisit();
		gResidual.newVertex(s);
		return path;
	}


}


