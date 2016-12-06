package tcss543;

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
 * This class is used to test the performance of three algorithms
 * on a range of graphs of each type.
 * @author yanan
 * @version Dec.2016
 *
 */
public class PerformanceTest {
	
	public static Utils ut;
	public static graphGenerator gg;
	private static int SIZE = 10;


	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		  SimpleGraph simpleG;
//          simpleG= new SimpleGraph();
//        
//        	  GraphInput.LoadSimpleGraph(simpleG, args[0]);
        	  System.out.print(performanceTesting());
        
	}
	
	
	/**
	 * run three algorithms on each type of graphs 10 times, and record running time
	 * @return
	 */
	public static String runAlgorithms (SimpleGraph g){
			
		  	long[] runtimeArray = new long[10];
	        long startTime = 0;
	        String results = null;
	        System.out.println("result for running all algorithem 10 times given an input file: ");
	        System.out.println("\n");
	        System.out.println("------------------------------------");
	        
	        //Ford-Fulkerson
	    	double FFMaxflow = 0;
	        for(int i = 0;i<10;i++){ 
	        	startTime = System.currentTimeMillis();
	        	FFMaxflow = FordFulkerson.FordFulkerson(g);
	        	runtimeArray[i] = System.currentTimeMillis() - startTime;
	        }
	        System.out.println("The maximum flow of the input Graph calculated "
	        		+ "by Ford-Fulkerson algorithm is "+FFMaxflow);
	        System.out.println("The average running time of 10 preflow Ford-Fulkerson "
	        		+ "algorithm is : " + ut.getAverageRunTime(runtimeArray) + " Milliseconds.");
	        results = results+","+String.valueOf(ut.getAverageRunTime(runtimeArray));
	        
	        
	        //Run Preflow Push
	        double preFlowMaxflow = 0;
	        for(int i = 0;i<10;i++){   
//	        	System.out.println(i+"th time for preFlow push");
	        	startTime = System.currentTimeMillis();
	        	preFlowMaxflow = PreflowPush.PreflowPush(g);
	        	runtimeArray[i] = System.currentTimeMillis() - startTime;
	            
	        } 
	        System.out.println("The maximum flow of the input Graph "
	        		+ "calculated by Preflow Push is "+preFlowMaxflow);
	        System.out.println("The average running time of 10 "
	        		+ "preflow push algorithm runs is : " + ut.getAverageRunTime(runtimeArray) + " Milliseconds.");
	        results = results+","+String.valueOf(ut.getAverageRunTime(runtimeArray));
	        
	        

	        //Run scaling Ford-Fulkerson
	        double SFFMaxflow = 0.0;
	        for(int i = 0;i<10;i++){      	
	        	startTime = System.currentTimeMillis();
	        	SFFMaxflow = ScalingFordFulkerson.ScalingFordFulkerson(g);
	        	runtimeArray[i] = System.currentTimeMillis() - startTime;
	        }   
	        System.out.println("The maximum flow of the input Graph calculated by scaling "
	        		+ "Ford-Fulkerson algorithm is "+SFFMaxflow);
	        System.out.println("The average running time of 10 scaling Ford-Fulkerson "
	        		+ "algorithm is : " + ut.getAverageRunTime(runtimeArray) + " Milliseconds.");
	        results = results+","+String.valueOf(ut.getAverageRunTime(runtimeArray));
	        
	        try(  PrintWriter out = new PrintWriter( "run_textfile_results.txt" )  ){
	            out.println(results );
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print("error when writing running time results");
			}
		
		return results;
			
		
	}
	
	

	
	/**
	 * use the graphs generated by {@link graphGenerator.java} to 
	 * test the performance of each algorithm
	 * @return
	 * @throws Exception 
	 */
	public static String performanceTesting () throws Exception{
	 	long[] runtimeArray = new long[10];
        long startTime = 0;
		String directory = System.getProperty("user.dir");
		
		String fileName1 = "vertex_range_result.txt";
		String fileName2 = "parameter_range_result.txt";
		String vertexrange_result = null;
		String pararange_result = null;
		System.out.println("---------------------------------------------------\n");
		
		// test three algorithms on graphs, change vertex count
		SimpleGraph[] bipartiteG_arr = graphGenerator.VertexRange(SIZE, "bipartite");
		  for(int i = 0;i<SIZE;i++){

	        	System.out.println(bipartiteG_arr[i].numVertices()+" - "+bipartiteG_arr[i].numEdges());   
	        	// get average running time on each graph
	        	 String tmp_rlt = runAlgorithms(bipartiteG_arr[i]);
	         	System.out.println(i+" "+ tmp_rlt);
	         	vertexrange_result = vertexrange_result+tmp_rlt;
	         	        	
	        }
		  
		  SimpleGraph[] fixdegree_arr = graphGenerator.VertexRange(SIZE, "fixeddegree");
		  for(int i = 0;i<SIZE;i++){

	        	System.out.println(fixdegree_arr[i].numVertices()+" - "+fixdegree_arr[i].numEdges());        	
	        	 String tmp_rlt = runAlgorithms(fixdegree_arr[i]);
	         	System.out.println(i+" "+ tmp_rlt);
	         	vertexrange_result = vertexrange_result+tmp_rlt;
	         	        	
	        }
		  
		  
		  SimpleGraph[] mesh_arr = graphGenerator.VertexRange(SIZE, "mesh");
		  for(int i = 0;i<SIZE;i++){

	        	System.out.println(mesh_arr[i].numVertices()+" - "+mesh_arr[i].numEdges());        	
	        	 String tmp_rlt = runAlgorithms(mesh_arr[i]);
	         	System.out.println(i+" "+ tmp_rlt);
	         	vertexrange_result = vertexrange_result+tmp_rlt;
	         	        	
	        }
		  
		  SimpleGraph[] random_arr = graphGenerator.VertexRange(SIZE, "random");
		  for(int i = 0;i<SIZE;i++){

	        	System.out.println(random_arr[i].numVertices()+" - "+random_arr[i].numEdges());        	
	        	 String tmp_rlt = runAlgorithms(random_arr[i]);
	         	System.out.println(i+" "+ tmp_rlt);
	         	vertexrange_result = vertexrange_result+tmp_rlt;
	         	        	
	        }
		  
		// test three algorithms on graphs, change parameter
			SimpleGraph[] bipartiteG_arr2 = graphGenerator.ParameterRange(SIZE, "bipartite");
			  for(int i = 0;i<SIZE;i++){

		        	System.out.println(bipartiteG_arr2[i].numVertices()+" - "+bipartiteG_arr2[i].numEdges());        	
		        	String tmp_rlt = runAlgorithms(bipartiteG_arr2[i]);
		         	System.out.println(i+" "+ tmp_rlt);
		         	pararange_result = pararange_result+tmp_rlt;
		         	        	
		        }
			  
			  SimpleGraph[] fixdegree_arr2 = graphGenerator.VertexRange(SIZE, "fixeddegree");
			  for(int i = 0;i<SIZE;i++){

		        	System.out.println(fixdegree_arr2[i].numVertices()+" - "+fixdegree_arr2[i].numEdges());        	
		        	 String tmp_rlt = runAlgorithms(fixdegree_arr[i]);
		         	System.out.println(i+" "+ tmp_rlt);
		         	pararange_result = pararange_result+tmp_rlt;
		         	        	
		        }
			  
			  
			  SimpleGraph[] mesh_arr2 = graphGenerator.VertexRange(SIZE, "mesh");
			  for(int i = 0;i<SIZE;i++){

		        	System.out.println(mesh_arr2[i].numVertices()+" - "+mesh_arr2[i].numEdges());        	
		        	 String tmp_rlt = runAlgorithms(mesh_arr[i]);
		         	System.out.println(i+" "+ tmp_rlt);
		         	pararange_result = pararange_result+tmp_rlt;
		         	        	
		        }
			  
			  SimpleGraph[] random_arr2 = graphGenerator.VertexRange(SIZE, "random");
			  for(int i = 0;i<SIZE;i++){

		        	System.out.println(random_arr2[i].numVertices()+" - "+random_arr2[i].numEdges());        	
		        	 String tmp_rlt = runAlgorithms(random_arr[i]);
		         	System.out.println(i+" "+ tmp_rlt);
		         	pararange_result = pararange_result+tmp_rlt;
		         	        	
		        }
			  
			  
		  try(  PrintWriter out = new PrintWriter( fileName1 )  ){
	            out.println(vertexrange_result );
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print("error when writing test performance results");
			}
		  
		  
		  try(  PrintWriter out = new PrintWriter( fileName2 )  ){
	            out.println(pararange_result );
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print("error when writing test performance results");
			}
		return vertexrange_result;
	}
	

}
