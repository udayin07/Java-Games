package snakeGame;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25; //pixel size
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; // 3600/25 = 144p
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	JButton btnRestart;
	Boolean showRestartButton = false;
	
	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
		btnRestart = new JButton("Restart?");
		btnRestart.setVisible(false);
		btnRestart.addActionListener(e -> restartGame());
		this.add(btnRestart);
		
		
		
	}
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
		
	}
	
	public void restartGame() {
	    bodyParts = 6;
	    applesEaten = 0;
	    direction = 'R';

	    // reset snake position
	    for (int i = 0; i < bodyParts; i++) {
	        x[i] = 0;
	        y[i] = 0;
	    }

	    running = true; 
	    showRestartButton = false;
	    btnRestart.setVisible(false);
	    startGame();              
	    this.requestFocusInWindow(); 
	    repaint();                
	}

	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
	 if (running) {
		
		//draw apple
		g.setColor(Color.RED);
		g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE); 
		
		//draw bodyparts of snake
			for(int i = 0; i < bodyParts; i++) {
				if (i == 0) { 
				//Draw head of the snake
				g.setColor(Color.GREEN);
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else { 
				// Draw body of snake
				g.setColor(new Color(45, 180, 0));
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			//draw score text
			g.setColor(Color.RED);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("SCORE: " + applesEaten,(SCREEN_WIDTH - metrics.stringWidth("SCORE: " + applesEaten))/2, g.getFont().getSize());
	  }
	 else {
		 //end game
		 gameOver(g);
		 
		 
		 
	 }
	}
	
	public void newApple() {
		//set x & y coordinates for apple to random squares in the grid
		appleX = random.nextInt((int)SCREEN_WIDTH/UNIT_SIZE) * UNIT_SIZE; //random int(24) * 25
		appleY = random.nextInt((int)SCREEN_WIDTH/UNIT_SIZE) * UNIT_SIZE;
	}
	
	public void move() {
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(direction) {
		case 'U' :
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D' :
			y[0] = y[0] +  UNIT_SIZE;
			break;
		case 'L' :
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R' :
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
		
	}
	
	public void checkApple() {
		if ((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	
	public void checkCollisions() {
		//checks if head collides with body
		for (int i = bodyParts; i > 0; i--) {
			if ((x[0] == x[i] && (y[0] == y[i]))) {
				running = false;
			}
		}
		//checks if head touches left border
		if (x[0] < 0) {
			running = false;
		}
		//checks if head touches right border
		if (x[0] > SCREEN_WIDTH) {
			running = false;
		}
		
		//checks if head touches top border
		if (y[0] < 0) {
			running = false;
		}
		
		//checks if head touches bottom border
		if (y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		if (!running) {
			timer.stop();
		}
		
	}
	
	public void gameOver(Graphics g) {
		//Game over text
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("GAME OVER",(SCREEN_WIDTH - metrics.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);
		
		showRestartButton = true;
		btnRestart.setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_UP :
				if (direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN :
				if (direction != 'U') {
					direction = 'D';
				}
				break;
			case KeyEvent.VK_RIGHT :
				if (direction != 'L') {
					direction = 'R';
				}
				break;
			}
		}
	}
	
}
