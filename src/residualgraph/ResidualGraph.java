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
        return vertex.outgoingEdge.Iterator();
    }


}
