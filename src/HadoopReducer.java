import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class HadoopReducer extends
		Reducer<Text, LongWritable, Text, LongWritable> {
	private LongWritable result = new LongWritable();

	public void reduce(Text key, Iterable<LongWritable> values, Context context)
			throws IOException, InterruptedException {
		long average = 0;
		int i = 0;
		for (LongWritable val : values) {
			if (val.get() != -9999) {
				i++;
				average = (((average*(i-1)) + val.get()) / i);
			}

		}
		result.set(average);
		context.write(key, result);
	}
}