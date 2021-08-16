import java.util.*;
import java.io.*;
public class Exercise2{
	//List.get() is O(1) time complexity provided that the input list is an arraylist
	//Inclusive high
	public static void quickSort(List<Integer> array,int low , int high){
		
		if(low >= high) {			
			return;	
		}	
		int randomPivot = (int) Math.random() * (high - low) + low;
		Pair<Integer,Integer> edges = partition(array,low,high,array.get(randomPivot));

		quickSort(array, 0 , edges.x -1); 
		quickSort(array , edges.y + 1 , high);	
	}
	public static Pair<Integer,Integer> partition(List<Integer> array,int low , int high,int x){
		int[] aux = new int[array.size()]; 
		//Copying elements to auxillary array		
		for(int i = low; i <= high ; i++){
			aux[i] = array.get(i);
		}
		int smaller = low;
		int greater = high;
		//Smaller than x elements are appended to the start of the array range
		//Greater than x elements are appended to the end of the array range
		for(int i = low; i <= high ; i++){
			if(aux[i] < x) array.set(smaller++,aux[i]);
			if(aux[i] > x) array.set(greater--,aux[i]);
		}
		for(int i = smaller; i <= greater ; i++){
			array.set(i,x);
		}
		return new Pair<Integer,Integer>(smaller,greater);
	}
	public static void main(String[] args)throws IOException{
		List<Integer> list = Utilities.convertFileSequenceToList(new File(args[0]));
		System.out.println(list);
		quickSort(list,0,list.size()-1);
		System.out.println(list);
	}
}
