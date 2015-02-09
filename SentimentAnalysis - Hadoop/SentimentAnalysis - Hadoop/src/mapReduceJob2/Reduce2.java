package mapReduceJob2;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import sentimentAnalysis.SentimentAnalysis;


/**
 * Reduce job2 for calculating the sentiment score of tweets for particular region.
 * Input: Input data file generated by Map2 job.
 * Output: Data file containing the sentiment score for a each location.
*/

public class Reduce2 extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
	
	public Reduce2() {
		SentimentAnalysis.init();
	}
    public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
    	double totalScore = 0;
    	double averageScore = 0;
    	String avgScoreString;
    	int count = 0;
    	Text t = new Text();
    	while(values.hasNext()) {
    		t = values.next();
    		totalScore += SentimentAnalysis.findSentimentScore(t.toString());
    		++count;
    	}
    	averageScore = totalScore/count;
    	avgScoreString = String.valueOf(averageScore);
    	if(avgScoreString.length() > 6) {
    		avgScoreString = avgScoreString.substring(0, 5);
    	}
    	output.collect(new Text(key), new Text(avgScoreString.trim() + "\t" + count));
    	
    }
}