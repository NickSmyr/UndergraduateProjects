import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.LinkedList;


import java.util.Random;

class Exercise2
{

	private Map <String, List<String>> nodes;
	private List<String> solution1;
	private long t1;
	private List<String> solution2;
	private long t2;
	
	private ArrayList<String> g;

	public Exercise2( Map <String, List<String>> nodes )
	{
		this.nodes = nodes;
		this.solution1 = new ArrayList<>();
		this.t1 = 0L; // time in milliseconds
		this.solution2 = new ArrayList<>();
		this.t2 = 0L;  // time in milliseconds
	}

	public void solveExercise2()
	{
		long end,start;
		System.out.println("Graph size = " + nodes.keySet().size());
		System.out.println(nodes);
		///Brute count
		g = new ArrayList<String>(nodes.keySet());
		
		start = System.currentTimeMillis();	
		for(int i = 1 ; i <= g.size() ; i++){
			System.out.println("[BRUTE-FORCE] Checking for vertex covers with size " + i);
			solution1 = DFS(i);
			if(solution1 != null) break;
		}
		end = System.currentTimeMillis();
		t1 = (end - start);
		System.out.println("Brute force solution : " + solution1);
		System.out.println("Brute force solution size : " + solution1.size());
		System.out.println("Brute force time  " + t1 + " ms");
		System.out.println();
		
		///Brute count
		/*
		* Implement your solution.
		* Use the following code to get the time (in milliseconds) for each solution.
		* - long start = System.currentTimeMillis();
		* - // your solution
		* - long end = System.currentTimeMillis(); 
		* - t1 = (end-start); // in the same way t2 = (end-start);
		*/
		
		//Exhaustive solution
		
		
		//Powerset construction
		/**
		List<List<String>> solutions = new ArrayList<List<String>>();
		solutions.add(new ArrayList<String>()); //Empty set
		if(checkSolution(new TreeSet<>(solutions.get(0)))) {solution1 = solutions.get(0);return;}
		int j = 0;
		for(String e : nodes.keySet()){
		j++;
			//System.out.println(solutions);
			List<List<String>> newSolutions =  new ArrayList<List<String>>();
			int oldSize = solutions.size();
			System.out.println("Power Set with "+ j+" items " );
			for(int i = 0; i < oldSize; i++){
				if(checkSolution(new TreeSet<>(solutions.get(i)))) {solution1 = solutions.get(i);return;}
				List<String> newSet = new ArrayList(solutions.get(i));
				newSet.add(e);
				solutions.add(newSet);
			}
		}
		**/
		//Greedy solution
		start = System.currentTimeMillis();
		ArrayList<String> solution = new ArrayList<String>();
		while(!checkSolution(solution)){
			//Greedy choice (Selecting vertex with maximum degree) 
			String maxGrade = findMaximumEdgeDegree();
			solution.add(maxGrade);
			//Removing the selected vertex as well adjacent edges and continuing with the rest of the graph
			removeVertex(maxGrade);
		}
		end = System.currentTimeMillis();
		
		solution2 = solution;
		t2 = end - start;
		
		System.out.println("Greedy solution " + solution);
		System.out.println("Greedy solution size " + solution.size());
		System.out.println("Greeedy time  " + t2 + " ms");
	}
	public String findMaximumEdgeDegree(){
		String max = null;
		int maxDegree = 0;
		for(String e : nodes.keySet()){
			if(nodes.get(e).size() > maxDegree){
				max = e;
				maxDegree = nodes.get(e).size();
			}
		}
		return max;
	}
	public void removeEdge(String u,String v){
		//System.out.println("Removing edge : (" + u +" , "+ v+")" );
		nodes.get(u).remove(v);
		nodes.get(v).remove(u);
	}
	public void removeVertex(String name){
		//System.out.println("Removing vertex " + name);
		//Removing every edge adjacent to input vertex
		while(!nodes.get(name).isEmpty()){
			String e = nodes.get(name).get(0);
			removeEdge(name,e);
		}
		nodes.remove(name);
	}
	public void printTable(int[] table){
		for(int i = 0 ; i < table.length ; i++){
			System.out.print(table[i] + " ");
		}
		System.out.println();
}
	public ArrayList<String> DFS(int k){
		//Shows the last index of the node that was placed in the ith position of the solution
		LinkedList<Integer> stack = new LinkedList<Integer>();
		ArrayList<String> solution = new ArrayList<>(k);
		int[] states = new int[k];
		
		for(int i = 0 ; i < k;i++){
			solution.add(g.get(i));
			states[i] = i;
			stack.push(i);
		}
		//printTable(states);
		if(checkSolution(solution)){
		 	//System.out.println("Solution succes : " + solution);
		 	return solution;
		}
		

		if(stack.isEmpty()){
			stack.push(0);
		}
		boolean nextSolution = false;
		boolean increment = false;
		while(!stack.isEmpty()){
							
			//try{Thread.sleep(100);}catch(Exception e){}
			//This loop must be executed while backtracking
			while(stack.size() < k || nextSolution){
				if(increment && stack.isEmpty()) break;
				
				
				int prev = stack.peek();
				if(nextSolution){
					nextSolution = false;
					stack.pop();
					//System.out.println("Next Solution pop");
				}
				if(increment){
					prev = stack.pop();
					increment = false;
					//System.out.println("Increment consume");
				}
				//If all items have been checked on the previous position
				if(prev >= g.size()-1){
					increment = true;
					//System.out.println("Increment trigger");
					continue;
				}
			
				//If all items have been checked on the previous position
				stack.push(prev + 1);
				if(stack.size() > k) {
					//nextSolution = true;
					//System.out.println("Stack size is greater than 3");
					increment = true;
				}
			}
			//System.out.println("CHECKING SOLUTION ");
			solution = new ArrayList<String>();
			for(int i : stack){
				solution.add(g.get(i));
			}
			if(checkSolution(solution)) {
				//System.out.println("Solution succes : " + solution);
				return solution;
			}
			//Solution didn't work 
			//Examining next solution by backtracking
			nextSolution = true;
			//If the last item is popped all possible combinations with k items have been examined and
			//None of them were a solution
			//System.out.println("Stack " + stack);
		}
		return null;
	}
	//Running time O(V + E)
	public boolean checkSolution(List<String> solution){
		for(String u:nodes.keySet()){
			if(solution.contains(u)) continue;
			for(String e : nodes.get(u)){
				if(!solution.contains(e)) return false;
			}
		}
		return true;
	}
	
	

