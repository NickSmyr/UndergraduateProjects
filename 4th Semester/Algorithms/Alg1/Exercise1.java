import java.util.*;
import java.io.*;
public class Exercise1 {

	//List.get() is O(1) time complexity provided that the input list is an arraylist
	public static Pair<Integer,Integer> firstAndLastIndexOf(List<Integer> array , int x){
		//If there is not a single x item
		int m = binarySearch(array,0,array.size(),x);
		if (m == -1) return new Pair<Integer,Integer>(-1,-1);
		//This search will return the index of the next item that is bigger than x 
		System.out.println(m);
		int high = array.size();
		int low = m;
		int i = (low + high)/2;
		while(high - low > 1){
			if( array.get(i) > x ) high = i;
			else low = i; //This means array.get(i) = x
			i = (low + high)/2; 
		}
		int lastIndex = low;
		//This search will return the index of the previous item that is smaller than x
		high = m;
		low = 0;
		i = (low + high)/2;
		while(high - low > 1){
			if( array.get(i) < x ) low = i;
			else high = i; //This means array.get(i) = x
			i = (low + high)/2; 
		}
		int firstIndex = high;	
		return new Pair<Integer,Integer>(firstIndex,lastIndex);
			
	}
	/**
		Searches for an element at a given list
		If the element is not on the list the method returns -1.
		the "to" position is non inclusive which means array.get(to) will not be 
		checked if it has the item 
	**/
	public static int binarySearch(List<Integer> array ,int from , int to,int x){
		if(from == to && from < array.size() && array.get(from) == x) return from;
		else if(from == to) return -1;
		int mid = (from + to)/2;
		if( array.get(mid) < x) return binarySearch(array , mid+1, to,x);
		else return binarySearch(array,from,mid,x); 
		
	}
	public static void main(String[] args) throws IOException{
		List<Integer> list = Utilities.convertFileSequenceToList(new File(args[0]));
		System.out.println(list);
		System.out.println(firstAndLastIndexOf(list,5));	
	}
}
