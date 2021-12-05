public class Card {
    private char value;
    private char suit;

    public Card(char value, char suit) {
        this.value = value;
        this.suit = suit;
    }

    public char getValue() {
        return value;
    }
    
    public char getSuit() {
        return suit;
    }

    public String toString() {
        return value + "" + suit;
    }
}
