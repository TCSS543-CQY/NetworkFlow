package graphGenerate;

import graph.GraphInput;
import graph.SimpleGraph;

/**
 * For each graph type, generate a range of graphs with different vertices
 * count, degrees, and probabilities.
 * 
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
	 * generate a range of graphs with different choices of parameters:
	 * degrees, MAX_PROBABILITY, capacities
	 * @param size
	 * @param type
	 * @return
	 * @throws Exception 
	 */
	public static SimpleGraph[] ParameterRange(int size, String type) throws Exception{
		SimpleGraph[] graphArray = new SimpleGraph[size];
		GraphInput graphInput = new GraphInput();
		if (size ==0){
			throw new IllegalArgumentException("graph array size cannot be zero");
		}
		
		switch (type.toLowerCase()) {
		// use a range of MAX_PROBABILITY to generate bipartite
		case "bipartite":
			for (int i = 1; i <= size; i++) {
				// create a new simpleGraph class for bipartite
				SimpleGraph bipartite = new SimpleGraph();
				BipartiteGraph bg = new BipartiteGraph();
				double probability = (1/size)*i;
				// generate bipartite and write to a file
				bg.graphGenerator(S_NODE_COUNT, T_NODE_COUNT, MIN_CAPACITY, MAX_CAPACITY, probability, FILENAME);
				// load from file the bipartite to a SimpleGraph instance
				graphInput.LoadSimpleGraph(bipartite, FILENAME);
				graphArray[i - 1] = bipartite;
			}
			System.out.println("bipartite:parameter tuning");
			break;
		case "fixeddegree":
			for (int i = 1; i <= size; i++) {
				SimpleGraph fix = new SimpleGraph();
				FixedDegreeGraph fdg = new FixedDegreeGraph();
				int degree = size;
				fdg.graphGenerator(S_NODE_COUNT*2, degree, MIN_CAPACITY, MAX_CAPACITY, FILENAME);
				graphInput.LoadSimpleGraph(fix, FILENAME);
				graphArray[i - 1] = fix;
			}
			System.out.println("fixed degree:parameter tuning");
			break;
		case "mesh":
			for (int i = 1; i <= size; i++) {
				SimpleGraph mesh = new SimpleGraph();
				int capacity = size;
				String[] args = { String.valueOf(S_NODE_COUNT), String.valueOf(T_NODE_COUNT), String.valueOf(capacity),
						FILENAME };
				MeshGenerator mg = new MeshGenerator(args);
				mg.generate();
				graphInput.LoadSimpleGraph(mesh, FILENAME);
				graphArray[i - 1] = mesh;
			}
			System.out.println("mesh graph:parameter tuning");
			break;

		case "random":
			for (int i = 1; i <= size; i++) {
				SimpleGraph random = new SimpleGraph();
				RandomGraph rg = new RandomGraph();
				double probability = (1/size)*i;
				rg.BuildGraph(FILENAME, ".", S_NODE_COUNT*2, (int) (probability * 100), MIN_CAPACITY, MAX_CAPACITY);
				graphInput.LoadSimpleGraph(random, FILENAME);
				graphArray[i - 1] = random;

			}
			System.out.println("random graph:parameter tuning");
			break;
		default:
			System.out.println("check graph type.");
			break;
		}
		return graphArray;
		
	}
	
	
	/**
	 * generate a range of graphs with different number of vertices
	 * here we use a the size in proportion to the number of graphs to 
	 * be generated: node_count = 20 * size. We use in our main method,
	 * size = 10 to test.
	 * 
	 * @param size
	 *            - number of graphs to be generated
	 * @param type
	 *            - graph type
	 * @return
	 * @throws Exception
	 */
	public static SimpleGraph[] VertexRange(int size, String type) throws Exception {

		SimpleGraph[] graphArray = new SimpleGraph[size];
		GraphInput graphInput = new GraphInput();
		// use a fixed size of vertices count of (size * 20) for every type

		switch (type.toLowerCase()) {

		case "bipartite":
			for (int i = 1; i <= size; i++) {
				// create a new simpleGraph class for bipartite
				SimpleGraph bipartite = new SimpleGraph();
				BipartiteGraph bg = new BipartiteGraph();
				// generate bipartite and write to a file
				bg.graphGenerator(i * 20, i * 20, MIN_CAPACITY, MAX_CAPACITY, MAX_PROBABILITY, FILENAME);
				// load from file the bipartite to a SimpleGraph instance
				graphInput.LoadSimpleGraph(bipartite, FILENAME);
				graphArray[i - 1] = bipartite;
			}
			System.out.println("bipartite:vertex count tuning");
			break;
		case "fixeddegree":
			for (int i = 1; i <= size; i++) {
				SimpleGraph fix = new SimpleGraph();
				FixedDegreeGraph fdg = new FixedDegreeGraph();
				fdg.graphGenerator(i * 20, DEGREE, MIN_CAPACITY, MAX_CAPACITY, FILENAME);
				graphInput.LoadSimpleGraph(fix, FILENAME);
				graphArray[i - 1] = fix;
			}
			System.out.println("fixed degree: vertex count tuning");
			break;
		case "mesh":
			for (int i = 1; i <= size; i++) {
				SimpleGraph mesh = new SimpleGraph();
				// parameters: row count, col count, max capacity and filename
				int row_count = i * 20;
				String[] args = { String.valueOf(row_count), String.valueOf(row_count), String.valueOf(MAX_CAPACITY),
						FILENAME };
				MeshGenerator mg = new MeshGenerator(args);
				mg.generate();
				graphInput.LoadSimpleGraph(mesh, FILENAME);
				graphArray[i - 1] = mesh;
			}
			System.out.println("mesh graph: vertex count tuning");
			break;

		case "random":
			for (int i = 1; i <= size; i++) {
				SimpleGraph random = new SimpleGraph();
				RandomGraph rg = new RandomGraph();
				rg.BuildGraph(FILENAME, ".", i * 20, (int) (MAX_PROBABILITY * 100), MIN_CAPACITY, MAX_CAPACITY);
				graphInput.LoadSimpleGraph(random, FILENAME);
				graphArray[i - 1] = random;

			}
			System.out.println("random graph: vertex count tuning");
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
		// RandomGenerator rg = new RandomGenerator();
		// rg.BuildGraph("lowDensity.txt", ".", 100, 5, 1, 50);

		// BipartiteGraph BG = new BipartiteGraph();
		String[] arguments = { String.valueOf(S_NODE_COUNT), String.valueOf(T_NODE_COUNT), String.valueOf(MAX_CAPACITY),
				FILENAME };
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
