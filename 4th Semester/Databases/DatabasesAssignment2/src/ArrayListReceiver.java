import java.util.function.*;
import java.util.*;
import java.io.*;
public class ArrayListReceiver{
	public String outputFile;
	public PrintWriter wr;
	
	public List<String> header;
	public List<String> outputHeader;
	public List<String> primaryFields;
	public Set<String> primaryIds;
	
	public List<String> foreignFields;	
	public Set<String> foreignIds;
	

	public Predicate<Map<String,String>> predicate;
	public Consumer<Map<String,String>> consumer;
	
	public Map<String,String> headerRename;
	
	public void output(ArrayList<String> in){
		rowsParsed++;
		Map<String,String> currentRow = Utils.parseRow(in,header);
		//if(outputFile.equals("temp/listing2amenity.csv")) System.err.println("Before cons = " + currentRow);
		boolean dropped = false;
		boolean isDuplicate = false;
		if(consumer!= null){
			consumer.accept(currentRow);
		}
		//if(outputFile.equals("amenity.csv")) System.err.println("After cons = " +currentRow);
		if(predicate != null){
			if(!predicate.test(currentRow)){
			return;
			}
		}
		if(pkCheck){
			if(!primaryIds.contains(Utils.arrayToString(Utils.getFieldValues(currentRow,primaryFields))))	
				primaryIds.add(Utils.arrayToString(Utils.getFieldValues(currentRow,primaryFields)));
			else{
				//if(outputFile.equals("temp/listing2amenity.csv")) System.err.println("DROPPING " +currentRow);
				//if(outputFile.equals("temp/listing2amenity.csv")) System.err.println();
				if(outputFile.equals("temp/listing2amenity.csv")) System.err.println("DROPPING DUE TO FOREIGN " +currentRow);
				if(outputFile.equals("listing2amenity.csv")) System.err.println("DROPPING DUE TO FOREIGN " +currentRow);
				return;			
			}
		}
		if(headerRename != null){
			for(String  field : header){
				currentRow.put(headerRename.get(field),currentRow.get(field));
			}
			
		}
		//In order to maintain primary key set integrity if pkCheck is disabled each row must be unique 
		
		if(fkCheck){
			if(!foreignIds.contains(Utils.arrayToString(Utils.getFieldValues(currentRow,foreignFields)))){ 	
				if(pkCheck){
					//This means that the row was a new one and so the primaryKeySet was updated
					primaryIds.remove(Utils.arrayToString(Utils.getFieldValues(currentRow,primaryFields)));	
				}
				else{
					//This means that pkCheck was not enabled so the row must be unique
					primaryIds.remove(Utils.arrayToString(Utils.getFieldValues(currentRow,primaryFields)));
					//if(outputFile.equals("listing.csv") || outputFile.equals("temp/listing.csv")) System.err.println("Dropping due to fk" +Utils.arrayToString(Utils.getFieldValues(currentRow,primaryFields)));	
				}
				if(outputFile.equals("temp/listing2amenity.csv")) System.err.println("DROPPING DUE TO FOREIGN " +currentRow);
				if(outputFile.equals("listing2amenity.csv")) System.err.println("DROPPING DUE TO FOREIGN " +currentRow);		
				return;
			 }
		}
		rowsOutput++;
		//if(outputFile.equals("amenity.csv")) System.err.println("Writing output" +currentRow);
		//System.out.println("asdasd" + Utils.arrayToString((Utils.rowToList(currentRow,header))));
		if(outputHeader == null) wr.println(Utils.arrayToString((Utils.rowToList(currentRow,header))));
		else wr.println(Utils.arrayToString((Utils.rowToList(currentRow,outputHeader))));
		
	}
	public boolean fkCheck = false;
	public ArrayListReceiver withForeignKeyCheck(boolean in){
		fkCheck = in;
		return this;
	}
	public boolean pkCheck = false;
	public ArrayListReceiver withPrimaryKeyCheck(boolean in){
		pkCheck = in;
		return this;
	}
	public void withOutputFile(String file){
		outputFile = file;
	}
	public void build(){
		if(outputFile == null){
			throw new RuntimeException("No output file specified :" + outputFile);
		}
		try{
			if(wr!= null) wr.close();
			wr = new PrintWriter(new File(outputFile));
			if(outputHeader != null){
				wr.println(Utils.arrayToString(outputHeader));
				//System.out.println("Output header " + outputFile);
			}
			else if(header!= null){
				wr.println(Utils.arrayToString(header));
				//System.out.println("Output header " + outputFile);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("Couldn't create output file :" + outputFile +" : " +e.getMessage());
		}
		System.err.println("CREATED OUTPUT FILE " + outputFile);	
	}
	public void reset(){
		outputFile =  null;
		if(wr!= null) wr.close();
		wr = null;		
		header = null ;
		outputHeader = null;
		primaryFields = null;
		primaryIds = null;	
		foreignFields = null;	
		foreignIds = null;
		predicate = null;
		consumer = null;
		rowsParsed = 0;
		rowsOutput = 0;
		pkCheck = false;
		fkCheck = false;
		headerRename = null;
		
	}
	public void readFrom(String filename)  {
	/**
		System.out.println("------READING------");		
		System.out.println("|outputFIle = "+ outputFile+"|");	
		System.out.println("|wr = "+ wr+"|");
		System.out.println("|header = "+ header+"|");	
		System.out.println("|outputHeader = "+ outputHeader+"|");
		System.out.println("|primaryFields = "+ primaryFields+"|");
		System.out.println("|foreignFields = "+ foreignFields+"|");				
		System.out.println("-------------------");
		System.out.println();
	**/			
    	Scanner sc= null;
		try{
			sc = new Scanner(new File(filename));
     		// ... Use your favorite Java CSV library ...
     		//... c.output(next csv record) ...
     		sc.nextLine();//SKip header
     		String result = "";
     		String line = "";
     		ArrayList<String> csv = new ArrayList<String>();
     		boolean insideQuotes = false;
     		while(sc.hasNextLine()){
		   					   		
		   		line = sc.nextLine() + "\n";
		   		for(char e: line.toCharArray()){
		   		
		   			//if(outputFile.matches("temp/listing.csv")){
		   			
		   			//if(csv.size()<header.size())System.out.println("Reading row column: "+header.get(csv.size()) + " total Tags " + csv.size());
		   			//else System.out.println("What  happened : " + result );
		   			//try{Thread.sleep(5);}catch(Exception z){}
		   			//System.out.println("Current character =" + e + "inside Quotes ?"+ insideQuotes);
		   			//System.out.println(result);
					//}
		   			
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
		   				if(csv.size() >= header.size()){
		   					//Converting array of tags to a csv row string
		   				
		   					output(new ArrayList<String>(csv));
		   					csv = new ArrayList<String>();
		   					//System.out.println("NEW RECORDout");
		   					break;
	   					}
	   					
		   			}
		   			else {
		   				result += e;
		   			}
		   		}
		   		if(insideQuotes) continue;
		   		if(csv.size() == 0) continue;
		   		else{
		   			csv.add(new String(result).trim());
	   				result = "";
	   				if(csv.size() >= header.size()){
	   					//Converting array of tags to a csv row string
	   				
	   					output(new ArrayList<String>(csv));
	   					csv = new ArrayList<String>();
	   					//System.out.println("NEW RECORDout");
	   					
	   				}
		   		}
		   		 
			}
     	}
     	catch(IOException e){
     		System.err.println("Critical error with file reading + "+	 e.getMessage());
     		throw new RuntimeException(e);
     	}
     	finally{
     		if(sc != null) sc.close();
     	}
	}
	//METRICS
	public int rowsOutput = 0;
	public int rowsParsed = 0;
}
