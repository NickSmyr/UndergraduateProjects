package com.xoul.sm;
import java.util.*;
import java.io.*;

/*
  	Operations on the airbnb dataset and information regarding 
  	relationships between the csv files of the dataset
  */
  public class AirbnbDataset{
  	//Contains the primary keys of the listings
  	public Set<String> listingIds = Collections.synchronizedSet(new TreeSet<>());
  	//Listing File Header
	public ArrayList<String> listingHeader;
	//Temporary listing file
	public String listingFile;
	
	public void loadListing(String filename){
		SeparateHeaderFile res = Utils.unheaderify(filename);
		listingHeader = res.header;
		listingFile = res.headerlessFile;
	} 
	//////////////////////////////////////////Host fields and methods
	public Set<String> hostIds = Collections.synchronizedSet(new TreeSet<>());
	public ArrayList<String> hostHeader;
	public String hostFile;
	public String headerHostFile;
	public void loadHost(String filename){
		SeparateHeaderFile res = Utils.unheaderify(filename);
		hostHeader = res.header;
		hostFile = res.headerlessFile;
		headerHostFile = filename;
	} 		
	//////////////////////////////////////////Neighborhood fields and methods
	public Set<String> neighborhoodIds = Collections.synchronizedSet(new TreeSet<>());
	public ArrayList<String> neighborhoodHeader;
	public String neighborhoodFile;
	public String headerNeighborhoodFile;
	public void loadNeighborhood(String filename){
		SeparateHeaderFile res = Utils.unheaderify(filename);
		neighborhoodHeader = res.header;
		neighborhoodFile = res.headerlessFile;
		headerNeighborhoodFile = filename;
	}
	//////////////////////////////////////////Calendar fields and methods
	//public Set<String> calendarIds = Collections.synchronizedSet(new TreeSet<>());
	public ArrayList<String> calendarHeader;
	public String calendarFile;
	public String headerCalendarFile;
	public void loadCalendar(String filename){
		SeparateHeaderFile res = Utils.unheaderify(filename);
		calendarHeader = res.header;
		calendarFile = res.headerlessFile;
		headerCalendarFile = filename;
	}
  	
  }
