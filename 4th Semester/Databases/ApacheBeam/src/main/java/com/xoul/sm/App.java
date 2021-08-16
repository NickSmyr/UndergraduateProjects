package com.xoul.sm;

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
import org.apache.beam.sdk.transforms.SerializableFunction;
import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.io.FileIO.ReadableFile;

import org.apache.beam.runners.spark.SparkRunner;

import org.apache.commons.csv.CSVParser; 
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.Serializable;
import java.nio.channels.Channels; 


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
public class App  {	
  /**
   * Creates table rows out of strings and fills a set containing the unique  key combinations
   * 	
   */
  static class ExtractRowsNoSetFn extends DoFn<String, Map<String,String>>{
			private ArrayList<String> header;
			public ExtractRowsNoSetFn(ArrayList<String> header){
				this.header = header;
			}
			
		  @ProcessElement
		  public void processElement(@Element String element, OutputReceiver<Map<String,String>> receiver) {
		    // Split the line into columns.
		  	Map<String,String> result = Utils.parseRow(element,header);
		  	receiver.output(result);     
		  }
  } 
  static class ExtractRowsFn extends DoFn<String, Map<String,String>>{
			private ArrayList<String> header;
			private Set<String> primaryKeys;
			private List<String> primaryKeyFields;
			public ExtractRowsFn(ArrayList<String> header,Set<String> primaryKey,List<String> primaryKeyFields){
				this.header = header;
				this.primaryKeys = primaryKey;
				if(primaryKeyFields.size() == 0) throw new RuntimeException("No primary key specified");
				this.primaryKeyFields = primaryKeyFields;
			}
			
		  @ProcessElement
		  public void processElement(@Element String element, OutputReceiver<Map<String,String>> receiver) {
		    // Split the line into columns.
			//System.out.println(primaryKeyFields);
		  	Map<String,String> result = Utils.parseRow(element,header);
		  	//System.out.println(result);
		  	//System.out.println("Line size ??? "+Utils.csvSplit(element).size());
		  	List<String> id = new ArrayList<>();
		  	for(String e : primaryKeyFields){
		  		id.add(result.get(e));
		  	}
		  	String primaryKey = Utils.listToHeader(id);	
		  	if(!primaryKeys.contains(primaryKey)){
		  	 	receiver.output(result);
		  	 	primaryKeys.add(primaryKey);		
		  	 }
		  	else{System.out.println("FOUND DUPLICATE KEYA" + primaryKey);} 
		    //for (int i = 2 ; i < temp.length;i++) {
		    //    more.add(temp.get(i));
		    //    receiver.output(new ArrayList<String>(more)); 	
		    //}
		    //receiver.output(new ArrayList<String>(more));         
		  }
  }
  /**
  	Converts an INput String to a collection of csv rows
  **/
  static class ConvertToString extends DoFn<ReadableFile,String>{
  	private ArrayList<String> header;
  	//Constructor
  	public ConvertToString(ArrayList<String> header){
  		this.header = header;
  	}
  	 @ProcessElement
    public void process(ProcessContext c) throws IOException {
    	Scanner sc= null;
		try (InputStream is = Channels.newInputStream(c.element().open())) {
     		// ... Use your favorite Java CSV library ...
     		//... c.output(next csv record) ...
     		sc = new Scanner(is);
     		sc.nextLine();//SKip header
     		String result = "";
     		String line = "";
     		ArrayList<String> csv = new ArrayList<String>();
     		boolean insideQuotes = false;
     		while(sc.hasNextLine()){
		   					   		
		   		line = sc.nextLine();
		   		for(char e: line.toCharArray()){
		   			//try{Thread.sleep(100);}catch(Exception z){}
		   			//System.out.println("Current character =" + e + "inside Quotes ?"+ insideQuotes);
		   			//System.out.println(result);
		   			if(e == '"' && !insideQuotes){
		   			 insideQuotes = true;
		   			 result += '"';
		   			}
		   			else if(e == '"' && insideQuotes) {
		   			 insideQuotes = false;
		   			 result += '"';
		   			}
		   			else if(e == ',' && !insideQuotes) {
		   				
		   				csv.add(new String(result));
		   				result = "";
		   				if(csv.size() == header.size()){
		   					//Converting array of tags to a csv row string
		   					c.output(Utils.listToHeader(new ArrayList<String>(csv)));
		   					csv = new ArrayList<String>();
		   					break;
		   				}
		   			}
		   			else {
		   				result += e;
		   			}
		   		}
		   		if(insideQuotes) continue;
		   		else{
		   			csv.add(new String(result));
	   				result = "";
	   				if(csv.size() == header.size()){
	   					//Converting array of tags to a csv row string
	   					c.output(Utils.listToHeader(new ArrayList<String>(csv)));
	   					csv = new ArrayList<String>();
	   				}
		   		}
		   		 
			}
     	}
     	catch(IOException e){
     		System.err.println("Critical error with file reading");
     	}
     	finally{
     		if(sc != null) sc.close();
     	}
	}
}

	
  /**
  	Alters fields according to a specified function
  **/
  static class ForceValueFieldsFn extends DoFn<Map<String,String>, Map<String,String>>{
			private List<String> fields;
			private Function<String,String> fun;
			public ForceValueFieldsFn(Function<String,String> fun,List<String> fields){
				this.fields = fields;
				this.fun = fun;
			}			
		  @ProcessElement
		  public void processElement(@Element Map<String,String> element, OutputReceiver<Map<String,String>> receiver) {
		  	element = new HashMap<String,String>(element); //Making copy of input element
		  	int prev = element.size();
		    for(String field : fields){
		    	String value = element.get(field);
		    	if (value == null){
						System.err.printf("Field %s is non existant\n",field);
					}
					element.replace(field,fun.apply(element.get(field)));
				}
				//System.out.printf("Old value %d , new value %s\n",prev,element.size());
		    receiver.output(element);         
		  }
  }
  /**
  static class ForceIntegerFieldsFn extends DoFn<Map<String,String>, Map<String,String>>{
			private List<String> fields;
			public ForceIntegerFieldsFn(List<String> fields){
				this.fields = fields;
			}			
		  @ProcessElement
		  public void processElement(@Element Map<String,String> element, OutputReceiver<Map<String,String>> receiver) {
		  	element = new HashMap<String,String>(element); //Making copy of input element
		    for(String field : fields){
		    	String value = element.get(field);
					element.put(element.get(field),forceInteger(element.get(field)));
				}
		    receiver.output(element);         
		  }
		  public static String forceInteger(String in){
		  	if(!Utils.isInt(in)) return "0";
		  	else return in;
			}
  }
  **/
  /** A SimpleFunction that converts a Row into a printable string.
  	 based on the header of the csv file the row belongs to
  **/
  public static class FormatAsTextFn extends SimpleFunction<Map<String,String>, String> {
  	private ArrayList<String> header;
  	public FormatAsTextFn(ArrayList<String> header){
  			this.header = header;
  	}
    @Override
    public String apply(Map<String,String> input) {
      String result = "";
      for(String e: header){
      	result = result + input.get(e) + ",";
      }
      //System.out.println(input.size());
      //Removing final comma
      return result.substring(0,result.length()-1);
    }    
  }

  
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
    String getOutput();

