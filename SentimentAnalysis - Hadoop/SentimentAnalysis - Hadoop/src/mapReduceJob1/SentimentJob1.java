package mapReduceJob1;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

/**
 * Sentiment1 class provides method for executing first mapreduce job i.e. map1 and reduce1 jobs.
 */
public class SentimentJob1 {
	
  public void runSentimentJob1(String input, String output, String userToken) throws Exception{
	    JobConf conf = new JobConf(SentimentJob1.class);
	    conf.setJobName("sentiment_job1");

	    conf.setOutputKeyClass(Text.class);
	    conf.setOutputValueClass(Text.class);

	    conf.setMapperClass(Map1.class);
	    conf.setReducerClass(Reduce1.class);

	    conf.setInputFormat(TextInputFormat.class);
	    conf.setOutputFormat(TextOutputFormat.class);
	    
	    conf.set("userToken1", userToken);

	    FileInputFormat.setInputPaths(conf, new Path(input));
	    FileOutputFormat.setOutputPath(conf, new Path(output));

	    JobClient.runJob(conf);
  }

}
