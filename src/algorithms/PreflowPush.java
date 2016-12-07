ckage tcss543;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Utility.Utils;
import algorithms.FordFulkerson;
import algorithms.PreflowPush;
import algorithms.ScalingFordFulkerson;
import graph.GraphInput;
import graph.SimpleGraph;
import graphGenerate.graphGenerator;

/**
 * This class is used to test the performance of three algorithms on a range of
 * graphs of each type.
 * 
 * @author weijia 
 * @version Dec.2016
 *
 */
public class PerformanceTest {

	public static Utils ut;
	public static graphGenerator gg;
	private static int SIZE = 3;

	public static void main(String[] args) throws Exception {

		System.out.println("now testing three algorithms on "
				+ "a range of auto-generated graphs");
		performanceTesting();

	}

	/**
	 * run three algorithms on each type of graphs SIZE times, and record running
	 * time
	 * 
	 * @return
	 */
	public static String runAlgorithms(SimpleGraph g) {

		long[] runtimeArray = new long[10];
		long startTime = 0;
		String results = "";
		System.out.println("\n");
		System.out.println("result for running all algorithem 10 times given an input file: ");
		//System.out.println("------------------------------------");

		// FordFulkerson
		double FFMaxflow = 0;
		for (int i = 0; i < 10; i++) {
			startTime = System.currentTimeMillis();
			FFMaxflow = FordFulkerson.FordFulkerson(g);
			runtimeArray[i] = System.currentTimeMillis() - startTime;
		}
		System.out.println("The maximum flow" + "by Ford-Fulkerson is " + FFMaxflow);
		System.out.println("The average running time of Ford-Fulkerson is : "
				+ ut.getAverageRunTime(runtimeArray) + " Milliseconds.");
		results = "FordFulkerson Test" + "\t" + ut.getAverageRunTime(runtimeArray);
		results = results + "\n";

		// Run PreflowPush
		double preFlowMaxflow = 0;
		for (int i = 0; i < 10; i++) {
	
			startTime = System.currentTimeMillis();
			preFlowMaxflow = PreflowPush.PreflowPush(g);
			runtimeArray[i] = System.currentTimeMillis() - startTime;

		}
		System.out.println("The maximum flow by Preflow Push is " + preFlowMaxflow);
		System.out.println("The average running time of preflow push is : "
				+ ut.getAverageRunTime(runtimeArray) + " Milliseconds.");
		results = results+"PreflowPush test" + "\t" + ut.getAverageRunTime(runtimeArray);
		results = results + "\n";
		
		// Run scaling FordFulkerson
		 double SFFMaxflow = 0.0;
		 for(int i = 0;i<10;i++){
			 startTime = System.currentTimeMillis();
			 SFFMaxflow = ScalingFordFulkerson.ScalingFordFulkerson(g);
			 runtimeArray[i] = System.currentTimeMillis() - startTime;
		 }
		 System.out.println("The maximum flow of Scaling Ford-Fulkerson algorithm is "+SFFMaxflow);
		 System.out.println("The average running time of scaling Ford-Fulkerson is " + 
		 ut.getAverageRunTime(runtimeArray) + " Milliseconds.");
		 results =results+"Scaling FordFulkerson test" + "\t" + ut.getAverageRunTime(runtimeArray);
		 results = results + "\n";
		 results = results + "Maxflow of this graph is: "+preFlowMaxflow;
		 results = results + "\n";
		try (PrintWriter out = new PrintWriter("run_textfile_results.txt")) {
			out.println(results);
		} catch (IOException e) {
			
			e.printStackTrace();
			System.out.print("error when writing running time results");
		}

		return results;

	}

