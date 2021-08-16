
import java.util.*;
import java.io.*;
public class Utils{
	/*
  	Splits input string on commas but not on commas that are enclosed in quotes
  	*/
	public static ArrayList<String> csvSplit(String in){
	  	boolean insideQuotes = false;
	  	ArrayList<String> results = new ArrayList<>();
	  	String currentToken = "";
	  	for(int i = 0 ; i < in.length() ; i++){
	  		if( in.charAt(i) == ',' && !insideQuotes){
	  			results.add(currentToken);
	  			currentToken = "";
	  		}
	  		else if( in.charAt(i) == '"' && !insideQuotes){ 
	  			insideQuotes = true;
	  			currentToken += '"';
	  		}
	  		else if( in.charAt(i) == '"' && insideQuotes){ 
	  			insideQuotes = false;
	  			currentToken += '"';
	  		}
	  		else {
	  			currentToken += in.charAt(i);
	  		}
			}
		//Adding last token
		results.add(currentToken);	
		return results;
	  }
	/**
		Creates a list holding the column names of a header
	**/  
	public static ArrayList<String> generateHeader(String line){
		return csvSplit(line);
	}  
	/**
		Creates a Row object with the specified header
	**/ 
	public static Map<String,String> parseRow(String line,ArrayList<String> header){
		Map<String,String> result = new HashMap<String,String>();
		ArrayList<String> tokens = csvSplit(line);
		//System.out.println(header.size());
		for(int i =0; i < header.size() ; i++){
			if(i < tokens.size()) result.put(header.get(i),tokens.get(i));
			else result.put(header.get(i),"");
		}
		return result;
	}
	public static Map<String,String> parseRow(List<String> columns,List<String> header){
		Map<String,String> result = new HashMap<String,String>();
		//System.out.println(header.size());
		for(int i =0; i < header.size() ; i++){
			result.put(header.get(i),columns.get(i));
		}
		return result;
	}
	public static List<String> rowToList(Map<String,String> row,List<String> header){
		List<String> result = new ArrayList<>();
		for(String e : header){
			result.add(row.get(e));
		}
		return result;
	}
	
	//IO
	public static String getHeaderFromFile(String filename){
  		Scanner sc = null;
  		String line = null;
  		try{
  			sc = new Scanner(new File(filename));
  			line = sc.nextLine();
  		}
  		catch(Exception e){
  			e.printStackTrace();
  			throw new RuntimeException("Problem with " + filename);
			}
			finally{
				if(sc != null) sc.close(); 
			}
		return line;
  	}
  	//Misc
  	public static boolean hasOpeningAndClosingQuotes(String in){
		if(in.length() >= 2 && in.charAt(0) == in.charAt(in.length()-1)&& in.charAt(0)  == '\"') return true;
		return false;
	}
	public static String removeOpeningAndClosingQuotes(String in){
		return in.substring(1,in.length()-1);
	}
	public static Boolean isInt(String in){
			try{
				int k = Integer.parseInt(in);
			}
			catch(Exception e){
				return false;
			}
			return true;
	}
	public static Boolean isDouble(String in){
			try{
				double k = Double.parseDouble(in);
			}
			catch(Exception e){
				return false;
			}
			return true;
	}
	public static Boolean isDate(String in){
			return in.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d");
	}
	public static String arrayToString(List<String> array){
		//System.out.println(header);
		String result = "";
      	for(String e: array){
      			result = result + e + ",";
    	  }
		  //Removing final comma
		  return result.substring(0,result.length()-1);
	}
	public static List<String> getFieldValues(Map<String,String> row,List<String> fields){
		ArrayList<String> result = new ArrayList<>();
		for(String e : fields){
			if(row.get(e) == null) throw new RuntimeException("field " +  e +" is null");
			result.add(row.get(e));
		}
		return result;
	} 
}
