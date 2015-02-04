import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Tree {

	public Node rootNode;
	public char computer;
	
	public Tree(ArrayList<Character> aBoard, char computer){//takes a Tic-Tac-Toe board in the form of ArrayList<Character>
		this.computer=computer;
		rootNode= new Node(aBoard);
		addLevels(rootNode,computer);
		applyAllWeight();
	}

	@SuppressWarnings("unused")//for testing
	private void initializeRootNode(){
		rootNode= new Node();
//		for(int i=0;i<9;i++){//blank board for rootNode
//			rootNode.board.add(' ');
//		}

		rootNode.board.add(' ');rootNode.board.add('O');rootNode.board.add('X');
		rootNode.board.add(' ');rootNode.board.add('X');rootNode.board.add(' ');
		rootNode.board.add('O');rootNode.board.add(' ');rootNode.board.add(' ');
		
		System.out.println("Root Node: "+rootNode.board);

	}

	private void addLevels(Node parent, char move){
		if(parent.weight!=0){return;}//pruning
		int open =0;
		for(int k=0;k<9;k++){	//determine the number of children/possible plays/openings
			if(parent.board.get(k)==' '){
				open++;
			}
		}
		
		if(open==0){return;}//quit adding levels if no more possible plays

		for(int n=0;n<open;n++){//make offspring's board carbon copy of parent's board
			Node node = new Node();
			node.parent=parent;
			for(int i=0;i<9;i++){
				node.board.add(node.parent.board.get(i));
			}
			node.parent.children.add(node);
		}
		
//		System.out.println("Children of the level: "+(9-open));
		
		int temp=0;
		for(int k=0;k<parent.children.size();k++){//make a play
			while(true){
				if((temp)>8)
				{break;}
				if(parent.children.get(k).board.get(temp)==' '){
					parent.children.get(k).board.set(temp, move);
					parent.children.get(k).weight=checkVictory(parent.children.get(k).board);
					parent.children.get(k).height=(parent.height+1);
					temp++;
					break;
				}
				else{
					temp++;
				}
			}
		}

		if(move=='X'){move='O';}//alternate players
		else{move='X';}

		for(Node child : parent.children)
		{
			addLevels(child, move);
		}
	}

	private int checkVictory(ArrayList<Character> aBoard){
		char oX=' ';
		outerLoop:{
			for(int r=0; r<3; r++){//check rows
				if(aBoard.get(r*3)==aBoard.get(r*3+1)&&aBoard.get(r*3+1)==aBoard.get(r*3+2)&&aBoard.get(r*3)!=' ')
				{
					oX=aBoard.get(r*3);
					break outerLoop;
				}
			}
			for(int c=0; c<3; c++){//check cols
				if(aBoard.get(c)==aBoard.get(c+3)&&aBoard.get(c+3)==aBoard.get(c+6)&&aBoard.get(c)!=' ')
				{
					oX=aBoard.get(c);
					break outerLoop;
				}
			}
			//check diagonals
			if(aBoard.get(0)==aBoard.get(4)&&aBoard.get(4)==aBoard.get(8)&&aBoard.get(0)!=' ')
			{
				oX=aBoard.get(0);
				break outerLoop;
			}
			if(aBoard.get(2)==aBoard.get(4)&&aBoard.get(4)==aBoard.get(6)&&aBoard.get(2)!=' ')
			{
				oX=aBoard.get(2);
				break outerLoop;
			}
			for(int k=0; k<9; k++){
				if(aBoard.get(k)!=' '){
					return 0;
				}
			}
		}
		if(oX==computer)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}

	private void applyWeight(Node n){
		if(n.children.size()==0){return;}
		boolean takeMax = determineMinimax(n);
		ArrayList<Integer> weightsList= new ArrayList<Integer>();
		for(Node child : n.children){
			weightsList.add(child.weight);
		}
		Collections.sort(weightsList);
		if(takeMax){
			n.weight=weightsList.get((weightsList.size()-1));
		}
		else
		{
			n.weight=weightsList.get(0);
		}
		
		for(Node child : n.children)
		{
			applyWeight(child);
		}
	}

	private boolean determineMinimax(Node n){//returns true is should take max of children
		char tempMove=' ';
		for(int k =0;k<9;k++){
			if(n.board.get(k)!=n.children.get(0).board.get(k)){
				tempMove=n.children.get(0).board.get(k);
			}
		}
		if(tempMove==computer){
			return true;
		}
		
		return false;
	}
	
	public ArrayList<Character> nextPlay(){
		if(rootNode.children.size()==0){return rootNode.board;}
		if(rootNode.weight==0){
			Random rand = new Random(rootNode.children.size()-1);
			int temp = rand.nextInt(rootNode.children.size()-1);
			int stuck = 0;
			while((rootNode.children.get(temp).weight==-1)&&(stuck<100)){
				temp = rand.nextInt(rootNode.children.size()-1);
				stuck++;
			}
			if(stuck<99){return rootNode.children.get(temp).board;}
			int k;
			for(k= 0;k<rootNode.children.size();k++){
				if(rootNode.children.get(k).weight!=-1){
					break;
				}
			}
			return rootNode.children.get(k).board;
		}
		for(Node child: rootNode.children){
			if(child.weight==1){
				return child.board;
			}
		}
		return rootNode.children.get(0).board;//should never get to this line
	}
	
	private void applyAllWeight(){
		for(int k=0;k<9;k++){
			applyWeight(rootNode);
			if(rootNode.weight!=0)
			{break;}
		}
	}
	/* for testing
	public static void main(String[] args) {
		initializeRootNode();
		addLevels(rootNode, 'X');
		for(int k=0;k<9;k++){
			applyWeight(rootNode);
			if(rootNode.weight!=0)
			{break;}
		}
		System.out.println(determineMinimax(rootNode.children.get(0).children.get(0)));
		System.out.println(rootNode.weight);
		System.out.println(nextPlay());
		System.out.println(rootNode.children.get(1).weight);
		System.out.println(rootNode.weight);
		System.out.print("W "+rootNode.children.get(2).children.get(1).weight);
		System.out.print(" H "+rootNode.children.get(2).children.get(1).height+": ");
		System.out.println(rootNode.children.get(2).children.get(1).board);
		try{
			System.out.println(rootNode.children.get(2).children.get(1).children.get(2).board);
			System.out.println(rootNode.children.get(2).children.get(1).children.get(2).weight);
		}
		catch(Exception e){
			System.out.println("no board");
		}
		


	}
	*/
}