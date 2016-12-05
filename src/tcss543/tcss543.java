/**
 * 
 */
package tcss543;

import graph.GraphInput;
import graph.SimpleGraph;

/**
 * @author yanan, weijia chen, zijun qu;
 * Dec, 2016
 *
 */
public class tcss543 {

	/**
	 * load graphs from txt files
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  SimpleGraph simpleG;
          simpleG= new SimpleGraph();
          GraphInput.LoadSimpleGraph(simpleG, args[0]);
	}

}
