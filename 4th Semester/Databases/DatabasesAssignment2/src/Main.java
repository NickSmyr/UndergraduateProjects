import java.util.*;
import java.io.*;
import java.util.function.*;
public class Main{
	public List<String> cities = Arrays.asList("boston/" , "denver/" , "portland/");
	
	
	public Set<String> neighborhoodIds = new TreeSet<String>();
	public List<String> neighborhoodHeader;
	
	public Set<String> hostIds = new TreeSet<String>();
	public List<String> hostHeader;
	
	public Set<String> listingIds = new TreeSet<String>();
	public Set<String> listing_neighborhood_fkey = new TreeSet<String>();
	public Set<String> listing_host_fkey = new TreeSet<String>();
	public List<String> listingHeader;
	public Set<String> tempListingIds = new TreeSet<String>();
	
	
	public Set<String> calendarIds = new TreeSet<String>();
	public Set<String> calendar_fkey = new TreeSet<String>();
	public List<String> calendarHeader;
	
	public Set<String> reviewIds = new TreeSet<String>();
	public Set<String> review_fkey = new TreeSet<String>();
	public List<String> reviewHeader;
	
	public Set<String> calendar_summaryIds = new TreeSet<String>();
	public Set<String> calendar_summary_fkey = new TreeSet<String>();
	public List<String> calendar_summaryHeader;
	
	public Set<String> summary_listingIds = new TreeSet<String>();
	public Set<String> summary_listing_fkey = new TreeSet<String>();
	public List<String> summary_listingHeader;
	
	public Set<String> summary_reviewIds = new TreeSet<String>();
	public Set<String> summary_review_fkey = new TreeSet<String>();
	public List<String> summary_reviewHeader;
	
	public Set<String> amenityIds = new TreeSet<String>();
	public Set<String> amenityfkey = new TreeSet<String>();
	public List<String> amenityHeader;
	