	/**
	 * use the graphs generated by {@link graphGenerator.java} to test the
	 * performance of each algorithm
	 * @return 
	 * 
	 * @return
	 * @throws Exception
	 */
	public static void performanceTesting() throws Exception {
		long[] runtimeArray = new long[10];
		long startTime = 0;
		String directory = System.getProperty("user.dir");
		// writing results for a test of running time on different vertex count
		String fileName1 = "vertex_range_result.txt";
		// writing results for a test of running time on different parameter
		String fileName2 = "parameter_range_result.txt";
		String vertexrange_result = "";
		String pararange_result = "";
		System.out.println("---------------------------------------------------\n");

		// test three algorithms on graphs, change vertex count
		System.out.print("*****************************************"+"\n");
		System.out.print("test on bipartite with different node count"+"\n");
		vertexrange_result = vertexrange_result + "------------------------"+"\n";
		vertexrange_result = vertexrange_result+"test on bipartite with different node count" + "\n";
		SimpleGraph[] bipartiteG_arr = graphGenerator.VertexRange(SIZE, "bipartite");
		for (int i = 0; i < SIZE; i++) {
			System.out.print("size iterator = "+i+"\n");
			System.out.print("vertext count"+"  \t" +"edge count"+"\n");
			System.out.println(bipartiteG_arr[i].numVertices() + " \t " + bipartiteG_arr[i].numEdges());
			// get average running time on each graph
			vertexrange_result = vertexrange_result+"size iterator = "+i+"\n";
			vertexrange_result = vertexrange_result +"vertext count"+"  \t" +"edge count"+"\n";
			vertexrange_result = vertexrange_result+bipartiteG_arr[i].numVertices() + " \t " + bipartiteG_arr[i].numEdges();
			vertexrange_result =vertexrange_result+"\n";
			String tmp_rlt = runAlgorithms(bipartiteG_arr[i]);
			
			System.out.println(tmp_rlt);
			System.out.println("------------------------------------");
			vertexrange_result = vertexrange_result + tmp_rlt;

		}
		
		System.out.print("*****************************************"+"\n");
		System.out.print("test on fixeddegree with different node count"+"\n");
		vertexrange_result = vertexrange_result + "------------------------"+"\n";
		vertexrange_result = vertexrange_result+"test on fixeddegree with different node count" + "\n";
		SimpleGraph[] fixdegree_arr = graphGenerator.VertexRange(SIZE, "fixeddegree");
		for (int i = 0; i < SIZE; i++) {
			System.out.print("size iterator = "+i+"\n");
			System.out.print("vertext count"+"  \t" +"edge count"+"\n");
			System.out.println(fixdegree_arr[i].numVertices() + " \t " + fixdegree_arr[i].numEdges());
			vertexrange_result = vertexrange_result+"size iterator = "+i+"\n";
			vertexrange_result = vertexrange_result +"vertext count"+"  \t" +"edge count"+"\n";
			vertexrange_result = vertexrange_result+fixdegree_arr[i].numVertices() + " \t " + fixdegree_arr[i].numEdges();
			vertexrange_result = vertexrange_result+"\n";
			String tmp_rlt = runAlgorithms(fixdegree_arr[i]);
			//System.out.print("size iterator = "+i+"\n");
			System.out.println(tmp_rlt);
			System.out.println("------------------------------------");
			vertexrange_result = vertexrange_result + tmp_rlt;

		}
		System.out.print("*****************************************"+"\n");
		System.out.print("test on mesh with different node count"+"\n");
		vertexrange_result = vertexrange_result + "------------------------"+"\n";
		vertexrange_result = vertexrange_result+"test on mesh with different node count" + "\n";
		SimpleGraph[] mesh_arr = graphGenerator.VertexRange(SIZE, "mesh");
		for (int i = 0; i < SIZE; i++) {
			System.out.print("size iterator = "+i+"\n");
			System.out.print("vertext count"+"  \t" +"edge count"+"\n");
			System.out.println(mesh_arr[i].numVertices() + " \t " + mesh_arr[i].numEdges());
			vertexrange_result = vertexrange_result+"size iterator = "+i+"\n";
			vertexrange_result = vertexrange_result +"vertext count"+"  \t" +"edge count"+"\n";
			vertexrange_result = vertexrange_result+mesh_arr[i].numVertices() + " \t " + mesh_arr[i].numEdges();
			vertexrange_result = vertexrange_result+"\n";
			String tmp_rlt = runAlgorithms(mesh_arr[i]);
			//System.out.print("size iterator = "+i+"\n");
			System.out.println(tmp_rlt);
			System.out.println("------------------------------------");
			vertexrange_result = vertexrange_result + tmp_rlt;

		}
		
		System.out.print("*****************************************"+"\n");
		System.out.print("test on random with different node count"+"\n");
		vertexrange_result = vertexrange_result + "------------------------"+"\n";
		vertexrange_result = vertexrange_result+"test on random with different node count" + "\n";
	
		SimpleGraph[] random_arr = graphGenerator.VertexRange(SIZE, "random");
		for (int i = 0; i < SIZE; i++) {
			System.out.print("size iterator = "+i+"\n");
			System.out.print("vertext count"+"  \t" +"edge count"+"\n");
			System.out.println(random_arr[i].numVertices() + " \t " + random_arr[i].numEdges());
			vertexrange_result = vertexrange_result+"size iterator = "+i+"\n";
			vertexrange_result = vertexrange_result +"vertext count"+"  \t" +"edge count"+"\n";
			vertexrange_result = vertexrange_result+random_arr[i].numVertices() + " \t " + random_arr[i].numEdges();
			vertexrange_result = vertexrange_result+"\n";
			String tmp_rlt = runAlgorithms(random_arr[i]);
			//System.out.print("size iterator = "+i+"\n");
			System.out.println(tmp_rlt);
			System.out.println("------------------------------------");
			vertexrange_result = vertexrange_result + tmp_rlt;

		}

		
		
		// test three algorithms on graphs, change parameter
		System.out.print("*****************************************"+"\n");
		System.out.print("test on bipartite with different probablities "+"\n");
		pararange_result = pararange_result + "------------------------"+"\n";
		pararange_result = pararange_result+"test on bipartite with different probablities" + "\n";
	
		SimpleGraph[] bipartiteG_arr2 = graphGenerator.ParameterRange(SIZE, "bipartite");
		for (int i = 0; i < SIZE; i++) {
			System.out.print("size iterator = "+i+"\n");
			System.out.print("vertext count"+"  \t" +"edge count"+"\n");
			System.out.println(bipartiteG_arr2[i].numVertices() + " \t " + bipartiteG_arr2[i].numEdges());
			pararange_result = pararange_result+"size iterator = "+i+"\n";
			pararange_result = pararange_result +"vertext count"+"  \t" +"edge count"+"\n";
			pararange_result = pararange_result+bipartiteG_arr2[i].numVertices() + " \t " + bipartiteG_arr2[i].numEdges();
			pararange_result = pararange_result+"\n";
			String tmp_rlt = runAlgorithms(bipartiteG_arr2[i]);
			//System.out.print("size iterator = "+i+"\n");
			System.out.println(tmp_rlt);
			System.out.println("------------------------------------");
			pararange_result = pararange_result + tmp_rlt;

		}

		System.out.print("*****************************************"+"\n");
		System.out.print("test on fixeddegree with different degrees "+"\n");
		pararange_result = pararange_result + "------------------------"+"\n";
		pararange_result = pararange_result+"test on fixeddegree with different degrees " + "\n";
	
		SimpleGraph[] fixdegree_arr2 = graphGenerator.VertexRange(SIZE, "fixeddegree");
		for (int i = 0; i < SIZE; i++) {
			System.out.print("size iterator = "+i+"\n");
			System.out.print("vertext count"+"  \t" +"edge count"+"\n");
			System.out.println(fixdegree_arr2[i].numVertices() + " \t " + fixdegree_arr2[i].numEdges());
			pararange_result = pararange_result+"size iterator = "+i+"\n";
			pararange_result = pararange_result +"vertext count"+"  \t" +"edge count"+"\n";
			pararange_result = pararange_result+fixdegree_arr2[i].numVertices() + " \t " + fixdegree_arr2[i].numEdges();
			pararange_result = pararange_result+"\n";
			
			String tmp_rlt = runAlgorithms(fixdegree_arr[i]);
			//System.out.print("size iterator = "+i+"\n");
			System.out.println(tmp_rlt);
			System.out.println("------------------------------------");
			pararange_result = pararange_result + tmp_rlt;

		}

		System.out.print("*****************************************"+"\n");
		System.out.print("test on mesh with different capacities "+"\n");
		pararange_result = pararange_result + "------------------------"+"\n";
		pararange_result = pararange_result+"test on mesh with different capacities" + "\n";
	
		SimpleGraph[] mesh_arr2 = graphGenerator.VertexRange(SIZE, "mesh");
		for (int i = 0; i < SIZE; i++) {
			System.out.print("size iterator = "+i+"\n");
			System.out.print("vertext count"+"  \t" +"edge count"+"\n");
			System.out.println(mesh_arr2[i].numVertices() + " \t " + mesh_arr2[i].numEdges());
			pararange_result = pararange_result+"size iterator = "+i+"\n";
			pararange_result = pararange_result +"vertext count"+"  \t" +"edge count"+"\n";
			pararange_result = pararange_result+mesh_arr2[i].numVertices() + " \t " + mesh_arr2[i].numEdges();
			pararange_result = pararange_result+"\n";
			String tmp_rlt = runAlgorithms(mesh_arr[i]);
			//System.out.print("size iterator = "+i+"\n");
			System.out.println(tmp_rlt);
			System.out.println("------------------------------------");
			pararange_result = pararange_result + tmp_rlt;

		}
		
		System.out.print("*****************************************"+"\n");
		System.out.print("test on random with different denses "+"\n");
		pararange_result = pararange_result + "------------------------"+"\n";
		pararange_result = pararange_result+"test on random with different denses" + "\n";
	
		SimpleGraph[] random_arr2 = graphGenerator.VertexRange(SIZE, "random");
		for (int i = 0; i < SIZE; i++) {
			System.out.print("vertext count"+"  \t" +"edge count"+"\n");
			System.out.println(random_arr2[i].numVertices() + " \t " + random_arr2[i].numEdges());
			pararange_result = pararange_result+"size iterator = "+i+"\n";
			pararange_result = pararange_result +"vertext count"+"  \t" +"edge count"+"\n";
			pararange_result = pararange_result+random_arr2[i].numVertices() + " \t " + random_arr2[i].numEdges();
			pararange_result = pararange_result+"\n";
			String tmp_rlt = runAlgorithms(random_arr[i]);
			System.out.print("size iterator = "+i+"\n");
			System.out.println(tmp_rlt);
			System.out.println("------------------------------------");
			pararange_result = pararange_result + tmp_rlt;

		}

		try (PrintWriter out = new PrintWriter(fileName1)) {
			out.println(vertexrange_result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("error when writing test performance results");
		}

		try (PrintWriter out = new PrintWriter(fileName2)) {
			out.println(pararange_result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("error when writing test performance results");
		}
	
	}

}

