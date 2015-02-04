

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;




@SuppressWarnings("serial")
public class TicTacToeGameGUI extends javax.swing.JFrame {
	
	private static int ICON_WIDTH = 400;
	private static int ICON_HEIGHT = 400;
	private static int gbHeight = 400;
	private static int gbWidth = 400;
	private String title;
	private boolean xTurn = false;
	private boolean useAI=true;

	public TicTacToeGameGUI() {
		initComponents();
		final JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		final GameBoard gameB = new GameBoard(0,0,gbHeight,gbWidth,useAI);
		ShapeIcon icon = new ShapeIcon(gameB, ICON_WIDTH, ICON_HEIGHT);
		final JLabel label = new JLabel(icon);
		mainPanel.add(label);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		
		if(!xTurn)
		{
			if(useAI){
				gameB.playAI();
				xTurn=true;
			}
		}
		setT();


		this.getContentPane().addHierarchyBoundsListener(new HierarchyBoundsListener(){//Allows it to be scalable

			public void ancestorMoved(HierarchyEvent e) {

			}
			public void ancestorResized(HierarchyEvent e) {

				gbHeight=mainPanel.getSize().height;
				gbWidth=mainPanel.getSize().width;
				gameB.height=gbHeight;
				gameB.width=gbWidth;
				label.repaint();
			}           
		});
		
		this.getContentPane().addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				Point mousePoint = e.getPoint();
				try{
					gameB.play(mousePoint, xTurn);
				}
				catch(IllegalMove x)
				{
					JOptionPane.showMessageDialog(mainPanel, 
							  "Must play in an empty space.",
							  "Illegal Move", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(xTurn)
				{
					if(useAI){
						gameB.playAI();
					}
					else{
					xTurn=false;
					title = "Tic-Tac-Toe	Player 2 (O)";
					}
				}
				else
				{
					xTurn=true;
					title = "Tic-Tac-Toe	Player 1 (X)";
					
				}
				setT();
				label.repaint();
				gameB.checkVictory(mainPanel);
				label.repaint();
			}
		
		
		});


	}
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new TicTacToeGameGUI().setVisible(true);
			}
		});
	}

	private void initComponents() {

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 400, Short.MAX_VALUE)
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 400, Short.MAX_VALUE)
				);
		pack();
		Dimension DIM= Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(DIM.width/2-this.getSize().width/2, DIM.height/2-this.getSize().height/2);
	}
	
	private void setT(){
		if(xTurn)
		{
			title = "Tic-Tac-Toe	Player 1 (X)";
		}
		else
		{
			title = "Tic-Tac-Toe	Player 2 (O)";
		}
		
		this.setTitle(title);
	}

	
}