	public Set<String> listing2amenityIds = new TreeSet<String>();
	public Set<String> listing2amenityfkey = new TreeSet<String>();
	public List<String> listing2amenityHeader;
	public void runMain(){
		
		neighborhoodHeader = Utils.csvSplit(Utils.getHeaderFromFile("../data/boston/neighborhood.csv"));
		hostHeader = Utils.csvSplit(Utils.getHeaderFromFile("../data/boston/host.csv"));
		listingHeader = Utils.csvSplit(Utils.getHeaderFromFile("../data/boston/listing.csv"));
		calendarHeader = Utils.csvSplit(Utils.getHeaderFromFile("../data/boston/calendar.csv"));
		reviewHeader = Utils.csvSplit(Utils.getHeaderFromFile("../data/boston/review.csv"));
		calendar_summaryHeader = Utils.csvSplit(Utils.getHeaderFromFile("../data/boston/calendar_summary.csv"));
		summary_listingHeader = Utils.csvSplit(Utils.getHeaderFromFile("../data/boston/summary_listing.csv"));
		summary_reviewHeader = Utils.csvSplit(Utils.getHeaderFromFile("../data/boston/summary_review.csv"));
		amenityHeader = Utils.csvSplit(Utils.getHeaderFromFile("../data/boston/amenity.csv"));
		listing2amenityHeader = Utils.csvSplit(Utils.getHeaderFromFile("../data/boston/amenity.csv"));
		
		ArrayListReceiver receiver = new ArrayListReceiver().withPrimaryKeyCheck(true);
		//Neighbourhoood
		System.err.println("[PROCESSING NEIGHBOURHOOD]");
		receiver.primaryIds = neighborhoodIds;
		receiver.primaryFields = Arrays.asList("neighbourhood_name","zip_code");
		receiver.header = neighborhoodHeader;		
		receiver.predicate = (x) -> {
			return Utils.isInt(x.get("zip_code"));
			};
		receiver.outputFile = "garbage/neighborhood.csv";	
		receiver.build();	
		receiver.readFrom("../data/austin/neighborhood.csv");
		receiver.rowsOutput = 0;
		receiver.outputFile = "neighborhood.csv";
		receiver.build();
		for( String city : cities){	
			receiver.readFrom("../data/"+city+"neighborhood.csv");
		}	
		int neighborhoodsParsed = receiver.rowsParsed;
		int neighborhoodsOutput = receiver.rowsOutput;
		
		receiver.reset();
		//Host
		System.err.println("[PROCESSING HOST]");
		receiver.withPrimaryKeyCheck(true);
		receiver.primaryIds = hostIds;
		receiver.primaryFields = Arrays.asList("id");
		receiver.header = hostHeader;
		//Filling hostIds with austin data
		receiver.outputFile = "garbage/host.csv";
		receiver.build();
		receiver.readFrom("../data/austin/host.csv");
		receiver.rowsOutput = 0;
		receiver.outputFile = "host.csv";
		receiver.build();
		for(String city : cities){
			
			receiver.readFrom("../data/"+city+"host.csv");
		}
		int hostsParsed = receiver.rowsParsed;
		int hostsOutput = receiver.rowsOutput;
		//Listing
		System.err.println("[PROCESSING LISTING]");
		receiver.reset();
		receiver.withPrimaryKeyCheck(true).withForeignKeyCheck(true);
		receiver.primaryIds = listingIds;
		receiver.primaryFields = Arrays.asList("id");
		receiver.foreignIds = hostIds;
		receiver.foreignFields = Arrays.asList("host_id");
		receiver.outputHeader = new ArrayList<String>(listingHeader);
		receiver.outputHeader.remove("calculated_host_listings_count");
		receiver.header = Utils.csvSplit(Utils.getHeaderFromFile("../data/austin/listing.csv"));
		receiver.outputFile = "temp/austin_listing.csv";
		receiver.build();
		receiver.readFrom("../data/austin/listing.csv");
		receiver.outputFile = "temp/listing.csv";
		receiver.build();	
		for(String city:cities){
			String filename = "../data/"+city+"listing.csv";
			receiver.header = Utils.csvSplit(Utils.getHeaderFromFile(filename));
			receiver.readFrom(filename);			
		}
		int totalListings = listingIds.size();
		int listingsParsed = receiver.rowsParsed;
		//receiver.reset();
		List<String> tempHeader = receiver.outputHeader;
		receiver.reset();
		receiver.withForeignKeyCheck(true).withPrimaryKeyCheck(true);
		receiver.primaryIds = tempListingIds;
		receiver.primaryFields = Arrays.asList("id");
		receiver.foreignIds = neighborhoodIds;
		receiver.foreignFields = Arrays.asList("neighborhood","neighbourhood_cleansed");
		receiver.header = tempHeader;	
		receiver.outputHeader = tempHeader;
		receiver.outputFile = "garbage/listing.csv";
		receiver.build();
		receiver.readFrom("temp/austin_listing.csv");
		receiver.outputFile = "listing.csv";
		receiver.build();
		receiver.rowsOutput = 0;
		receiver.readFrom("temp/listing.csv");
		int listingsOutput = receiver.rowsOutput;
		listingIds = tempListingIds; 
		
		///CAlendar
		System.err.println("[PROCESSING Calendar]");
		receiver.reset();
		receiver.withForeignKeyCheck(true).withPrimaryKeyCheck(true);
		receiver.primaryIds = calendarIds;
		receiver.primaryFields = Arrays.asList("listing_id","date");
		receiver.foreignIds = listingIds;
		receiver.foreignFields = Arrays.asList("listing_id");
		receiver.header = calendarHeader;
		receiver.outputFile = "garbage/calendar.csv";
		receiver.build();
		receiver.readFrom("../data/austin/calendar.csv");
		receiver.rowsOutput = 0;
		receiver.outputFile = "calendar.csv";
		receiver.build();
		for(String city : cities){
			String filename = "../data/"+city+"calendar.csv";
			System.out.println("Reading from" + filename );	
			receiver.readFrom(filename);			
		}
		int calendarEntriesOutput = receiver.rowsOutput;
		int calendarEntriesParsed = receiver.rowsParsed;
		//Review
		System.err.println("[PROCESSING REVIEW]");
		//calendarIds = null; // Wastes a lot of memory by existing
		receiver.reset();
		receiver.withForeignKeyCheck(true).withPrimaryKeyCheck(true);
		receiver.primaryIds = reviewIds;
		receiver.primaryFields = Arrays.asList("id");
		receiver.foreignIds = listingIds;
		receiver.foreignFields = Arrays.asList("listing_id");
		receiver.header = reviewHeader;
		receiver.outputFile = "garbage/review.csv";
		receiver.build();
		receiver.readFrom("../data/austin/review.csv");
		receiver.rowsOutput = 0;
		receiver.outputFile = "review.csv";
		receiver.build();
		for(String city:cities){
			String filename = "../data/"+city+"review.csv";
			receiver.readFrom(filename);			
		}
		int reviewsOutput = receiver.rowsOutput;
		int reviewsParsed = receiver.rowsParsed;
		
		////////////Calendar_summary
		System.err.println("[PROCESSING CALENDAR_SUMMARY]");
		receiver.reset();
		receiver.withForeignKeyCheck(true).withPrimaryKeyCheck(true);
		receiver.primaryIds = calendar_summaryIds;
		receiver.primaryFields = Arrays.asList("listing_id","from_date");
		receiver.foreignIds = listingIds;
		receiver.foreignFields = Arrays.asList("listing_id");
		receiver.header = calendar_summaryHeader;
		receiver.outputFile = "garbage/calendar_summary.csv";
		receiver.predicate = (x) -> {
			return x.get("listing_id")!=null && x.get("from_date") != null;
		};
		receiver.build();
		receiver.readFrom("../data/austin/calendar_summary.csv");
		receiver.rowsOutput = 0;
		receiver.outputFile = "calendar_summary.csv";
		receiver.build();
		for(String city:cities){
			String filename = "../data/"+city+"calendar_summary.csv";
			receiver.readFrom(filename);			
		}
		int calendar_summaryOutput = receiver.rowsOutput;
		int calendar_summaryParsed = receiver.rowsParsed;
		////////////Summary_Listing
		System.err.println("[PROCESSING SUMMARY_LISTING]");
		receiver.reset();
		receiver.withForeignKeyCheck(true).withPrimaryKeyCheck(true);
		receiver.primaryIds = summary_listingIds;
		receiver.primaryFields = Arrays.asList("id");
		receiver.foreignIds = listingIds;
		receiver.foreignFields = Arrays.asList("id");
		receiver.header = summary_listingHeader;
		receiver.outputFile = "garbage/summary_listing.csv";
		receiver.consumer = (x) ->
		{
			if(!Utils.isInt(x.get("zip_code"))) x.put("zip_code","0");
			x.put("calculated_host_listings_count","0");
		};
		receiver.build();
		receiver.readFrom("../data/austin/summary_listing.csv");
		receiver.rowsOutput = 0;
		receiver.outputFile = "summary_listing.csv";
		receiver.build();
		for(String city:cities){
			String filename = "../data/"+city+"summary_listing.csv";
			receiver.readFrom(filename);			
		}
		int summary_listingOutput = receiver.rowsOutput;
		int summary_listingParsed = receiver.rowsParsed;
		////////////Summary_Listing
		System.err.println("[PROCESSING SUMMARY_REVIEW]");
		receiver.reset();
		receiver.withForeignKeyCheck(true).withPrimaryKeyCheck(true);
		receiver.primaryIds = summary_reviewIds;
		receiver.primaryFields = Arrays.asList("listing_id","date");
		receiver.foreignIds = listingIds;
		receiver.foreignFields = Arrays.asList("listing_id");
		receiver.header = summary_reviewHeader;
		receiver.outputFile = "garbage/summary_review.csv";
		receiver.build();
		receiver.readFrom("../data/austin/summary_review.csv");
		receiver.rowsOutput = 0;
		receiver.outputFile = "summary_review.csv";
		receiver.build();
		for(String city:cities){
			String filename = "../data/"+city+"summary_review.csv";
			receiver.readFrom(filename);			
		}
		int summary_reviewOutput = receiver.rowsOutput;
		int summary_reviewParsed = receiver.rowsParsed;
		//Amenity
		receiver.reset();
		int amenityOutput = 0;
		int amenityParsed = 0;
		System.err.println("[PROCESSING AMENITIES AND HAS AMENITY]");
		//amenity_id - > amenity_name
		final Map<String,String> amenities = new HashMap<String,String>();
		//amenity_id -> amenity_id
		final Map<String,String> bostonTranslation = new HashMap<String,String>();
		final Map<String,String> portlandTranslation = new HashMap<String,String>();
		final Map<String,String> denverTranslation = new HashMap<String,String>();
		
		receiver.withForeignKeyCheck(false).withPrimaryKeyCheck(true);
		receiver.primaryIds = amenityIds;
		receiver.primaryFields = Arrays.asList("amenity_id");
		receiver.header = Utils.csvSplit(Utils.getHeaderFromFile("../data/austin/amenity.csv"));
		receiver.outputFile = "garbage/amenity.csv";
		receiver.consumer = new InitConsumer(amenities);
		receiver.build();
		receiver.readFrom("../data/austin/amenity.csv");
		receiver.rowsOutput = 0;
		receiver.outputHeader = Utils.csvSplit(Utils.getHeaderFromFile("../data/austin/amenity.csv"));
		receiver.headerRename = new HashMap<>();
		receiver.headerRename.put("amenity_name","amenity");
		receiver.header = Utils.csvSplit(Utils.getHeaderFromFile("../data/boston/amenity.csv"));
		receiver.outputFile = "amenity.csv";
		receiver.build();
		//if(receiver.outputFile.equals("amenity.csv"))throw new RuntimeException(receiver.header.toString());
		//Boston amenites
		receiver.header = Utils.csvSplit(Utils.getHeaderFromFile("../data/boston/amenity.csv"));
		receiver.consumer = new MapConsumer(bostonTranslation,amenities);
		receiver.readFrom("../data/boston/amenity.csv");
		//Portland ameniteis
		receiver.header = Utils.csvSplit(Utils.getHeaderFromFile("../data/portland/amenity.csv"));
		receiver.consumer = new MapConsumer(portlandTranslation,amenities);
		receiver.readFrom("../data/portland/amenity.csv");
		//Denver amenities
		receiver.header = Utils.csvSplit(Utils.getHeaderFromFile("../data/denver/amenity.csv"));
		receiver.consumer = new MapConsumer(portlandTranslation,amenities);
		receiver.readFrom("../data/denver/amenity.csv");
		amenityOutput  += receiver.rowsOutput;
		amenityParsed += receiver.rowsParsed;
		receiver.reset();
		
		
		///LISTING2AMENITY
		int listing2amenityOutput = 0;
		int listing2amenityParsed = 0;
		System.err.println("[PROCESSING AMENITIES AND HAS AMENITY]");
		receiver.withForeignKeyCheck(true).withPrimaryKeyCheck(true);
		receiver.primaryIds = listing2amenityIds;
		receiver.primaryFields = Arrays.asList("listing_id","amenity_id");
		receiver.foreignIds = listingIds;
		receiver.foreignFields = Arrays.asList("listing_id");
		receiver.header = Utils.csvSplit(Utils.getHeaderFromFile("../data/austin/listing2amenity.csv"));
		receiver.outputFile = "temp/austin_listing2amenity.csv";
		receiver.build();
		receiver.readFrom("../data/austin/listing2amenity.csv");
		receiver.rowsOutput = 0;
		
		receiver.outputFile = "temp/listing2amenity.csv";
		receiver.build();
		
		//Boston
		String filename = "../data/boston/listing2amenity.csv";
		receiver.header = Utils.csvSplit(Utils.getHeaderFromFile(filename));
		receiver.consumer = new listing2AmenityConsumer(bostonTranslation);
		receiver.readFrom(filename);
		//Portland
		filename = "../data/portland/listing2amenity.csv";
		receiver.header = Utils.csvSplit(Utils.getHeaderFromFile(filename));
		receiver.consumer = new listing2AmenityConsumer(portlandTranslation);
		receiver.readFrom(filename);
		//Denver
		filename = "../data/denver/listing2amenity.csv";
		receiver.header = Utils.csvSplit(Utils.getHeaderFromFile(filename));
		receiver.consumer = new listing2AmenityConsumer(denverTranslation);
		receiver.readFrom(filename);
		listing2amenityParsed +=receiver.rowsParsed;
		
		
		receiver.reset();
		receiver.withForeignKeyCheck(true).withPrimaryKeyCheck(true);
		receiver.primaryIds = new TreeSet<>();
		receiver.primaryFields = Arrays.asList("listing_id","amenity_id");
		receiver.foreignIds = listingIds;
		receiver.foreignFields = Arrays.asList("listing_id");
		receiver.header = Utils.csvSplit(Utils.getHeaderFromFile("temp/listing2amenity.csv"));
		receiver.outputFile = "listing2amenity.csv";
		receiver.build();
		receiver.readFrom("temp/listing2amenity.csv");
		listing2amenityOutput += receiver.rowsOutput;
		
		receiver.reset();
		System.out.println("Neighbourhoods Output = " + neighborhoodsOutput);
		System.out.println("Neighbourhoods Parsed = " + neighborhoodsParsed);
		System.out.println("Hosts Output = " + hostsOutput);
		System.out.println("Hosts Parsed = " + hostsParsed);
		System.out.println("Listings Output = " + listingsOutput);
		System.out.println("Listings Parsed = " + listingsParsed);
		System.out.println("Calendar Entries Output = " + calendarEntriesOutput);
		System.out.println("Calendar Entries Parsed = " + calendarEntriesParsed);
		System.out.println("Reviews Output = " + reviewsOutput);
		System.out.println("Reviews Parsed = " + reviewsParsed);
		System.out.println("Summary Calendar Entries Output = " + calendar_summaryOutput);
		System.out.println("Summary Calendar Entries Parsed = " + calendar_summaryParsed);
		System.out.println("Summary Listings Output = " + summary_listingOutput);
		System.out.println("Summary Listings Parsed = " + summary_listingParsed);
		System.out.println("Summary Reviews Output = " + summary_reviewOutput);
		System.out.println("Summary Reviews Parsed = " + summary_reviewParsed);
		System.out.println("Amenities output = " + amenityOutput);
		System.out.println("Amenities parsed = " + amenityParsed);
		System.out.println("Listing2Amenity output = " + listing2amenityOutput);
		System.out.println("Listing2Amenity parsed = " + listing2amenityParsed);
		System.out.println();
		
		System.out.println("Total Neighborhoods " + neighborhoodIds.size());
		System.out.println("Total Listings " + totalListings);
		System.out.println("Total Hosts " + hostIds.size());
		
		System.out.println("Total Calendar entries " + calendarIds.size());
		System.out.println("Total Reviews " + reviewIds.size());
		System.out.println("Total Calendar Summary entries " + calendar_summaryIds.size());
		System.out.println("Total Summary Listing entries " + summary_listingIds.size());
		System.out.println("Total Summary Review entries " + summary_reviewIds.size());
		
		System.out.println("Total Amenities :" + amenityIds.size());
		System.out.println("Total Listin2Amenity entries " + listing2amenityIds.size());
		
		//System.out.println("boston translation" + bostonTranslation);
		//System.out.println("portland translation" + portlandTranslation);
		//System.out.println("denver translation" + denverTranslation);
		//System.out.println(amenityIds);
		//System.out.println(amenities);
		//System.out.println("listing.primaryIds.contains(\"13968660\") : " + listingIds.contains("13968660"));
		//System.out.println("listing.primaryIds.contains(\"11889576\") : " + listingIds.contains("11889576"));
		
	}
	public static void main(String[] args){
		new Main().runMain();
	}
	public static class InitConsumer implements Consumer<Map<String,String>>{
		private Map<String,String> map;
		public InitConsumer(Map<String,String> map){
			this.map = map;
		}
		public void accept(Map<String,String> in){
			map.put(in.get("amenity_id"),in.get("amenity"));			
		}
	}
	public static class MapConsumer implements Consumer<Map<String,String>>{
		private Map<String,String> translation;
		private Map<String,String> amenities;
		public MapConsumer(Map<String,String> translation, Map<String,String> amenities){
			this.translation = translation;
			this.amenities = amenities;
		}
		public void accept(Map<String,String> in){
			//If there already exists an amenity with the same id and different name
			//The new amenity must be added with a different id to the amenities
			if(amenities.get(in.get("amenity_id")) != null && !(amenities.get(in.get("amenity_id")).equals(in.get("amenity_name"))) ){
				//System.out.println("CRITICAL ::: " + in);
				//System.out.println("WHat already exists is :" + in.get("amenity_id") +" , " + amenities.get(in.get("amenity_id")));
				int i = 1;
				while(amenities.get(String.valueOf(i)) != null){
					i++;
				}
				//This means that there is no amenity with id i
				
				translation.put(in.get("amenity_id"),String.valueOf(i));
				//Changing amenity id
				//System.out.println("Old id = " + in.get("amenity_id"));
				//System.out.println("New id = " + String.valueOf(i));
				//System.out.println();
				//System.out.println("ISNOTNOTOKAY");
				//System.out.println("Old value = " + in);
				
				in.put("amenity_id",String.valueOf(i));
				amenities.put(in.get("amenity_id"),in.get("amenity_name"));
				//System.out.println("New value = " + in);
				
			}
			else{
				//System.out.println("ISOKAY");
				amenities.put(in.get("amenity_id"),in.get("amenity_name"));
			}			
		}
	}
	/**
		Fixes amenity_id references
	**/
	public static class listing2AmenityConsumer implements Consumer<Map<String,String>>{
		private Map<String,String> translation;
		public listing2AmenityConsumer(Map<String,String> translation){
			this.translation = translation;
		}
		public void accept(Map<String,String> in){
			if(translation.get(in.get("amenity_id")) != null){
				//System.out.println("Changing... " + in);
				in.replace("amenity_id",translation.get(in.get("amenity_id")));
				//System.out.println("New One... " + in);
				//System.out.println();
			}
		}
	}
}
