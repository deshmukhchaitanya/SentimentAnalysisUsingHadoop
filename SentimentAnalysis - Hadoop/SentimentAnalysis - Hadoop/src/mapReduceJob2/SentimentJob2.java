package mapReduceJob2;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;


/**
 * Sentiment2 class provides method for executing second mapreduce job i.e. map2 and reduce2 jobs.
 */
public class SentimentJob2 {
	
  public void runSentimentJob2(String input, String output) throws Exception{
	    JobConf conf = new JobConf(SentimentJob2.class);
	    conf.setJobName("sentiment_job2");

	    conf.setOutputKeyClass(Text.class);
	    conf.setOutputValueClass(Text.class);

	    conf.setMapperClass(Map2.class);
	    conf.setReducerClass(Reduce2.class);

	    conf.setInputFormat(TextInputFormat.class);
	    conf.setOutputFormat(TextOutputFormat.class);

	    FileInputFormat.setInputPaths(conf, new Path(input));
	    FileOutputFormat.setOutputPath(conf, new Path(output));

	    JobClient.runJob(conf);
  }
}
