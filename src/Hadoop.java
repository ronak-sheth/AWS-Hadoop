//Name: Ronak Sheth
//ID: 1001051051
//
//Name: Sharan Raghu
//ID: 1001117995
//
//Name: Ameya Patil
//ID: 1001101520
//
//CSE 6331
//Batch Time: 3:30-5:20 pm

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class Hadoop {

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    conf.set("fs.s3.impl",
			"org.apache.hadoop.fs.s3native.NativeS3FileSystem");
	conf.set("fs.s3.awsAccessKeyId", ""); // Access Id
	conf.set("fs.s3.awsSecretAccessKey",
			""); // Secret Key

	System.out.println("Started");
    
    Job job = new Job(conf, "word count");
    job.setJarByClass(Hadoop.class);
    job.setMapperClass(HadoopMap.class);
    job.setCombinerClass(HadoopReducer.class);
    job.setReducerClass(HadoopReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(LongWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}