import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class HadoopMap extends Mapper<LongWritable, Text, Text, LongWritable> {

	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		StringTokenizer itr = new StringTokenizer(value.toString(), ",");
		while (itr.hasMoreTokens()) {
			//itr.nextToken();
			String year = itr.nextToken();
			year = year.substring(0,4);
			itr.nextToken();

			Long TMax = Long.parseLong(itr.nextToken());
			Long TMin = Long.parseLong(itr.nextToken());
			Long average;
			if (TMax == -9999 && TMin == -9999) {
				average = (long) -9999;

			} else if (TMax == -9999 && TMin != -9999) {

				average = (long) TMin;
			} else if (TMax != -9999 && TMin == -9999) {

				average = (long) TMax;
			} else {

				average = (TMax + TMin) / 2;
			}
			context.write(new Text(year), new LongWritable(average));

		}
	}
}