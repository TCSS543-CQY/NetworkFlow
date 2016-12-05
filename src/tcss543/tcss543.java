/**
 * 
 */
package tcss543;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import Utility.Utils;
import algorithms.FordFulkerson;
import algorithms.PreflowPush;
import algorithms.ScalingFordFulkerson;
import graph.GraphInput;
import graph.SimpleGraph;


/**
 * @author yanan, weijia chen, zijun qu;
 * Dec, 2016
 *
 */
public class tcss543 {
	
	public static Utils ut;
	public static PerformanceTest pt;

	/**
	 * load graphs from txt files
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		  SimpleGraph simpleG;
          simpleG= new SimpleGraph();
          if (args.length>0){
        	  GraphInput.LoadSimpleGraph(simpleG, args[0]);
        	  System.out.print(pt.runAlgorithms(simpleG));
          }
          else{
        	  throw new IOException("please input a text file");
          }
          
          
	}
	
	
	/**
	 * run three algorithms on each type of graphs 10 times, and record running time
	 * @return
	 */
//	public static String runAlgorithms (SimpleGraph g){
//			
//		  	long[] runtimeArray = new long[10];
//	        long startTime = 0;
//	        String results = null;
//	        System.out.println("result for running all algorithem 10 times given an input file: ");
//	        System.out.println("\n");
//	        System.out.println("------------------------------------");
//	        
//	        //Ford-Fulkerson
//	    	double FFMaxflow = 0;
//	        for(int i = 0;i<10;i++){ 
//	        	startTime = System.currentTimeMillis();
//	        	FFMaxflow = FordFulkerson.FordFulkerson(g);
//	        	runtimeArray[i] = System.currentTimeMillis() - startTime;
//	        }
//	        System.out.println("The maximum flow of the input Graph calculated "
//	        		+ "by Ford-Fulkerson algorithm is "+FFMaxflow);
//	        System.out.println("The average running time of 50 preflow Ford-Fulkerson "
//	        		+ "algorithm is : " + ut.getAverageRunTime(runtimeArray) + " Milliseconds.");
//	        results = results+","+String.valueOf(ut.getAverageRunTime(runtimeArray));
//	        
//	        
//	        //Run Preflow Push
//	        double preFlowMaxflow = 0;
//	        for(int i = 0;i<10;i++){   
////	        	System.out.println(i+"th time for preFlow push");
//	        	startTime = System.currentTimeMillis();
//	        	preFlowMaxflow = PreflowPush.PreflowPush(g);
//	        	runtimeArray[i] = System.currentTimeMillis() - startTime;
//	            
//	        } 
//	        System.out.println("The maximum flow of the input Graph "
//	        		+ "calculated by Preflow Push is "+preFlowMaxflow);
//	        System.out.println("The average running time of 50 "
//	        		+ "preflow push algorithm runs is : " + ut.getAverageRunTime(runtimeArray) + " Milliseconds.");
//	        results = results+","+String.valueOf(ut.getAverageRunTime(runtimeArray));
//	        
//	        
//
//	        //Run scaling Ford-Fulkerson
//	        double SFFMaxflow = 0.0;
//	        for(int i = 0;i<10;i++){      	
//	        	startTime = System.currentTimeMillis();
//	        	SFFMaxflow = ScalingFordFulkerson.ScalingFordFulkerson(g);
//	        	runtimeArray[i] = System.currentTimeMillis() - startTime;
//	        }   
//	        System.out.println("The maximum flow of the input Graph calculated by scaling "
//	        		+ "Ford-Fulkerson algorithm is "+SFFMaxflow);
//	        System.out.println("The average running time of 50 scaling Ford-Fulkerson "
//	        		+ "algorithm is : " + ut.getAverageRunTime(runtimeArray) + " Milliseconds.");
//	        results = results+","+String.valueOf(ut.getAverageRunTime(runtimeArray));
//	        
//	        try(  PrintWriter out = new PrintWriter( "run_textfile_results.txt" )  ){
//	            out.println(results );
//	        } catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				System.out.print("error when writing running time results");
//			}
//		
//		return results;
//			
//		
//	}
	
	


}
