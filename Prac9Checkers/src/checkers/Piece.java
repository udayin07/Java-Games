package checkers;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Piece {

	public static final int BLACK = 0;
	public static final int RED = 1;

	private int color;
	private Image image = null;
	private boolean isCrown = false;

	public Piece(int color) {
		// TODO display image of correct piece in the square 
		 this.color = color;
		    try {
		        if (color == Piece.RED) {
		            image = ImageIO.read(new File("images/red.gif"));
		        } else if (color == Piece.BLACK) {
		            image = ImageIO.read(new File("images/black.gif"));
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	}	

	public int getColor() {
		return color;
	}
	
	public boolean isCrown() {
		return isCrown;
	}

	public void draw(Graphics g, int x, int y, int height, int width) {
		// TODO draw the image in the square
		if (image != null) {
	        g.drawImage(image, x + 5, y + 5, width - 10, height - 10, null);
	    }
		
	}
	
	public void makeCrown() {
		// TODO display image of the correct king piece in the square
		isCrown = true;
		
		try {
			if (color == Piece.RED) {
				image = ImageIO.read(new File("images/redcrown.png"));
			} else if (color == Piece.BLACK){
				image = ImageIO.read(new File("images/blackcrown.gif"));
			}
		} catch (Exception e) {
			e.getMessage();
			System.out.println("Cannot load crowned image");
		}
	}
}
