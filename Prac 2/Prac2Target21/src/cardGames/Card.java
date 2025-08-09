package cardGames; /**
 * 
 */

public class Card {

	public static final int HEARTS = 0;
	public static final int CLUBS = 1;
	public static final int SPADES = 2;
	public static final int DIAMONDS = 3;

	private int suit;
	private int value;

	/**
	 * @param suit
	 * @param value
	 */
	public Card(int suit, int value) {
		this.suit = suit;
		this.value = value;
	}

	/**
	 * Represent the card as string for printing Now the cardGames.Card can be printed with
	 * println
	 */
	public String toString() {
		return "cardGames.Card [suit= " + getSuitAsString() + ", value=" + getValueAsString() + "]";
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @return the suit
	 */
	public int getSuit() {
		return suit;
	}
	
	public String getSuitAsString() {
		switch (suit) {
		case 0 : return "H";
		case 1 : return "D";
		case 2 : return "C";
		case 3 : return "S";
		default : return "?";
		}
	}
	
	public String getValueAsString() {
		
		if (value == 1) return "A";
		if (value >= 2 && value <= 10)
	return String.valueOf(value);
		if (value == 11) return "J";
		if (value == 12) return "Q";
		if (value == 13) return "K";
		return "?";
	}
}
