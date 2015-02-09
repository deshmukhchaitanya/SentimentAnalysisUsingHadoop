package mapReduceJob2;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/**
 * Mapping the Reduce1 output twitter data file among data nodes.
 * Input: Input data file containing location information and tweet data relevant to user input.
 * Output: Intermediate data files for reduce2 job.
*/
public class Map2 extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
  private Text locationKey = new Text();
  private Text tweetValue = new Text();
 
  public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
    String line = value.toString();
    String[] words = line.split("\t");
    locationKey.set(words[0]);
    tweetValue.set(words[1]);
    output.collect(locationKey,tweetValue);
  }
  
}