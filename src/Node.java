import java.util.ArrayList;


public class Node {
	Node parent;
	public ArrayList<Character> board = new ArrayList<Character>();
	public ArrayList<Node> children = new ArrayList<Node>();
	public int height;
	public int weight;
    
	Node(){
		
	}
	Node(ArrayList<Character> aBoard){
		board = aBoard;
	}
	
}
