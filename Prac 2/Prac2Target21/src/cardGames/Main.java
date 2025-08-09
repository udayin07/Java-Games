package cardGames;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// Write your code here
    	Scanner input = new Scanner(System.in);
        Deck d = new Deck();
        d.shuffle();
        
        System.out.println(d.dealCard());
    
        Hand player = new Hand(10);
        Hand dealer = new Hand(10);
        
        player.addCard(d.dealCard());
        
        player.addCard(d.dealCard());
        
        dealer.addCard(d.dealCard()); // face up
        
        Card dealerHidden = d.dealCard(); //face down
        
        System.out.println("Dealer's first card: " + dealer);
        
        System.out.println("Player's hand: " + player);
        System.out.println("Value of player's hand" + player.value());
        
        if (player.value() == 21 && dealer.value() != 21) {
        	System.out.println("You win!");
        	input.close();
        	return;
        } else if (dealer.value() == 21) {
        	System.out.println("You lose!");
        	input.close();
        	return;
        }
        
       
        while (true) {
        	System.out.println("Do you want to hit(h) or stand(s)? ");
        	String choice = input.next().toLowerCase();        	
        	if (choice.equals("h")) {
        		player.addCard(d.dealCard());
        		
        		System.out.println("Player's hand: " + player);
        		System.out.println("Player's hand value: " + player.value());
        		
        		if (player.value() > 21) {
        			System.out.println("Bust! You lose!");
        			input.close();
        			return;
        		}  	else if (choice.equals("s")){
        			break;
        		} else {
        			System.out.println("Invalid input. Please enter 'h' or 's'");
        		}	
        	}
        	
        	dealer.addCard(dealerHidden);
        	System.out.println("\nDealer's hand: " + dealer );
        	
        	while (dealer.value() < 16) {
        		dealer.addCard(d.dealCard());
        		System.out.println("Dealer drew: " + dealer);
        		
        		System.out.println("Dealer's final hand: " + dealer.value());
        		System.out.println("Player's final hand: " + player.value());
        		
        		
        		if (dealer.value() > 21 || player.value() > dealer.value()) {
        			System.out.println("You win!");
        		} else if (player.value() == dealer.value()) {
        			System.out.println("It's a draw!");
        		} else {
        			System.out.println("Dealer wins!");
        		}
        		
        	}
        }
        input.close();
    }
}
