package mapReduceJob1;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

  /**
   * Mapping the twitter data file among data nodes.
   * Input: Input data file containing location information and tweet data.
   * Output: Intermediate data files for reduce job.
 */
public class Map1 extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
    private Text locationKey = new Text();
    private Text tweetValue = new Text();
 
    public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
      String line = value.toString();
      
      String[] words = line.split("\t");
      if(words.length >= 2 && words[0] != null && words[0].trim() != "" && words[1] != null && words[1].trim() != "") {
    	  if(words[0].contains(",")) {
    		  String location = words[0].split(",")[0];
    		  if(location != null && location.trim() != "") {
    			  locationKey.set(location);
    			  tweetValue.set(words[1]);
    			  output.collect(locationKey,tweetValue);
    		  }
    	  }
      }
    }
  }