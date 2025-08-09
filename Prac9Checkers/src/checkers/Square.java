package checkers;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Square {
	
	public static final int SIZE = 60;
	private Piece p = null;
	private int x, y;
	private boolean isDark;
	private int row, col;
	
	public Square(int row, int col,boolean isDark) {
		x = col * SIZE;
		y = row * SIZE;
		this.isDark = isDark;
		this.row = row;
		this.col = col;
		
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public Piece getPiece() {
		return p;
	}

	public void addPiece(Piece p){
		this.p = p;
	}
	
	public void removePiece(){
		p = null;
	}

	public void draw(Graphics g) {
		// TODO: draw the square

		if (isDark) {
	        g.setColor(Color.lightGray);
	    } else {
	        g.setColor(Color.WHITE);
	    }
	    g.fillRect(x, y, SIZE, SIZE);
	}	
}
