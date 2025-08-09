package colMatch;

import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;

public class Card {
	
	private Image frontImage, backImage;
	private String cardName;
	private int row, col;
	private boolean isFaceUp;
	private boolean isFocus;
	
	public static final int cardWidth = 90;
	public static final int cardHeight = 120;

	Card(String cardName) throws IOException{
		this.cardName = cardName;
		isFaceUp = false;
				
				//load front image
						try {
							// Load the front image for this card using its name (e.g., "1.png")
							frontImage = ImageIO.read(new File("images/" + cardName + ".png"));
						} catch (Exception e) {
							e.printStackTrace();
						}
					
					
					//load back image
					try {
						backImage = ImageIO.read(new File("images/bg.png"));
					} catch (IOException e) {
						e.printStackTrace();
					}		
	}

	// ADD REQUIRED ACCESSOR AND MUTATOR METHODS
	public String getCardName() {
		return cardName;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	public void setCol(int col) {
		this.col = col;
	}
	
	public boolean getFaceUp() {
		return isFaceUp;
	}
	
	public void draw(Graphics g, boolean isSelected) {
		int x = col * cardWidth ;
		int y = row * cardHeight;
		
		// Draw front if card image if face up
			if (isFaceUp) {
				g.drawImage(frontImage, x, y, cardWidth, cardHeight, null);	
			} else {
				// Draw back of card image if face-down,
				g.drawImage(backImage, x, y, cardWidth, cardHeight, null);	
			}	
			
			//higlight card selected
//			if (isSelected) {
//				g.setColor(Color.yellow);
//				g.drawRect(x - 2, y - 2, cardWidth + 5, cardHeight + 5);
//				g.drawRect(x - 1, y - 1, cardWidth + 2, cardHeight + 2);
//			}
	}
	
	public void flip() {
		isFaceUp = !isFaceUp;
	}

	public boolean isSame(Card card) {
		// return true if the 2 cards have the same name	
		if (getCardName().equals(card.getCardName())) {
			return true;
		}
		return false;
	}
}
