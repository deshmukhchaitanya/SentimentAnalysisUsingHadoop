package mainSentiment;

import mapReduceJob1.SentimentJob1;
import mapReduceJob2.SentimentJob2;


/**
 * Main sentiment analysis class for executing all mapreduce jobs.
 */
public class SentimentMain {
	
	public static void main(String[] args) {
		
		try {
			if(args[0] == null || args[0] == "" || args[1] == null || args[1] == "") {
				System.out.println("Provide proper input !!!");
				return;
			}
			String outDirectory1 = "output1";
			String outDirectory2 = "output2";
			
			//Creating first mapreduce job
			SentimentJob1 job1 = new SentimentJob1();
			job1.runSentimentJob1(args[0],outDirectory1,args[1]);
			
			//Creating first mapreduce job
			SentimentJob2 job2 = new SentimentJob2();
			job2.runSentimentJob2(outDirectory1,outDirectory2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
