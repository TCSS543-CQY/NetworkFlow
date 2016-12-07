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
	 * load graphs from txt files and run the 3 algorithms on this graph
	 * output maxflow and running time
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
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
	
	
	

}
