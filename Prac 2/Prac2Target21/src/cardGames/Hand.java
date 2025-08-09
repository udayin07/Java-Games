package cardGames; 

public class Hand {
	private Card[] hand;
	private int cardCount;
	
	/** Creates a hand to hold num cards */
	public Hand(int num) {		
		hand = new Card[num];
		cardCount = 0;
	}
	
	/** Adds a card to this hand */
	public void addCard(Card card) {
		if (cardCount < hand.length) {
			hand[cardCount++] = card;
		} else {
			System.out.println("Hand is full!");
		}
	}

	/** Removes the card at index i from this hand */
	public void removeCard() {
		if (cardCount > 0) {
			hand[--cardCount] = null;
		} else {
		System.out.println("Cannot remove card since hand is empty!");
		}
	}
	
	/** Returns the value of the cards in this hand 
	 * 
	 * @return value of hand*/
	public int value() {
		int total = 0;
		int aceCount = 0;
		
		for (int i = 0; i < cardCount; i++) {
			int val = hand[i].getValue();
			if (val > 10) {
					total +=10;
				} else if (val == 1){
					aceCount++;
					 total+=11;
				} else {
					total+= val;
				}
			}
		while (total > 21 && aceCount > 0) {
			total -= 10;
			aceCount--;
		}
		return total;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cardCount; i++) {
				sb.append(hand[i].toString()).append(" ");
			}
		return sb.toString().trim();
	}
	
}
