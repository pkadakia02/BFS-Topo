import java.util.ArrayList;
import java.util.ArrayDeque;

//-------------------------------------------------------------
//
//
class Node
{
    public Node(String name)
    {
        this.name = name;
    }
    public String name;
    public int in_count = 0;
    public boolean visited = false;
    
    public ArrayList<Edge> out_edge_list = new ArrayList<>();
    public String toString(){ return name; }
}
//-------------------------------------------------------------
//
//
class Edge
{
    public Edge(String name, double weight, Node from, Node to)
    {
        this.name = name;
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
    public String toString(){ return name + ": " + from + " " + to; }
    public String name;
    public double weight;
    public Node from;
    public Node to;
}
//-------------------------------------------------------------
//
//
class Graph
{
    public ArrayList<Node> node_list = new ArrayList<>();
    public ArrayList<Edge> edge_list = new ArrayList<>();
    
    //----------------------------------------------------------
    //
    public int find_node_index(String name) 
    { 
        for(int i = 0; i < node_list.size(); ++i)
            if (node_list.get(i).name == name) return i;
        return -1;
    }
    //----------------------------------------------------------
    // Add a new edge ( and possibly new nodes) to the graph.
    //
    public void add_edge(String name, double weight, String node_name_from, String node_name_to)
    {
        int node_index;
        Node node_from, node_to;
        
        node_index = find_node_index(node_name_from);
        if (node_index >= 0) 
            node_from = node_list.get(node_index);
        else 
            node_list.add(node_from = new Node(node_name_from));
        
        node_index = find_node_index(node_name_to);
        if (node_index >= 0) 
            node_to = node_list.get(node_index);
        else 
            node_list.add(node_to = new Node(node_name_to));
        
        Edge new_edge = new Edge(name, weight, node_from, node_to);
        edge_list.add(new_edge);
        node_from.out_edge_list.add(new_edge);
    }
    //----------------------------------------------------------
    //
    public String toString()
    {
        String s = "\nNodes\n---------------\n";
        for(Node n : node_list)
        {
            s += n + " " + n.in_count + "\n";
        }
        s += "\nEdges\n---------------\n";
        for(Edge e : edge_list)
        {
            s += e + "\n";
        }
        return s;
    }
    //----------------------------------------------------------
    // Initialize Node in counts.
    //
    public void init_in_counts() // use for each loop(for:this) run a for loop over the edge and find the data member "to" and gets it data member "in count" and increment it
    {
    	for(Edge e: edge_list) {
    		e.to.in_count+=1; 
    	}

    }
    
    public void dfs(Node n) 
    {
    	
    	
    	if(n.visited == true)
    		return;
    	System.out.println("Push " + n);
    	n.visited = true;
    	
    	for(Edge e:n.out_edge_list) {
    		Node nod = e.to;
    		dfs(nod);
    	}
    	System.out.println("Pop");
    	
    }
    
    public void top_sort() {
    	
    	Node[] sort = new Node[node_list.size()];
    	init_in_counts();
    	
    	top_sort(0, sort);
    	System.out.println("\n");
    }
    
    private void top_sort(int nodes_visited, Node[] sort) {
    
    	if(nodes_visited == node_list.size()) {
    		for(int i=0;i<sort.length;i++){
    	        System.out.print(sort[i] + " ");
    	      }
    		System.out.print("\n");
    		return; 
    	} 
    	
    	for(Node n:node_list) {
    		if(!n.visited && n.in_count == 0) {
    			
    			sort[nodes_visited]=n;    			
    			n.visited = true;
    			for(Edge e: edge_list) {
    				if(e.from == n)
    					e.to.in_count -=1;
    			}
    			top_sort(nodes_visited+1,sort);
    			for(Edge e: edge_list) {
    				if(e.from == n)
    					e.to.in_count +=1;	
    			}
    			n.visited = false;
    		}
    	}
    }
    
    public void bfs(String node_name)
    { bfs(node_list.get(find_node_index(node_name)));}
    
    public void bfs(Node n) {
    	ArrayDeque<Node> node_q = new ArrayDeque<>(node_list.size());
    	for(Node no:node_list) {
    		no.visited = false;
    	}
    	
    	n.visited = true;
    	node_q.add(n); 
    	System.out.println("\n=========================\nBFS: Start Node - " + n + "\n=========================");
    	System.out.println("Visit " + n);
    	
    	while(node_q.isEmpty() == false) {
    		Node removed = node_q.getFirst(); 
    		node_q.remove();
    		for(Edge e:removed.out_edge_list) { 
    			if(e.to.visited == false) {
    				e.to.visited = true;
    				System.out.println("Visit " + e.to); 
    				node_q.add(e.to);
    			}
    		}
    		

    	}
    	
    }
}

//-------------------------------------------------------------
//
//
class BFS
{
    public static void main(String[] args)
    {
    	Graph g = new Graph();
    	g.add_edge("e1", 1.0, "1", "4");
    	g.add_edge("e2", 2.0, "1", "5");
    	g.add_edge("e3", 3.0, "2", "3"); 
    	g.add_edge("e4", 4.0, "2", "4"); 
    	g.add_edge("e5", 5.0, "3", "4");
    	g.add_edge("e6", 6.0, "3", "6");  
    	g.add_edge("e7", 7.0, "3", "8");  
    	g.add_edge("e1", 8.0, "4", "5"); 
    	g.add_edge("e3", 9.0, "5", "7");  
    	g.add_edge("e3", 10.0, "5", "9"); 
    	g.add_edge("e3", 11.0, "6", "7");   
    	g.add_edge("e3", 12.0, "7", "9");     
    	g.add_edge("e3", 13.0, "8", "9");
    	
    	
    	System.out.println("=========================\n Topological Sorts\n=========================\n");
    	g.top_sort();
    	  
    	g.bfs("1");
      	g.bfs("2");
    	g.bfs("3");
    }
}