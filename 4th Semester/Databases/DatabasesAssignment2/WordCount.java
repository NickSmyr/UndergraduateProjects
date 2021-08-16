/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.beam.examples;

import org.apache.beam.examples.common.ExampleUtils;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.metrics.Counter;
import org.apache.beam.sdk.metrics.Distribution;
import org.apache.beam.sdk.metrics.Metrics;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.Validation.Required;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionList;
import org.apache.beam.sdk.transforms.Flatten;
import org.apache.beam.sdk.transforms.Filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
/**
 * An example that counts words in Shakespeare and includes Beam best practices.
 *
 * <p>This class, {@link WordCount}, is the second in a series of four successively more detailed
 * 'word count' examples. You may first want to take a look at {@link MinimalWordCount}. After
 * you've looked at this example, then see the {@link DebuggingWordCount} pipeline, for introduction
 * of additional concepts.
 *
 * <p>For a detailed walkthrough of this example, see <a
 * href="https://beam.apache.org/get-started/wordcount-example/">
 * https://beam.apache.org/get-started/wordcount-example/ </a>
 *
 * <p>Basic concepts, also in the MinimalWordCount example: Reading text files; counting a
 * PCollection; writing to text files
 *
 * <p>New Concepts:
 *
 * <pre>
 *   1. Executing a Pipeline both locally and using the selected runner
 *   2. Using ParDo with static DoFns defined out-of-line
 *   3. Building a composite transform
 *   4. Defining your own pipeline options
 * </pre>
 *
 * <p>Concept #1: you can execute this pipeline either locally or using by selecting another runner.
 * These are now command-line options and not hard-coded as they were in the MinimalWordCount
 * example.
 *
 * <p>To change the runner, specify:
 *
 * <pre>{@code
 * --runner=YOUR_SELECTED_RUNNER
 * }</pre>
 *
 * <p>To execute this pipeline, specify a local output file (if using the {@code DirectRunner}) or
 * output prefix on a supported distributed file system.
 *
 * <pre>{@code
 * --output=[YOUR_LOCAL_FILE | YOUR_OUTPUT_PREFIX]
 * }</pre>
 *
 * <p>The input file defaults to a public data set containing the text of of King Lear, by William
 * Shakespeare. You can override it and choose your own input with {@code --inputFile}.
 */
public class WordCount {	
  /**
   * Concept #2: You can make your pipeline assembly code less verbose by defining your DoFns
   * statically out-of-line. This DoFn tokenizes lines of text into individual words; we pass it to
   * a ParDo in the pipeline.
   */
  static class ExtractRowsFn extends DoFn<String, ArrayList<String>> {
    private final String bedrooms;
    private final int startYear;
    private final int startMonth;
    private final int endYear;
    private final int endMonth;
    
    int numberOfValues = 0; 
    //Constructor   
	public ExtractRowsFn(String bedrooms,int startYear,int startMonth,int endYear,int endMonth){
		this.bedrooms = bedrooms;
		this.startYear = startYear;
		this.startMonth = startMonth;
		this.endYear = endYear;
		this.endMonth = endMonth;
	}
    @ProcessElement
    public void processElement(@Element String element, OutputReceiver<ArrayList<String>> receiver) {
      // Split the line into words.
      String[] words = element.split(",", -1);
      	  ArrayList<String> temp = new ArrayList<String>();
       	
      for(String e : words){temp.add(e);}
      
       
	  //If there exists a line that has a number of values different than all the others
	  if(numberOfValues != 0 && words.length != numberOfValues ){
	  	System.err.println("[PARSE INFO] Found csv line with different amount of values ");
	  }	
	  //Columns : RegionName City State Metro COuntyName SizeRank (2010-09 - 2018-01)	
      // Output each word encountered into the output PCollection.
       ArrayList<String> more = new ArrayList<String>();
          more.add(temp.get(0)); //RegName
          more.add(temp.get(1)); //City	
          more.add(temp.get(2)); //State
          more.add(temp.get(3)); //Metro
          more.add(temp.get(4)); //CountyName
          more.add(temp.get(5)); //SizeRank
     
      	  //7th element will be the date
      	  more.add("");
      	  //8th element will be the price on that date
      	  more.add("");
      	  //9th element will be the number of bedrooms
      	  more.add(bedrooms);
      	  int year = startYear;
      	  int month = startMonth;
      	  String date;
      	  for(int i = 6 ; (year != endYear || month != endMonth) && i < temp.size() ;i++){
      	  
      	  	  if(month < 10) date = String.valueOf(year) +"-0"+month+"-01";
      	  	  else date = String.valueOf(year) +"-"+month+"-01";
      	  	  
			  more.set(6,date);
			  more.set(7 , temp.get(i));	      	  
		  	  receiver.output(new ArrayList<String>(more));
		  	  //Increasing the date to the next month
		  	  if(month == 12){
		  	  	year++;
		  	  	month = 1;
			  }
			  else{
			  	month++;
			  }
			 // System.err.println(more);
			  //System.err.println(i); 
		  }
		  //try{Thread.sleep(2000);}catch(Throwable e){}	
		  
		  if(year != endYear&& month != endMonth) {
		  	System.err.println("[PARSE INFO] Error on date parsing at bedrooms = "+bedrooms);
		  } 	    
      //for (int i = 2 ; i < temp.length;i++) {
      //    more.add(temp.get(i));
      //    receiver.output(new ArrayList<String>(more)); 	
      //}
      //receiver.output(new ArrayList<String>(more));         
    }
  }

