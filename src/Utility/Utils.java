package Utility;

public class Utils {
	
	/**
	 * return average running time
	 * @param runtime
	 * @return
	 */
	public static double getAverageRunTime(long[] runtime){
		long sum = 0;
		for(int i=0; i < runtime.length ; i++)
            sum = sum + runtime[i];
   
    //calculate average value
    double average = (double)sum / (double)runtime.length;
   
		return average;
	}

}
