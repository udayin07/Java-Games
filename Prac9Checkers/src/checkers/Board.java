package checkers;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel implements MouseListener {
	private static final int numRowCol = 8;
	private Square[][] squares = new Square[numRowCol][numRowCol];
	private Square firstSelected = null;
	private Square secondSelected = null;
	private boolean isBlackTurn = true;

	public Board() {
		// TODO create squares and add pieces to each square to match the default
		// configuration of the
		// board from the image
		this.addMouseListener(this);
		setPreferredSize(new Dimension(Square.SIZE * numRowCol, Square.SIZE * numRowCol));
		
		 // Initialize the squares
	    for (int row = 0; row < numRowCol; row++) {
	        for (int col = 0; col < numRowCol; col++) {
	            boolean isDark = (row + col) % 2 == 1;  // Dark squares are those where row + col is odd
	            squares[row][col] = new Square(row, col, isDark);

	            // Place pieces on the dark squares of the first 3 and last 3 rows
	            if (row < 3 && isDark) {
	                squares[row][col].addPiece(new Piece(Piece.RED));
	            } else if (row > 4 && isDark) {
	                squares[row][col].addPiece(new Piece(Piece.BLACK));
	            }
	        }
	    }
	}

	public void paintComponent(Graphics g) {
		// TODO draw each square
		 super.paintComponent(g);
		    for (int row = 0; row < numRowCol; row++) {
		        for (int col = 0; col < numRowCol; col++) {
		            squares[row][col].draw(g);
		            Piece p = squares[row][col].getPiece();
		            if (p != null) {
		                p.draw(g, squares[row][col].getX(), squares[row][col].getY(), Square.SIZE, Square.SIZE);
		            }
		        }
		    }
	}

	// TODO Select the first Square and then select the second square. Execute the
	// move IF IT IS VALID
	@Override
	public void mouseClicked(MouseEvent e) {
	    int col = e.getX() / Square.SIZE;
	    int row = e.getY() / Square.SIZE;

	    // Bounds check
	    if (row < 0 || row >= numRowCol || col < 0 || col >= numRowCol) return;

	    Square clicked = squares[row][col];

	    if (firstSelected == null) {
	        // First click: select a piece
	        Piece p = clicked.getPiece();
	        if (p != null && ((isBlackTurn && p.getColor() == Piece.BLACK) || (!isBlackTurn && p.getColor() == Piece.RED))) {
	            firstSelected = clicked;
	        }
	    } else {
	        // Second click: select destination
	        secondSelected = clicked;

	        if (isValidCapture(firstSelected, secondSelected)) {
	            capture();
	        } else if (isValidMove(firstSelected, secondSelected)) {
	            move();
	        } else {
	            // Invalid second selection, reset
	            firstSelected = null;
	            secondSelected = null;
	        }
	    }

	    repaint();
	}


	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	public boolean isValidMove(Square from, Square to) {
	    // Allow 1-step forward diagonal to empty square, no capture
	    if (to.getPiece() != null) return false;

	    int fromRow = from.getRow();
	    int fromCol = from.getCol();
	    int toRow = to.getRow();
	    int toCol = to.getCol();

	    int rowDiff = toRow - fromRow;
	    int colDiff = Math.abs(toCol - fromCol);

	    Piece piece = from.getPiece();
	    if (piece == null) return false;

	    if (!piece.isCrown()) {
	        if (piece.getColor() == Piece.BLACK && rowDiff != -1) return false;
	        if (piece.getColor() == Piece.RED && rowDiff != 1) return false;
	    } else {
	        if (Math.abs(rowDiff) != 1) return false;
	    }

	    return colDiff == 1;
	}

	public boolean isValidCapture(Square from, Square to) {
	    if (to.getPiece() != null) return false;

	    int fromRow = from.getRow();
	    int fromCol = from.getCol();
	    int toRow = to.getRow();
	    int toCol = to.getCol();

	    int rowDiff = toRow - fromRow;
	    int colDiff = toCol - fromCol;

	    if (Math.abs(rowDiff) != 2 || Math.abs(colDiff) != 2) return false;

	    int midRow = fromRow + rowDiff / 2;
	    int midCol = fromCol + colDiff / 2;

	    Piece movingPiece = from.getPiece();
	    if (movingPiece == null) return false;

	    Piece midPiece = squares[midRow][midCol].getPiece();
	    if (midPiece == null || midPiece.getColor() == movingPiece.getColor()) return false;

	    if (!movingPiece.isCrown()) {
	        if (movingPiece.getColor() == Piece.BLACK && rowDiff != -2) return false;
	        if (movingPiece.getColor() == Piece.RED && rowDiff != 2) return false;
	    }

	    return true;
	}

	public void capture() {
	    if (firstSelected == null || secondSelected == null) return;

	    int fromRow = firstSelected.getRow();
	    int fromCol = firstSelected.getCol();
	    int toRow = secondSelected.getRow();
	    int toCol = secondSelected.getCol();

	    int rowDiff = toRow - fromRow;
	    int colDiff = toCol - fromCol;

	    if (Math.abs(rowDiff) == 2 && Math.abs(colDiff) == 2) {
	        int midRow = fromRow + rowDiff / 2;
	        int midCol = fromCol + colDiff / 2;
	        Square middle = squares[midRow][midCol];
	        Piece middlePiece = middle.getPiece();
	        Piece movingPiece = firstSelected.getPiece();

	        if (middlePiece != null && movingPiece != null &&
	            middlePiece.getColor() != movingPiece.getColor()) { //if pieces are not the same and not the same color

	            middle.removePiece();  // Capture
	            move();                // Move and switch turn
	        }
	    }

	    // Clear selections (just in case)
	    firstSelected = null;
	    secondSelected = null;
	}




	public void move() {
	    if (firstSelected == null || secondSelected == null) return;

	    Piece movingPiece = firstSelected.getPiece();
	    if (movingPiece == null) return; //cannot move from empty

	    // Destination must be empty
	    if (secondSelected.getPiece() != null) return;

	    // Move the piece
	    firstSelected.removePiece();
	    secondSelected.addPiece(movingPiece);

	    // Crown the piece if it reaches the last row
	    int toRow = secondSelected.getRow();
	    if ((movingPiece.getColor() == Piece.BLACK && toRow == 0) ||
	        (movingPiece.getColor() == Piece.RED && toRow == 7)) {
	        movingPiece.makeCrown();
	    }

	    // Switch turns
	    isBlackTurn = !isBlackTurn;

	    // Clear selections
	    firstSelected = null;
	    secondSelected = null;

	    repaint();
	    checkWin();
	}

	
	public void checkWin() {
		// TODO check if one of the players has won the game after a move is executed 
		int blackCount = 0;
		int redCount = 0;
		
		for (int row = 0; row < numRowCol; row++)  {
			for (int col = 0; col < numRowCol; col++) {
				Piece p = squares[row][col].getPiece();
				if (p != null) {
					if (p.getColor() == Piece.BLACK) {
						blackCount++;
					} else if (p.getColor() == Piece.RED) {
						redCount++;
					}
				}
			}
		}
		if (blackCount == 0) {
	        JOptionPane.showMessageDialog(this, "Red wins!");
	    } else if (redCount == 0) {
	        JOptionPane.showMessageDialog(this, "Black wins!");
	    }
	}

}