  /** A SimpleFunction that converts a Word and Count into a printable string. */
  public static class FormatAsTextFn extends SimpleFunction<ArrayList<String>, String> {
    @Override
    public String apply(ArrayList<String> input) {
      String result = "";
      for(int i = 0 ; i < input.size();i++){
      	result = result + input.get(i) + ",";
      }
      //Removing final comma
      return result.substring(0,result.length()-1);
    }
    
  }

  /**
   * A PTransform that converts a PCollection containing lines of text into a PCollection of
   * formatted word counts.
   *
   * <p>Concept #3: This is a custom composite transform that bundles two transforms (ParDo and
   * Count) as a reusable PTransform subclass. Using composite transforms allows for easy reuse,
   * modular testing, and an improved monitoring experience.
   */
  /**public static class CountWords
      extends PTransform<PCollection<String>, PCollection<ArrayList<String>>> {
	private final String bedrooms;
    public CountWords(String bedrooms){
		super();
		this.bedrooms = bedrooms;
	}
	@Override
    public PCollection<ArrayList<String>> expand(PCollection<String> lines) {
 
      // Convert lines of text into individual words.
      PCollection<ArrayList<String>> words = lines.apply(ParDo.of(new ExtractWordsFn(bedrooms)));

      // Count the number of times each word occurs.
      //PCollection<KV<String, Long>> wordCounts = words.apply(Count.perElement());

      return words;
    }
  }
		**/
  /**
   * Options supported by {@link WordCount}.
   *
   * <p>Concept #4: Defining your own configuration options. Here, you can add your own arguments to
   * be processed by the command-line parser, and specify default values for them. You can then
   * access the options values in your pipeline code.
   *
   * <p>Inherits standard configuration options.
   */
  public interface WordCountOptions extends PipelineOptions {

    /**
     * By default, this example reads from a public dataset containing the text of King Lear. Set
     * this option to choose a different input file or glob.
     */
    @Description("Path of the file to read from")
    @Default.String("gs://apache-beam-samples/shakespeare/kinglear.txt")
    String getInputFile();

    void setInputFile(String value);

    /** Set this required option to specify where to write the output. */
    @Description("Path of the file to write to")
    @Required
    String getOutput();

    void setOutput(String value);
  }

