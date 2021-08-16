import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class Exercise1
{

	private int[] leaves;
	private int[] solution;

	public Exercise1( int[] leaves )
	{
		this.leaves = leaves;
	}

	public void solveExercise1()
	{
		/*
		OPT(i) = min(OPT(j) + 1) (for every j through which i is accessible )
								(the frog can jump from j to i)
		 */
		ArrayList<ArrayList<Integer>> solutions = new ArrayList<ArrayList<Integer>>();
		solutions.add(new ArrayList<Integer>());
		solutions.get(0).add(0);

		int[] OPT = new int[leaves.length];
		// prev[i] represents the previous leaf in the optimal path until the i+1 leaf
		int[] prev = new int[leaves.length];
		OPT[0] = 0;
		prev[0] = -1;
		for(int i = 1 ; i < OPT.length;i++){
			//Calculating minimum times the frog can jump
			//until it reaches position i
			int min = leaves.length; //Every shortcut will have less jumps than going to leaf i by jumping one leaf at a time
			int index = -1;
			for(int j = 0; j < i; j++){
				//if the frog can jump from j to i
				if(leaves[j] >= i-j && OPT[j] + 1 < min ) {
					min = OPT[j] + 1;
					index = j;
				}

			}
			if(index == -1) return;
			OPT[i] = min;
			prev[i] = index;
			
		}
		
		//Calculating solution
		int[] stack = new int[leaves.length];
		int sp = 0;
		
		for(int i = leaves.length-1; i != -1 ; ){
			stack[sp++] = i;
			i = prev[i];
		}
		solution = new int[sp];
		for(int i = 0;sp>0;i++){
			solution[i] = stack[--sp] + 1; 
		}
		System.out.printf("Solution :\n %d ",OPT[leaves.length-1]);
		System.out.print("{ ");
		for(int i =0; i < solution.length ; i++){
			System.out.print(solution[i]+" ");
		}
		System.out.println("}");
		
	}

	public void printSolution()
	{
		if(solution!=null)
			for( int i=0; i<solution.length; i++ )
			{
				System.out.print( solution[i]+" " );
			}
		else
			System.out.print( " Solution for Exercise1 is empty." );
				
		System.out.println();
	}


	public void printInputData()
	{
		if( leaves !=null )
		{
			for( int i=0; i<leaves.length; i++ )
			{
				System.out.print( leaves[i]+" " );
			}
			System.out.println();
		} 
		else
			System.out.println("Input table is null.");
	}


}
