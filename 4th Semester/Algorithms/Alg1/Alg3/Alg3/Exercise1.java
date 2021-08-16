import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.LinkedList;

class Exercise1
{

	private Map <String, List<String>> nodes;
	private List<String> solution;
	private String name1;
	private String name2;

	public Exercise1( Map <String, List<String>> nodes, String name1, String name2 )
	{
		this.nodes = nodes;
		this.name1 = name1;
		this.name2 = name2;
		this.solution = new ArrayList<>();
	}

	public void solveExercise1()
	{
		if(nodes.get(name1) == null || nodes.get(name2) == null){
			throw new RuntimeException("Input names for exercise1 not existent in Input graph");
		}
		/*
		* Implement your solution.
		*/
		ArrayList<String> path1 = BFS(nodes,name1,name2);
		//System.out.println("PAath 1 =" + path1);
		if(path1 == null) return;
		//Removing every (u,v) edge of the shortest path and calcuating shortest path again
		
		for(int i = 0; i< path1.size()-1; i++){
			removeEdge(path1.get(i),path1.get(i+1));
		}
		
		ArrayList<String> path2 = BFS(nodes,name2,name1);
		//System.out.println("PAath 2 =" + path2);
		if(path2 == null) return;
		
		for(String e : path1){
			solution.add(e);
		}
		for(int i = 1 ;i< path2.size() ; i++){
			solution.add(path2.get(i));
		}
		
		
	}
	public void removeEdge(String u,String v){
		//System.out.println("Removing : " + u + v );
		nodes.get(u).remove(v);
		nodes.get(v).remove(u);
	}
	public void printSolution()
	{
		if(solution.size()!=0)
		{
			System.out.print("Solution 1 : { ");
			for( int i=0; i<solution.size(); i++ )
			{
				System.out.print( solution.get(i)+" " );
			}	
			System.out.println(" }");
		}
	}
	public ArrayList<String> BFS(Map<String,List<String>> graph,String name,String target){
		Map<String,String> prev = new TreeMap<String,String>();
		Map<String,Boolean> visited = new TreeMap<String,Boolean>();
		Map<String,Integer> d = new TreeMap<String,Integer>();
		d.put(name,0);
		visited.put(name,true);
		
		LinkedList<String> queue = new LinkedList<>();
		
		queue.offer(name);
		while(!queue.isEmpty()){
			String u = queue.poll();
			for(String v: graph.get(u)){
				if(visited.get(v) != null) continue;
				queue.offer(v);
				prev.put(v,u);
				d.put(v,d.get(u)+1);
				visited.put(v,true);
			}
		}
		//System.out.println(d);	
		//Calculating Path
		ArrayList<String> shortestUnweightedPath = new ArrayList<String>();
		String currNode = target;
		for(;currNode != null ;){
			shortestUnweightedPath.add(currNode);
			currNode = prev.get(currNode);
		}
		if(shortestUnweightedPath.size() == 1) return null;
		String[] temp = new String[shortestUnweightedPath.size()];
		for(int i = 0 ; i < temp.length ; i++){
			temp[i] = shortestUnweightedPath.get(i);
		}
		ArrayList<String> reverseResult = new ArrayList<String>();
		for(int i = 0 ; i < shortestUnweightedPath.size() ; i++){
			reverseResult.add(temp[temp.length-1-i]);
		}
		
		return reverseResult;
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

		System.out.println( "Find a circle with "+this.name1+" and "+this.name2+" inside." );
	}
	*/
	

}