  static void runWordCount(WordCountOptions options) {
    Pipeline p = Pipeline.create(options);
	ZillowHeaderReader reader = new ZillowHeaderReader();
   
    //1 Bedroom pipeline path
    String filename = "Zip_MedianRentalPrice_1Bedroom.csv";
    if(!reader.read("header/" + filename)) {
    	return;
    }
    PCollection<ArrayList<String>> bdr1 = 
    	p.apply("ReadBedrooms1", TextIO.read().from("no_header/"+filename))
         .apply(ParDo.of(new ExtractRowsFn("1",reader.startYear,reader.startMonth,reader.endYear,reader.endMonth)));
    
    
    //2 Bedroom pipeline path
    filename = "Zip_MedianRentalPrice_2Bedroom.csv";      
    if(!reader.read("header/" + filename)) {
    	return;
    }     
    PCollection<ArrayList<String>> bdr2 = 
    	p.apply("ReadBedrooms2", TextIO.read().from("no_header/"+filename))
         .apply(ParDo.of(new ExtractRowsFn("2",reader.startYear,reader.startMonth,reader.endYear,reader.endMonth)));
    

	//3 Bedroom pipeline path
    filename = "Zip_MedianRentalPrice_3Bedroom.csv";      
    if(!reader.read("header/" + filename)) {
    	return;
    }    
    PCollection<ArrayList<String>> bdr3 = 
    	p.apply("ReadBedrooms3", TextIO.read().from("no_header/"+filename))
         .apply(ParDo.of(new ExtractRowsFn("3",reader.startYear,reader.startMonth,reader.endYear,reader.endMonth)));  
    //4 Bedroom pipeline path     
    filename = "Zip_MedianRentalPrice_4Bedroom.csv";      
    if(!reader.read("header/" + filename)) {
    	return;
    } 
    PCollection<ArrayList<String>> bdr4 = 
    	p.apply("ReadBedrooms4", TextIO.read().from("no_header/"+filename))
         .apply(ParDo.of(new ExtractRowsFn("4",reader.startYear,reader.startMonth,reader.endYear,reader.endMonth)));
    //5+ Bedroom pipeline path
    filename = "Zip_MedianRentalPrice_5BedroomOrMore.csv";      
    if(!reader.read("header/" + filename)) {
    	return;
    } 
    PCollection<ArrayList<String>> bdr5 = 
    	p.apply("ReadBedrooms5+", TextIO.read().from("no_header/"+filename))
         .apply(ParDo.of(new ExtractRowsFn("5+",reader.startYear,reader.startMonth,reader.endYear,reader.endMonth)));
    //Combining all pipeline paths                           
    PCollectionList<ArrayList<String>> allbedrooms = PCollectionList
    													.of(bdr1).and(bdr2).and(bdr3).and(bdr4).and(bdr5);
    //Applying final filters and writing to output file													
    PCollection<ArrayList<String>> events = allbedrooms.apply(Flatten.pCollections());													     
        events.apply("FIlter_2016_and_later_and_before_2018",Filter.by((ArrayList<String> row) -> {
        	String[] x =  row.get(6).split("-");
        	return Integer.valueOf(x[0]) >=  2016 && !(Integer.valueOf(x[0]) >= 2018 && Integer.valueOf(x[1]) > 1);
        }))
        .apply(MapElements.via(new FormatAsTextFn()))
        .apply("WriteCounts", TextIO.write().to(options.getOutput())
        						.withHeader("Zipcode,City,State,Metro,CountyName,SizeRank,Date,Price,NumBedrooms"));

    p.run().waitUntilFinish();
  }

  public static void main(String[] args) {
    WordCountOptions options =
        PipelineOptionsFactory.fromArgs(args).withValidation().as(WordCountOptions.class);
    runWordCount(options);
  }
  /**
  	Opens one of the zillow data files and draws information from the header line
  	endYear and endMonth are one month later than the last date specified in the header
  **/
  public static class ZillowHeaderReader{
  	int startYear = 0;
  	int startMonth = 0;
  	int endYear = 0; 
  	int endMonth = 0;
  	

  	public boolean read(String filename){
  		System.out.println(filename);
 		String line;
 		Scanner sc  = null;	
 		try{
 			sc = new Scanner(new File(filename));
 			line = sc.nextLine();
 		}
 		catch(Exception e){
			return false;
		}
		finally{
			if(sc!=null)sc.close();
		}
		String[] tokens = line.split(",");
		//Dates start from the 7th column
		String firstDate = tokens[6];
		String lastDate = tokens[tokens.length-1];
		//Some csv files when read have double quotes at the
		//start and end of the tokens
		if(hasOpeningAndClosingQuotes(firstDate)){
			firstDate = removeOpeningAndClosingQuotes(firstDate);
		}
		if(hasOpeningAndClosingQuotes(lastDate)){
			lastDate = removeOpeningAndClosingQuotes(lastDate);
		}
		//System.out.println(line);
		String[] yymmdd1 = firstDate.split("-");
		String[] yymmdd2 = lastDate.split("-");
		//System.out.println(yymmdd1[0].charAt(0));
		//System.out.printf("(%s %s) (%s %s)",yymmdd1[0],yymmdd1[1],yymmdd2[0],yymmdd2[1]);
		try{
			this.startYear = Integer.valueOf(yymmdd1[0]);
			this.startMonth = Integer.valueOf(yymmdd1[1]);
			
			this.endYear = Integer.valueOf(yymmdd2[0]);
			this.endMonth = Integer.valueOf(yymmdd2[1]);
			//Incrementing the end date by 1
			if(endMonth == 12){
				endYear++;
				endMonth = 1;
			}
			else endMonth++;
		}
		catch(Exception e){
			return false;
		}
		System.out.printf("Successfully read filename %s end year %d end month %d\n" , filename,endYear,endMonth);
		return true;
  	}
  	public static boolean hasOpeningAndClosingQuotes(String in){
		if(in.length() >= 2 && in.charAt(0) == in.charAt(in.length()-1)&& in.charAt(0)  == '\"') return true;
		return false;
	}
	public static String removeOpeningAndClosingQuotes(String in){
		return in.substring(1,in.length()-1);
	}
  }
}
