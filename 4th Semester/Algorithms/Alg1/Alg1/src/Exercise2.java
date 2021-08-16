import java.util.Arrays;

class Exercise2 {

	private int[][] caloriesAndFat; // 2D array. First row has the calories. Second row has the fat.
	private int wantedCalories; // Total calories.
	private int[] solution; // The selected products.

	public Exercise2(int[][] caloriesAndFat, int wantedCalories) {
		this.caloriesAndFat = caloriesAndFat;
		this.wantedCalories = wantedCalories;
	}

	public void solveExercise2() {
		/*
			Let OPT(i,j) be the optimal solution for the problem with the items 1,...,i
			and with wanted calories = j
			Then by optimal substructure property
			OPT(i,j).cals = max(OPT(i-1,j-cals(i)).cals + cals(i) , OPT(i-1,j).cals)
			OPT(i,j).fats = max(OPT(i-1,j-cals(i)).fats + fats(i) , OPT(i-1,j).fats)
			Let two solutions (a,b) and (b,c)
			where the first number represents the calories and the second the
			total fat of the solution. Solution (a,b) is better than another (u,v)
			iff ( a>u OR ((a = u) -> b<v) ) that means that the calories are compared first
			and then the fats

			OPT(n,w) = max(OPT(n-1,w-cals(n)) , OPT(n-1,w))

		*/
		//OPT[i][j][0] optimal calories with items 1,...,i and wanted cals = j
		//OPT[i][j][1] optimal fat with items 1,...,i and wanted cals = j
		int[][][] OPT = new int[caloriesAndFat[0].length + 1][wantedCalories+1][2];
		//Initialization with zeros
		
		//OPT(i,0) = 0
		for (int i = 0; i < OPT.length; i++) {
			OPT[i][0][0] = 0;
			OPT[i][0][1] = 0;
		}
		//OPT(0,i) = 0
		for (int i = 0; i < OPT[0].length; i++) {
			OPT[0][i][0] = 0;
			OPT[0][i][1] = 0;
		}
		//printInputData();
		//printTable(OPT);
		for (int i = 1; i < OPT.length; i++) {
			for (int j = 1; j < OPT[0].length; j++) {
				//OPT[i][j] is the current problem (j is the maximum calories)
				//cals(i) = caloriesAndFat[0][i-1]
				//fats(i) = caloriesAndFat[1][i-1]

				//Optimum when item i is not included
				int calories1 = OPT[i - 1][j][0];
				int fats1 = OPT[i - 1][j][1];
				//Optimum when item i is included
				int calories2 = 0;
				int fats2 = 0;
				//IF the current item can be added to the foods list
				//THEN the the optimal solution where the item i
				//is included can be considered
				if (j >= cals(i)) {
					calories2 = OPT[i - 1][j - cals(i)][0] + cals(i);
					fats2 = OPT[i - 1][j - cals(i)][1] + fats(i);
				}	
				if(solution1(calories1,fats1,calories2,fats2)){
					OPT[i][j][0] = calories1;
					OPT[i][j][1] = fats1;
				}
				else{
					OPT[i][j][0] = calories2;
					OPT[i][j][1] = fats2;
				}
			}
		}
		//printTable(OPT);
		System.out.println();
		System.out.printf("Output solution : %d cals and %d fats\n", OPT[caloriesAndFat[0].length][wantedCalories][0],OPT[caloriesAndFat[0].length][wantedCalories][1]);
		//Calculating solution
		int[] itemIndexesStack = new int[caloriesAndFat[0].length];
		int stackPointer = 0;
		for(int i=caloriesAndFat[0].length , j=wantedCalories; j>0 && i >0;){
			//Optimum when item i is not included
			int calories1 = OPT[i - 1][j][0];
			int fats1 = OPT[i - 1][j][1];
			//Optimum when item i is included
			int calories2 = 0;
			int fats2 = 0;
			//IF the current item can be added to the foods list
			//THEN the the optimal solution where the item i
			//is included can be considered
			if (j >= cals(i)) {
				calories2 = OPT[i - 1][j - cals(i)][0] + cals(i);
				fats2 = OPT[i - 1][j - cals(i)][1] + fats(i) ;
			}
			if(solution1(calories1,fats1,calories2,fats2)){
				i--;
			}
			else if(j >= cals(i)){
				itemIndexesStack[stackPointer++] = i-1;
				j-= cals(i);
				i--;
			}
			else{
				i--;
			}
		}
		solution = new int[stackPointer];
		System.out.println("Items (cals,fats):");
		for(int i = 0 ; i < stackPointer ; i++){
			int index = itemIndexesStack[i];
			System.out.printf("( %d , %d ) ", caloriesAndFat[0][index], caloriesAndFat[1][index]);
			solution[i]=index;
		}
		System.out.println();
	}

	public int fats(int i){
		return caloriesAndFat[1][i-1];
	}
	public int cals(int i){
		return caloriesAndFat[0][i-1];
	}
	/*
		Compares solutions (a,b) and (u,v) and returns true if (a,b) is better else false
	 */
	public boolean solution1(int a,int b,int u,int v){
		if(a>u) return true;
		else if(a==u && b < v) return true;
		else return false;
	}
	

	public void printTable(int[][][] in){
		for(int i = 0 ; i < in.length;i++) {
			for(int j =0; j <in[0].length ;j++){
				System.out.print(" "+ in[i][j][0] +" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	public void printSolution()
	{
		printInputData();
		if(solution!=null)
			for( int i=0; i<solution.length; i++ )
			{
				System.out.print( solution[i]+" " );
			}
		else
			System.out.print( " Solution for Exercise2 is empty." );
				
		System.out.println();
	}


	public void printInputData()
	{
		if( caloriesAndFat !=null )
		{
			int rows = caloriesAndFat.length;
			int columns = caloriesAndFat[0].length;
			
			for( int i=0; i<rows; i++ )
			{
				for( int j=0; j<columns; j++ )
				{
					System.out.print( caloriesAndFat[i][j]+" " );
				}
				System.out.println();
			}
		} 
		else
			System.out.println("Input table is null.");
		System.out.println( "Total calories: "+wantedCalories );
	}


}
