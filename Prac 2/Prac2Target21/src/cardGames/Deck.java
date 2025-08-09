package cardGames;
import java.util.Random;
public class Deck {

	/** An array of 52 Cards, representing the deck. */
	private Card[] deck;
	/** How many cards have been dealt from the deck. */
	private int cardsUsed;

	/**
	 * Constructs an unshuffled deck of cards. Creates and stores 52 cards
	 */
	public Deck() {
		deck = new Card[52];
		cardsUsed = 0;
		
		int index = 0;
		
		for (int suit = 0; suit < 4; suit++) {
			for (int value = 1; value <= 13; value++) {
				deck[index++] = new Card(suit,value);
			}
		}
		cardsUsed = 0;
	}

	/**
	 * Resets the cardsUsed and shuffles the full deck into a random
	 * order.
	 */
	public void shuffle() {
		Random rand = new Random();
		
		for (int i = deck.length - 1; i > 0; i--) {
			int j = rand.nextInt(i + 1);
			Card temp = deck[j];
			deck[i] = deck[j];
			deck[j] = temp;
			}
			cardsUsed = 0;
		}

	/**
	 * As cards are dealt from the deck, the number of cards left decreases. This
	 * function returns the number of cards that are still left in the deck.
	 * 
	 * @return Number of cards left in deck
	 */
	public int cardsLeft() {

		return 52 - cardsUsed;
	}

	/**
	 * Check if the deck is empty.
	 * 
	 * @return True if this deck as no cards else return false.
	 */
	public boolean isEmpty() {
		if (cardsUsed >= deck.length) {
			return true;
		}
		
		return false;
	}

	/**
	 * Deals one card from the deck and returns it.
	 * 
	 * @return <tt>cardGames.Card</tt> dealt
	 */
	public Card dealCard() {
		if (isEmpty()) {
			throw new IllegalStateException("No cards left in the deck");
		}
		return deck[cardsUsed++];
	}

}
