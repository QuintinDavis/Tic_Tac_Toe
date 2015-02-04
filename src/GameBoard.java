import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GameBoard
{
	public GameBoard(int x, int y, int height, int width, boolean AI)
	{
		this.height= height;
		this.width= width;

		for(int c=0; c<3; c++){
			ArrayList<Integer> row = new ArrayList<Integer>();
			for(int r=0; r<3; r++){
				row.add(0);
			}
			listOfSpaces.add(row);
		}
		useAI=AI;
	}

	public void play(Point play, boolean x) throws IllegalMove
	{
		int row=0;
		int col=0;
		if(play.y>2*height/3)
		{
			row=2;
		}
		else if(play.y>height/3)
		{
			row=1;
		}
		if(play.x>2*width/3)
		{
			col=2;
		}
		else if(play.x>width/3)
		{
			col=1;
		}
		if(listOfSpaces.get(col).get(row)!=0)
		{
			throw new IllegalMove();
		}
		if(x)
		{
			listOfSpaces.get(col).set(row, 1);
		}
		else
		{
			listOfSpaces.get(col).set(row, 2);
		}

	}

	public void checkVictory(JPanel panel)
	{

		String result="";
		int oX=0;
		outerLoop:{
			for(int r=0; r<3; r++){//check rows
				if(listOfSpaces.get(r).get(0)==listOfSpaces.get(r).get(1)&&listOfSpaces.get(r).get(1)==listOfSpaces.get(r).get(2))
				{
					oX=listOfSpaces.get(r).get(0);
					break outerLoop;
				}
			}
			for(int c=0; c<3; c++){//check cols
				if(listOfSpaces.get(0).get(c)==listOfSpaces.get(1).get(c)&&listOfSpaces.get(1).get(c)==listOfSpaces.get(2).get(c))
				{
					oX=listOfSpaces.get(0).get(c);
					break outerLoop;
				}
			}
			//check diagonals
			if(listOfSpaces.get(0).get(0)==listOfSpaces.get(1).get(1)&&listOfSpaces.get(1).get(1)==listOfSpaces.get(2).get(2))
			{
				oX=listOfSpaces.get(0).get(0);
				break outerLoop;
			}
			if(listOfSpaces.get(0).get(2)==listOfSpaces.get(1).get(1)&&listOfSpaces.get(1).get(1)==listOfSpaces.get(2).get(0))
			{
				oX=listOfSpaces.get(0).get(2);
				break outerLoop;
			}
			innerLoop:{//check draw
				for(int c=0; c<3; c++){
					for(int r=0; r<3; r++){
						if(listOfSpaces.get(r).get(c)==0){
							break innerLoop;
						}
					}
				}
				result= "Draw.";
			}
		}
		if(oX==1)
		{
			result="Player 1 (X) wins the game!";
		}
		else if(oX==2)
		{
			if(useAI){
				result = "Computer wins!";
			}
			else{
			result="Player 2 (O) wins the game!";
			}
		}


		if(result!="")
		{
			for(int c=0; c<3; c++){
				for(int r=0; r<3; r++){
					listOfSpaces.get(r).set(c,0);
				}
			}
			JOptionPane.showMessageDialog(panel, 
					(result),
					"Game Over", JOptionPane.INFORMATION_MESSAGE);
			
		}
		
	}

	public void draw(Graphics2D g2)
	{
		//Draw the grid
		Point2D.Double r1
		= new Point2D.Double(width / 3, 0);
		Point2D.Double r2
		= new Point2D.Double(width / 3, height);
		Point2D.Double r3
		= new Point2D.Double(2*width / 3, 0);
		Point2D.Double r4
		= new Point2D.Double(2*width / 3, height);
		Line2D.Double frame1
		= new Line2D.Double(r1, r2);
		Line2D.Double frame2
		= new Line2D.Double(r3, r4);
		Point2D.Double r5
		= new Point2D.Double(0,height / 3);
		Point2D.Double r6
		= new Point2D.Double(width , height/ 3);
		Point2D.Double r7
		= new Point2D.Double(0,2*height / 3);
		Point2D.Double r8
		= new Point2D.Double(width,2*height / 3);
		Line2D.Double frame3
		= new Line2D.Double(r5, r6);
		Line2D.Double frame4
		= new Line2D.Double(r7, r8);
		
		g2.draw(frame1);
		g2.draw(frame2);
		g2.draw(frame3);
		g2.draw(frame4);

		for(int r=0; r<3; r++){
			for(int c=0; c<3; c++){
				if(listOfSpaces.get(r).get(c)==0)
				{

				}
				else if(listOfSpaces.get(r).get(c)==1)
				{
					Point2D.Double x1
					= new Point2D.Double( r*width/(3)+width*shrinkF, c*height/(3)+height*shrinkF);
					Point2D.Double x2
					= new Point2D.Double(width / 3*(r+1)-width*shrinkF, height/3*(c+1)-height*shrinkF);

					Point2D.Double x3
					= new Point2D.Double(width / 3*(r)+width*shrinkF, (c+1)*height/(3)-height*shrinkF);
					Point2D.Double x4
					= new Point2D.Double(width / 3*(r+1)-width*shrinkF, c*height/(3)+height*shrinkF);

					Line2D.Double X1
					= new Line2D.Double(x1, x2);
					Line2D.Double X2
					= new Line2D.Double(x3, x4);
					g2.draw(X1);
					g2.draw(X2);
				}
				else
				{		
					Ellipse2D.Double circle= new Ellipse2D.Double(r*width/(3)+width*shrinkF, c*height/(3)+height*shrinkF,
							width / 3-width*2*shrinkF, height/3-height*2*shrinkF);
					g2.draw(circle);
				}
			}
		}
	}
	
	private ArrayList<Character> adaptGameBoard(ArrayList<ArrayList<Integer>> gameBoard){
		ArrayList<Character> temp = new ArrayList<Character>();
		
		for(int r=0; r<3; r++){
			for(int c=0; c<3; c++){
				char cTemp = ' ';
				if(gameBoard.get(c).get(r)==1){
					cTemp = 'X';
				}
				else if(gameBoard.get(c).get(r)==2){
					cTemp = 'O';
				}
					
				temp.add(cTemp);
			}
		}
		
//		System.out.println("GB: "+gameBoard);
//		System.out.println("TR: "+temp);
		return temp;
	}
	
	private ArrayList<ArrayList<Integer>>  adaptTreeBoard(ArrayList<Character> treeBoard){
		ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>> (); 	
		
		ArrayList<Integer> rowTemp = new ArrayList<Integer>();
		for(int k =0;k<9;k+=3){
			if(treeBoard.get(k)==' '){
				rowTemp.add(0);
			}
			else if(treeBoard.get(k)=='X'){
				rowTemp.add(1);
			}
			else{
				rowTemp.add(2);
			}
			
		}
		temp.add(rowTemp);
		ArrayList<Integer> rowTemp2 = new ArrayList<Integer>();
		for(int k =1;k<9;k+=3){
			if(treeBoard.get(k)==' '){
				rowTemp2.add(0);
			}
			else if(treeBoard.get(k)=='X'){
				rowTemp2.add(1);
			}
			else{
				rowTemp2.add(2);
			}
		}
		temp.add(rowTemp2);
		ArrayList<Integer> rowTemp3 = new ArrayList<Integer>();
		for(int k =2;k<9;k+=3){
			if(treeBoard.get(k)==' '){
				rowTemp3.add(0);
			}
			else if(treeBoard.get(k)=='X'){
				rowTemp3.add(1);
			}
			else{
				rowTemp3.add(2);
			}
		}
		temp.add(rowTemp3);

		return temp;
	}
	
	public void playAI() {
		char computer = 'O';
		adaptGameBoard(listOfSpaces);
		currentTree = new Tree(adaptGameBoard(listOfSpaces), computer);
//		System.out.println(adaptGameBoard(listOfSpaces));
//		System.out.println(currentTree.nextPlay());
		listOfSpaces = adaptTreeBoard(currentTree.nextPlay());
	}
	public Tree currentTree;
	public int height;
	private double shrinkF = 0.03;
	public int width;
	private ArrayList<ArrayList<Integer>> listOfSpaces= new ArrayList<ArrayList<Integer>>();
	private boolean useAI;
	
}