    void setOutput(String value);
  }

  static void runWordCount(WordCountOptions options) {
    Pipeline p = Pipeline.create(options);
    final AirbnbDataset airbnb = new AirbnbDataset();
    airbnb.loadListing("listing.csv");
    airbnb.loadHost("host.csv");
    airbnb.loadNeighborhood("neighborhood.csv");
    airbnb.loadCalendar("calendar.csv");
    //Phase 1 reads all db files applies quality criteria and ensures uniqueness
    //of primary keys 
    //System.out.println(airbnb.listingHeader);
    ///System.out.println(airbnb.listingFile);
		//////////////////////////////////////////////////////Listing pipeline
		/**
		airbnb.listingHeader.remove("calculated_host_listings_count");         
    	p.apply("Read from file", TextIO.read().from(airbnb.listingFile))
         .apply("Transform into rows",ParDo.of(new ExtractRowsFn(	airbnb.listingHeader,airbnb.listingIds,Arrays.asList("id"))))
         .apply("Force Integer Fields",ParDo.of(new ForceValueFieldsFn(new ForceInteger(),Arrays.asList(
         "scrape_id","id","neighbourhood_cleansed","host_id","accommodates","bedrooms",
         	"beds","guests_included","minimum_nights","maximum_nights","availability_365",
         	"number_of_reviews"))))
         .apply("Force Date Fields",ParDo.of(new ForceValueFieldsFn(new ForceDate(),Arrays.asList(
         	"last_scraped"
         ))))  
    //Applying final filters and writing to output file																									     
       	.apply(MapElements.via(new FormatAsTextFn(airbnb.listingHeader)))
        .apply("WriteCounts", TextIO.write().to("outputl.csv")
        						.withoutSharding().withHeader(Utils.listToHeader(airbnb.listingHeader)));
        						**/
    ///////////////////////////////////////////////////////Host pipeline
    /**
    p.apply(FileIO.match().filepattern(airbnb.hostFile))
 	   .apply(FileIO.readMatches())
     .apply("ReadCsv",ParDo.of(new ConvertToString(airbnb.hostHeader)))
     .apply("Transform into rows",ParDo.of(new ExtractRowsFn(	airbnb.hostHeader,airbnb.hostIds,Arrays.asList("id"))))
     .apply("Force Integer Fields",ParDo.of(new ForceValueFieldsFn(new ForceInteger(),Arrays.asList(
         "id","listings_count","total_listings_count","calculated_host_listings_count"))))
     .apply("Force Date Fields",ParDo.of(new ForceValueFieldsFn(new ForceDate(),Arrays.asList(
         	"since"
         ))))
     .apply(MapElements.via(new FormatAsTextFn(airbnb.hostHeader)))
        .apply("WriteCounts", TextIO.write().to("outputh.csv")
        						.withoutSharding().withHeader(Utils.listToHeader(airbnb.hostHeader)));
     **/    
     //////////////////////////////////////////////////////Neighborhood pipeline
     /**
     System.out.println(airbnb.headerNeighborhoodFile);       
     p.apply(FileIO.match().filepattern(airbnb.headerNeighborhoodFile))
 	  .apply(FileIO.readMatches())
      .apply("ReadCsv",ParDo.of(new ConvertToString(airbnb.neighborhoodHeader))) 
      .apply("Transform into rows",ParDo.of(new ExtractRowsFn(	airbnb.neighborhoodHeader,airbnb.hostIds,Arrays.asList("zip_code","neighbourhood_name"))))  
      .apply("Filter non empty primary key",Filter.by(new FieldsNotEmpty(Arrays.asList("zip_code","neighbourhood_name"))))
      .apply("Filter zipcode integer",Filter.by(new FieldsAreInteger(Arrays.asList("zip_code"))))
      .apply(MapElements.via(new FormatAsTextFn(airbnb.neighborhoodHeader)))
      .apply("Output", TextIO.write().to("outputneigh.csv")
        						.withoutSharding().withHeader(Utils.listToHeader(airbnb.neighborhoodHeader))); 
       **/
     /////////////////////////////////////////////////Calendar pipeline    
     p.apply(FileIO.match().filepattern(airbnb.headerCalendarFile))
 	  .apply(FileIO.readMatches())
      .apply("ReadCsv",ParDo.of(new ConvertToString(airbnb.calendarHeader)))     
      .apply("Transform into rows",ParDo.of(new ExtractRowsNoSetFn(airbnb.calendarHeader)))    
      .apply("Filter integer",Filter.by(new FieldsAreInteger(Arrays.asList("listing_id"))))
      .apply("Filter date",Filter.by(new FieldsAreDate(Arrays.asList("date"))))
      .apply("Format as text",MapElements.via(new FormatAsTextFn(airbnb.calendarHeader)))
      .apply("Output", TextIO.write().to("outputcal.csv")
        						.withoutSharding().withHeader(Utils.listToHeader(airbnb.calendarHeader)));       						
    p.run().waitUntilFinish();
    //Phase two discards all data not conforming to foreign key constraints
  }

  public static void main(String[] args) {
    WordCountOptions options =
        PipelineOptionsFactory.fromArgs(args).withValidation().as(WordCountOptions.	class);
    options.setRunner(SparkRunner.class);    
    runWordCount(options);
  }
  //Filter Predicates
  public static class FieldIsContainedIn implements SerializableFunction<Map<String,String>,Boolean>{
		private String field;
		private Set<String> allFields;
		public FieldIsContainedIn(String field, Set<String> allFields){
			this.field = field;
			this.allFields = allFields;
		}
		@Override
		public Boolean apply(Map<String,String> in){
			return allFields.contains(in.get(field));
		}
	}
	public static class FieldsAreInteger implements SerializableFunction<Map<String,String>,Boolean>{
		private List<String> fields;
		public FieldsAreInteger(List<String> fields){
			this.fields = fields;
		}
		//TODO Faster isInt check
		@Override
		public Boolean apply(Map<String,String> in){
			for(String e : fields){
				if(!Utils.isInt(in.get(e))) return false;
			}
			return true;
		}
		//Functions	
		
	}
	public static class FieldsAreDate implements SerializableFunction<Map<String,String>,Boolean>{
		private List<String> fields;
		public FieldsAreDate(List<String> fields){
			this.fields = fields;
		}
		@Override
		public Boolean apply(Map<String,String> in){
			for(String e : fields){
				if(!Utils.isDate(in.get(e))) return false;
			}
			return true;
		}
		//Functions	
		
	}
	public static class FieldsNotEmpty implements SerializableFunction<Map<String,String>,Boolean>{
		private List<String> fields;
		public FieldsNotEmpty(List<String> fields){
			this.fields = fields;
		}
		@Override
		public Boolean apply(Map<String,String> in){
			for(String e : fields){
				//System.out.println(in);
				//System.out.println(e);
				//System.out.println(in.get(e));
				if(in.get(e).equals("")) return false;
			}
			return true;
		}
		//Functions	
		
	}
	public static class ForceInteger implements Function<String,String> , Serializable{
			@Override
			public String apply(String in){
				if(!Utils.isInt(in)){
					return "0";
				}
				else return in;
			}	
		}
	public static class ForceNumeric implements Function<String,String> , Serializable{
			@Override
			public String apply(String in){
				if(!Utils.isDouble(in)){
					return "0.0";
				}
				else return in;
			}	
		}
	public static class ForceDate implements Function<String,String> , Serializable{
			@Override
			public String apply(String in){
				if(!Utils.isDate(in)){
					return "1970-01-01";
				}
				else return in;
			}	
	} 	  
}
