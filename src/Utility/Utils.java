package Utility;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Utils {
	
	/**
	 * return average running time
	 * @param runtime
	 * @return
	 */
	public static String getAverageRunTime(long[] runtime){
		
		
		BigDecimal SUM, AVERAGE;
	
           
		double sum = 0.0;
	
		for(int i=0; i < runtime.length ; i++)
            sum = sum + runtime[i];
		
   
    //calculate average value
	 SUM = new BigDecimal(sum)  ;
	 BigDecimal RUN_LEN = new BigDecimal(runtime.length);
	        
    AVERAGE = SUM.divide(RUN_LEN);
    //DecimalFormat df = new DecimalFormat("#.#####");
    String average_str = AVERAGE.toString();
	return average_str;
	}

}
