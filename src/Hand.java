import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// instead of returning true false, maybe return highest value card of rank or -1 if condition is false

public class Hand {
    private ArrayList<Card> cards;

    public Hand(String[] hand) {
        this.cards = new ArrayList<Card>();
        for(String cardString: hand) {
            Card card = new Card(cardString.charAt(0), cardString.charAt(1));
            this.cards.add(card);
        }

        // Sort hand by order 
        List<Character> valueOrder = Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');
        this.cards.sort(Comparator.comparing(a -> valueOrder.indexOf(a.getValue())));
    }

    public boolean hasRoyalFlush() {
        // Check if all cards have the same suit
        if(!hasFlush()) return false;

        Set<Character> royalFlushValue = new HashSet<>();
        royalFlushValue.add('T');
        royalFlushValue.add('J');
        royalFlushValue.add('Q');
        royalFlushValue.add('K');
        royalFlushValue.add('A');

        // Add values of hand in set
        Set<Character> values = new HashSet<>();
        for(Card card:this.cards) {
            values.add(card.getValue());
        }

        // If the hand consists of 10 to Ace values
        return royalFlushValue.equals(values);
    } 

    public boolean hasStraightFlush() {
        return hasStraight() && hasFlush();
    } 
      
    public boolean hasFourOfAKind() {
        return hasXOfAKind(4);
    } 

    public boolean hasFullHouse() {
        return hasPair() && hasThreeOfAKind();
    }   

    public boolean hasFlush() {
        char suit = this.cards.get(0).getSuit();

        for(Card card:this.cards) {
            if(card.getSuit() != suit)
                return false;
        }
        return true;
    }

    public boolean hasStraight() {
        List<Character> valueOrder = Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');
        
        char lowestValue = cards.get(0).getValue();
        int lowestIndex = valueOrder.indexOf(lowestValue);

        // If the lowest value is above 10 (9th index), it is impossible to get a straightflush
        if(lowestIndex > 9) return false;

        for(int i = 0; i < 5; i++) {
            if(valueOrder.get(lowestIndex+i) != cards.get(i).getValue())
                return false;
        }
        return true;
    }
        
    public boolean hasThreeOfAKind() {
        return hasXOfAKind(3);
    }
        
    public boolean hasTwoPairs() {
        HashMap<Character, Integer> valueCount = new HashMap<Character, Integer>();

        for(Card card:cards) {
            if(valueCount.containsKey(card.getValue())){
                valueCount.put(card.getValue(), valueCount.get(card.getValue()) + 1);
            } else {
                valueCount.put(card.getValue(), 1);
            }
        }

        return Collections.frequency(valueCount.values(), 2) == 2;
    }
        
    public boolean hasPair() {
        return hasXOfAKind(2);
    }

    public boolean hasXOfAKind(int x) {
        HashMap<Character, Integer> valueCount = new HashMap<Character, Integer>();

        for(Card card:cards) {
            if(valueCount.containsKey(card.getValue())){
                valueCount.put(card.getValue(), valueCount.get(card.getValue()) + 1);
            } else {
                valueCount.put(card.getValue(), 1);
            }
        }

        return valueCount.containsValue(x);
    }

    public String toString() {
        String string = "";
        for(Card card:cards) {
            string = string + card.toString() + " ";
        }
        return string;
    }
}
