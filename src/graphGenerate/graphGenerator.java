package graphGenerate;

import graph.GraphInput;
import graph.SimpleGraph;

/**
 * For each graph type, generate a range of graphs with different vertices count,
 * degrees, and probabilities. 
 * @author yanan, weijia chen, zijun qu
 * @version Dec, 2016
 *
 */
public class graphGenerator {
	// temporary file path of generated graphs
	private static final String FILENAME = "generatedGraph.txt";
	// number of nodes on the source side
	private static int S_NODE_COUNT = 200;
	// number of node on the sink side
	private static int T_NODE_COUNT = 200;
	private static int MIN_CAPACITY = 1;
	private static int MAX_CAPACITY = 10;
	// for fixed degree, degree means the number of edges out of each node
	private static int DEGREE = 5;
	private static double MAX_PROBABILITY = 1;
	
	
	
	  /**
     * generate graphs with different number of vertices
     * @param size - number of graphs to be generated
     * @param type - graph type
     * @return
	 * @throws Exception 
     */
    public static SimpleGraph[] GraphsNumNodes(int size, String type) throws Exception {
    	
    	SimpleGraph[] graphArray = new SimpleGraph[size]; 
        GraphInput graphInput = new GraphInput();
        // use a fixed size of vertices count of (size * 10) for every type
        
        switch (type.toLowerCase()){
        
		case "bipartite":		
			for(int i =1;i<=size;i++){
				// create a new simpleGraph class for bipartite
				SimpleGraph bipartite = new SimpleGraph();
		    	BipartiteGraph bg = new BipartiteGraph();
		    	// generate bipartite and write to a file
				bg.graphGenerator(i*10, i*10, MIN_CAPACITY, MAX_CAPACITY, MAX_PROBABILITY, FILENAME);
				// load from file the bipartite to a SimpleGraph instance
				graphInput.LoadSimpleGraph(bipartite,FILENAME);
				graphArray[i-1] = bipartite;
			}
			System.out.println("bipartite");
			break;
		case "fixeddegree":
			for(int i =1;i<=size;i++){
				SimpleGraph fix = new SimpleGraph();
				FixedDegreeGraph fdg = new FixedDegreeGraph();
				fdg.graphGenerator(i*10, DEGREE, MIN_CAPACITY, MAX_CAPACITY, FILENAME);
				graphInput.LoadSimpleGraph(fix,FILENAME);
				graphArray[i-1] = fix;
			}
			System.out.println("fixed degree");
			break;
		case "mesh":
			for(int i =1;i<=size;i++){
				SimpleGraph mesh = new SimpleGraph();
				// parameters: row count, col count, max capacity and filename
				int row_count = i*10;
				String[] args = {String.valueOf(row_count),String.valueOf(row_count),String.valueOf(MAX_CAPACITY),FILENAME};
				MeshGenerator mg = new MeshGenerator(args);
				mg.generate();
				graphInput.LoadSimpleGraph(mesh,FILENAME);
				graphArray[i-1] = mesh;
			}
			System.out.println("mesh graph");
			break;

		case "random":
			for(int i =1;i<=size;i++){
				SimpleGraph random = new SimpleGraph();
				RandomGraph rg = new RandomGraph();
				rg.BuildGraph(FILENAME, ".", i*10, (int) (MAX_PROBABILITY*100), MIN_CAPACITY, MAX_CAPACITY);
				graphInput.LoadSimpleGraph(random,FILENAME);								
				graphArray[i-1] = random;
				
			}
			System.out.println("random graph");
			break;
		default:
			System.out.println("check graph type.");
			break;
		}
    	return graphArray;
    }
	
    
    // test with bipartite graph
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//    RandomGenerator rg = new RandomGenerator();
//    rg.BuildGraph("lowDensity.txt", ".", 100, 5, 1, 50);
	
	
		
//	BipartiteGraph BG = new BipartiteGraph();
		String[] arguments = {String.valueOf(S_NODE_COUNT),
				String.valueOf(T_NODE_COUNT),String.valueOf(MAX_CAPACITY),FILENAME};
		MeshGenerator MG = new MeshGenerator(arguments);
	try {
		MG.generate();
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.print("error generating graph");
	}
	
	}

}