	public void printSolution()
	{
		if(solution1.size()!=0)
		{
			for( int i=0; i<solution1.size(); i++ )
			{
				System.out.print( solution1.get(i)+" " );
			}
			System.out.println( " "+t1 );
		}
		
		if(solution2.size()!=0)
		{
			for( int i=0; i<solution2.size(); i++ )
			{
				System.out.print( solution2.get(i)+" " );
			}
			System.out.println( " "+t2 );
		}
	}
	public void generateRandomGraph(int numNodes){
		Random random = new Random();
		HashMap<String, List<String>> graph = new HashMap<>();
		//Creating numNodes vertexes with names {1,..,numNodes}
		for(int i = 1 ; i <= numNodes ;i++){
			graph.put(String.valueOf(i),new ArrayList<String>());
		}
		//For each edge{u,v}
		for(int u = 1; u <= numNodes ; u++){
			for(int v = u; v <= numNodes ; v++){
				//With 0.5 probability the edge is added to the graph
				if(random.nextDouble() >= 0.8){
					graph.get(String.valueOf(u)).add(String.valueOf(v));
					if(u!=v)graph.get(String.valueOf(v)).add(String.valueOf(u));
				}
			}
		}
		this.nodes = graph;
	}
	
	/*
	public void printInputData()
	{
		if( nodes !=null )
		{
			System.out.println( "Size of Adjacency-List: "+nodes.size() );
			for (String key   : nodes.keySet()) 
			{
			    List<String> values = nodes.get(key);
			    System.out.println( "-Node "+key+" has neighbors: "+Arrays.toString(values.toArray()) ); 
			}
			System.out.println();
		} 
		else
			System.out.println("Input table is null.");
	}
	*/
	
}
