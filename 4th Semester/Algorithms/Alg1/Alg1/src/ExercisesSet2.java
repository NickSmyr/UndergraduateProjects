import java.io.File;
import java.util.List;

class ExercisesSet2
{

	/*
	* RUNNING FROM COMMAND LINE
	* Assuming that you have .java and input files in the same folder, do the following:
	*	- javac ExercisesSet2.java
	*	- java ExercisesSet2 2.1-sm.txt 2.2-sm.txt 360
	*
	*
	* RUNNING FROM IDE
	* It would be helpful for us, if you kept all the files in the same folder.
	*
	*/

	public static void main(String args[])
	{
		String ex1fileName = args[0];
		String ex2fileName = args[1];
		int ex2Calories = Integer.parseInt(args[2]);

		exercise1( ex1fileName );
		exercise2( ex2fileName, ex2Calories );

	}

	public static void exercise1( String fileName )
	{
		int[] data = turnListIntoTable( fileName );
		Exercise1 exercise1 = new Exercise1( data );
		exercise1.printInputData();
		exercise1.solveExercise1();
		exercise1.printSolution();		
	}

	public static void exercise2( String fileName, int totalCalories )
	{
		int[][] data = turnListsInto2Dtable( fileName );
		Exercise2 exercise2 = new Exercise2( data, totalCalories );
		exercise2.solveExercise2();
		exercise2.printSolution();		
	}

	public static int[] turnListIntoTable( String fileName )
	{
		List<Integer> list = null;
		int[] data = null;

		try
		{
			File file = new File(fileName); 
			list = (List)Utilities.convertFileSequenceToList(file); // retrieve data, create Lists. 
			data = new int[list.size()]; // create 2-d table  
 			for( int i=0; i<data.length; i++ )
				data[i] = list.get(i);	
		}
		catch( Exception e )
		{
			System.out.println("- Problem with file-reading.");
		}	

		return data;
	}

	public static int[][] turnListsInto2Dtable( String fileName )
	{
		File file = new File(fileName);		
		List<List<Integer>> list = null;
		int [][] data = null;

		try
		{
			list = (List)Utilities.convertFileMatrixToListOfLists(file); // retrieve data, create Lists. 
			int rows = list.size(); // get the rows
			int columns = ((List<Integer>)list.get(0)).size(); // get the columns
			data = new int[rows][columns]; // create 2-d table  
 
			for( int i=0; i<rows; i++ )
			{
				List<Integer> rowList =  (List<Integer>)list.get(i);
				for( int j=0; j<columns; j++ )
					data[i][j] = rowList.get(j);	
			}

		}
		catch( Exception e )
		{
			System.out.println("- Problem with file-reading.");
		}	

		return data;
	}

}
