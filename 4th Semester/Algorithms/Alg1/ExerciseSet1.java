import java.io.File;
import java.util.List;
import java.io.IOException;
public class ExerciseSet1 {
	public static void main(String[] args) throws IOException{
		List<Integer> list = Utilities.convertFileSequenceToList(new File("1.1-sm.txt")); 
		System.out.printf("Input list : %s%n",list);
		int x = 547;
		System.out.printf("First and last index of %d : %s%n" ,x, Exercise1.firstAndLastIndexOf(list,x) );

		list = Utilities.convertFileSequenceToList(new File("1.2-sm.txt"));
		Exercise2.quickSort(list,0,list.size()-1) ;
		System.out.printf("Input list : %s%n",list);
		System.out.printf("Sorted list :  %s%n" , list);	
	}
}
