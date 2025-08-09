package colMatch;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

public class Table extends JPanel {
	private static final int tablePadding = 50;
	
	private Card[][] grid;
	private Card firstCard;
	private Card secondCard;
	private Timer timer;
	private static final int NUM_ROWS = 3;
	private static final int NUM_COLS = 4;
	private int playerOneScore, playerTwoScore;
	private ArrayList <Card> cards;
	private boolean isPlayerOneTurn = true;
	private JTextField firstPlayerScoreTxt, secondPlayerScoreTxt;
	
	private int selectedRow = 0;
	private int selectedCol = 0;


	/**
	 * @param numCards           The number of colour cards
	 * @param firstPlayerScoreTxt  Textfield to display the score of the red player
	 * @param secondPlayerScoreTxt Textfield to display the score of the blue player
	 */
	Table(int numCards, JTextField firstPlayerScoreTxt, JTextField secondPlayerScoreTxt) throws IOException {
		setBackground(Color.BLACK);
		// Initialise the table		
		// Fill the array by reading in the cards
		//Create the cards
		String[] cardNames = {"1", "2", "3", "4", "5", "6"};
		 cards = new ArrayList<Card>();
		for (String name : cardNames) {
		    cards.add(new Card(name));
		    cards.add(new Card(name)); // duplicate for matching
		}	
//			Initialize the grid
				 grid = new Card[NUM_ROWS][NUM_COLS];				
					
				// Shuffle before adding cards to the grid
				shuffle();
				
				//add cards to grid
		int index = 0;
		for (int row = 0; row < NUM_ROWS; row++) {
			for(int col = 0; col < NUM_COLS; col++) {
				Card c = cards.get(index++);
		        grid[row][col] = c;
		        c.setRow(row); //set rows and cols of card so they know where they belong
		        c.setCol(col);
			}
		}
		
		//Players score
		playerOneScore = 0;
		playerTwoScore = 0;
		
		setLayout(new BorderLayout());		
		
		this.firstPlayerScoreTxt = firstPlayerScoreTxt;
		this.secondPlayerScoreTxt = secondPlayerScoreTxt;

		firstPlayerScoreTxt.setEditable(false);
		firstPlayerScoreTxt.setFont(new Font("SansSerif", Font.BOLD, 18));
		firstPlayerScoreTxt.setHorizontalAlignment(SwingConstants.CENTER);;
		firstPlayerScoreTxt.setText("First Player: " + playerOneScore);

		this.secondPlayerScoreTxt.setEditable(false);
		this.secondPlayerScoreTxt.setFont(new Font("SansSerif", Font.BOLD, 18));
		this.secondPlayerScoreTxt.setHorizontalAlignment(JTextField.CENTER);
		this.secondPlayerScoreTxt.setText("Second Player: " + playerTwoScore);

		
		
		add(firstPlayerScoreTxt, BorderLayout.NORTH);
		add(secondPlayerScoreTxt, BorderLayout.SOUTH);
		
		//set selected cards to null
		firstCard = null;
		secondCard = null;	
				
		setPreferredSize(new Dimension(Card.cardWidth * NUM_COLS + tablePadding, Card.cardHeight * NUM_ROWS
				+ tablePadding));
		
		//add listener
		
		MouseListener ml = new MyMouseListener();
		this.addMouseListener(ml);
		KeyListener kl = new MyKeyListener();
		
		this.addKeyListener(kl);
		setFocusable(true);
		requestFocusInWindow();
		
		
		gameOver();
	}
	
	
	public void shuffle() {
		// Swap 2 random cards 24 times
	    Random rand = new Random();
	    for (int i = 0; i < 24; i++) {
	        int a = rand.nextInt(cards.size());
	        int b = rand.nextInt(cards.size());
	        Card temp = cards.get(a);
	        cards.set(a, cards.get(b));
	        cards.set(b, temp);
	    }
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//draw the grid and call the draw method
		for (int row = 0; row < NUM_ROWS; row++) {
			for(int col = 0; col < NUM_COLS; col++) {
				boolean isSelected = (row == selectedRow && col == selectedCol);
				grid[row][col].draw(g,isSelected);
			}
		}

	}

	public boolean gameOver() {
	    for (int row = 0; row < NUM_ROWS; row++) {
	        for (int col = 0; col < NUM_COLS; col++) {
	            if (!grid[row][col].getFaceUp()) { //If there exists a card that is not face up
	                return false; // At least one card is still face-down
	            }
	        }
	    }
	    return true; // All cards are matched and face-up
	}
	//Listener classes
	private class MyKeyListener extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (selectedCol < NUM_COLS - 1) selectedCol++;
				repaint();
			} 
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (selectedCol > 0) selectedCol--;
				repaint();
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (selectedRow > 0) selectedRow--;
				repaint();
			} 
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (selectedRow < NUM_ROWS - 1) selectedRow++;
				repaint();
			}
			
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				
				Card cardClicked = grid[selectedRow][selectedCol];
				System.out.println("(" +selectedCol + "," + selectedRow +")");
				
				if (!cardClicked.getFaceUp()) 
				cardClicked.flip();
				repaint();
				
				if (firstCard == null) {
					firstCard = cardClicked;
					
				} else if (secondCard == null && firstCard != null) {
							secondCard = cardClicked;
							
							timer = new Timer(1000, new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									if (!firstCard.isSame(secondCard)) {
										firstCard.flip();
										secondCard.flip();
										isPlayerOneTurn = !isPlayerOneTurn;
										
									} else {
										if (isPlayerOneTurn) {
											playerOneScore++;
										} else {
											playerTwoScore++;
										}
										firstPlayerScoreTxt.setText("First Player: " + playerOneScore);
										secondPlayerScoreTxt.setText("Second Player: " + playerTwoScore);
									}
									
									firstCard = null;
									secondCard = null;
									repaint();								
								}
							});
							timer.setRepeats(false);
							timer.start();
					}					
			}
		}
	}
	
	private class MyMouseListener extends MouseAdapter{
		
		public void mouseClicked(MouseEvent e) {
			int col = e.getX()/Card.cardWidth;
			int row = e. getY()/Card.cardHeight;
			
			Card cardClicked = grid[row][col];
			
			if (!cardClicked.getFaceUp()) {
				cardClicked.flip();
				repaint();
				
				if (firstCard  == null) {
				firstCard = cardClicked;
				} else if (secondCard == null && firstCard != null) {
					secondCard = cardClicked;
					
					removeMouseListener(this);
					timer = new Timer(1000,new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							if (!firstCard.isSame(secondCard)) {
								firstCard.flip();
								secondCard.flip();
								isPlayerOneTurn = !isPlayerOneTurn;
							} else {
								if (isPlayerOneTurn) {
									playerOneScore++;
								} else {
									playerTwoScore++;
								}
								firstPlayerScoreTxt.setText("First Player: " + playerOneScore);
								secondPlayerScoreTxt.setText("Second Player: " + playerTwoScore);
							}
							firstCard = null;
							secondCard = null;
							repaint();
							addMouseListener(MyMouseListener.this);
							
							if (gameOver()) {
							    String winner;
							    if (playerOneScore > playerTwoScore) {
							        winner = "Player 1 wins!";
							    } else if (playerTwoScore > playerOneScore) {
							        winner = "Player 2 wins!";
							    } else {
							        winner = "It's a tie!";
							    }
							    JOptionPane.showMessageDialog(null, "Game Over!\n" + winner);
							}
						}
					});
					timer.setRepeats(false);
					timer.start();
				}
				
			}
			
		}
	}
	
}
