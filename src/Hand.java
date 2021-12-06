import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ListModel;

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
        if(hasFlush() == null) return false;

        // Old solution for unsorted hand
        // Set<Character> royalFlushValue = new HashSet<>();
        // royalFlushValue.add('T');
        // royalFlushValue.add('J');
        // royalFlushValue.add('Q');
        // royalFlushValue.add('K');
        // royalFlushValue.add('A');

        // // Add values of hand in set
        // Set<Character> values = new HashSet<>();
        // for(Card card:this.cards) {
        //     values.add(card.getValue());
        // }

        // // If the hand consists of 10 to Ace values
        // return royalFlushValue.equals(values);

        
        // Since hand is sorted, the highest and lowest hand should be T and A
        // assuming that there are no duplicate of the same value and suit.
        return cards.get(0).getValue() == 'T' && cards.get(4).getValue() == 'A';
    } 

    public int hasStraightFlush() {
        return (hasStraight() != 0 && hasFlush() != null) ? hasStraightFlush() : 0;
    } 
      
    public List<Integer> hasFourOfAKind() {
        return hasXOfAKind(4);
    } 

    public List<Integer> hasFullHouse() {
        return hasPair() != null && hasThreeOfAKind() != null ? hasThreeOfAKind() : null;
    }   

    public List<Integer> hasFlush() {
        char suit = this.cards.get(0).getSuit();

        ArrayList<Integer> checkOrder = new ArrayList<Integer>();

        for(Card card:this.cards) {
            if(card.getSuit() != suit)
                return null;
            checkOrder.add(getIntValue(card.getValue()));
        }
        Collections.sort(checkOrder, Collections.reverseOrder());


        return checkOrder;
    }

    // Returns 0 if no straight or highest value
    public int hasStraight() {
        List<Character> valueOrder = Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');
        
        char lowestValue = cards.get(0).getValue();
        int lowestIndex = valueOrder.indexOf(lowestValue);

        // If the lowest value is above 10 (9th index), it is impossible to get a straightflush
        if(lowestIndex > 9) return 0;

        for(int i = 0; i < 5; i++) {
            if(valueOrder.get(lowestIndex+i) != cards.get(i).getValue())
                return 0;
        }

        return cards.get(4).getValue();
    }
        
    public List<Integer> hasThreeOfAKind() {
        return hasXOfAKind(3);
    }
        
    public List<Integer> hasTwoPairs() {
        HashMap<Character, Integer> valueCount = new HashMap<Character, Integer>();

        for(Card card:cards) {
            if(valueCount.containsKey(card.getValue())){
                valueCount.put(card.getValue(), valueCount.get(card.getValue()) + 1);
            } else {
                valueCount.put(card.getValue(), 1);
            }
        }

        ArrayList<Integer> checkOrder = new ArrayList<Integer>();

        if(Collections.frequency(valueCount.values(), 2) != 2) return null;

        for(Character value: valueCount.keySet()) {
            if(valueCount.get(value) == 2) {
                checkOrder.add(getIntValue(value));
            }
        }
        Collections.sort(checkOrder, Collections.reverseOrder());
        for(Character value: valueCount.keySet()) {
            if(valueCount.get(value) == 1) {
                checkOrder.add(getIntValue(value));
            }
        }
        return checkOrder;
    }
        
    public List<Integer> hasPair() {
        return hasXOfAKind(2);
    }

    public List<Integer> hasXOfAKind(int x) {
        HashMap<Character, Integer> valueCount = new HashMap<Character, Integer>();

        for(Card card:cards) {
            if(valueCount.containsKey(card.getValue())){
                valueCount.put(card.getValue(), valueCount.get(card.getValue()) + 1);
            } else {
                valueCount.put(card.getValue(), 1);
            }
        }

        ArrayList<Integer> checkOrder = new ArrayList<Integer>();

        int firstCheck = -1;

        for(Character value: valueCount.keySet()) {
            if(valueCount.get(value) == x) {
                firstCheck = getIntValue(value);
            }
        }

        for(Card card:cards) {
            if(getIntValue(card.getValue()) != firstCheck) {
                checkOrder.add(getIntValue(card.getValue()));
            }
        }
        
        if(firstCheck == -1) return null;

        Collections.sort(checkOrder, Collections.reverseOrder());
        checkOrder.add(0, firstCheck);

        return checkOrder;
    }

    public List<Integer> getValueInDescOrder() {
        ArrayList<Integer> checkOrder = new ArrayList<Integer>();
        for(Card card: cards) {
            checkOrder.add(getIntValue(card.getValue()));
        }
        Collections.sort(checkOrder, Collections.reverseOrder());
        return checkOrder;
    }

    public int getHandRank() {
        int rank = 1;

        if(hasPair() != null) rank = 2;
        if(hasTwoPairs() != null)  rank = 3;
        if(hasThreeOfAKind() != null) rank = 4;
        if(hasStraight() != 0) rank = 5;
        if(hasFlush() != null) rank = 6;
        if(hasFullHouse() != null) rank = 7;
        if(hasFourOfAKind() != null) rank = 8;
        if(hasStraightFlush() != 0) rank = 9;
        if(hasRoyalFlush()) rank = 10;

        return rank;
    }

    public int getIntValue(char value) {
        if(value == '2') return 2;
        if(value == '3') return 3;
        if(value == '4') return 4;
        if(value == '5') return 5;
        if(value == '6') return 6;
        if(value == '7') return 7;
        if(value == '8') return 8;
        if(value == '9') return 9;
        if(value == 'T') return 10;
        if(value == 'J') return 11;
        if(value == 'Q') return 12;
        if(value == 'K') return 13;
        if(value == 'A') return 14;
        return 0;
    }

    public String toString() {
        String string = "";
        for(Card card:cards) {
            string = string + card.toString() + " ";
        }
        return string;
    }
}
