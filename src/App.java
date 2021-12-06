import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class App {
    public static void main(String[] args) throws Exception {

        // Take in hands from command line input then form a list using split

        Scanner scanner = new Scanner(System.in);
        int p1WinCount = 0;
        int p2WinCount = 0;
        while(scanner.hasNextLine()){
            String cards = scanner.nextLine();
            String[] player1 = cards.substring(0,14).split(" ");
            String[] player2 = cards.substring(15,29).split(" ");
            Hand playerHand1 = new Hand(player1);
            Hand playerHand2 = new Hand(player2);
            int winner = findWinner(playerHand1, playerHand2);
            if(winner == 1) p1WinCount++;
            if(winner == 2) p2WinCount++;
        }
        scanner.close();

        System.out.println("Player 1: " + p1WinCount);
        System.out.println("Player 2: " + p2WinCount);
    }

    private static int findWinner(Hand player1, Hand player2) {
        int player1Rank = player1.getHandRank();
        int player2Rank = player2.getHandRank();

        if(player1Rank > player2Rank) return 1;
        if(player2Rank > player1Rank) return 2;
        

        if(player1Rank == 10) {
            return 0;
        } else if(player1Rank == 9) {
            int p1 = player1.hasStraightFlush();
            int p2 = player2.hasStraightFlush();

            if(p1 == p2) return 0;

            return p1>p2 ? 1 : 2;

        } else if(player1Rank == 8) {
            List<Integer> player1CheckOrder = player1.hasFourOfAKind();
            List<Integer> player2CheckOrder = player2.hasFourOfAKind();
            for(int i = 0; i<2; i++) {
                if(player1CheckOrder.get(i) > player2CheckOrder.get(i)) return 1;
                if(player1CheckOrder.get(i) < player2CheckOrder.get(i)) return 2;
            }

            return 0;
        } else if(player1Rank == 7) {
            List<Integer> player1CheckOrder = player1.hasFullHouse();
            List<Integer> player2CheckOrder = player2.hasFullHouse();
            for(int i = 0; i<2; i++) {
                if(player1CheckOrder.get(i) > player2CheckOrder.get(i)) return 1;
                if(player1CheckOrder.get(i) < player2CheckOrder.get(i)) return 2;
            }

            return 0;
        } else if(player1Rank == 6) {
            List<Integer> player1CheckOrder = player1.hasFlush();
            List<Integer> player2CheckOrder = player2.hasFlush();
            for(int i = 0; i<5; i++) {
                if(player1CheckOrder.get(i) > player2CheckOrder.get(i)) return 1;
                if(player1CheckOrder.get(i) < player2CheckOrder.get(i)) return 2;
            }

            return 0;
        } else if(player1Rank == 5) {
            int p1 = player1.hasStraight();
            int p2 = player1.hasStraight();

            if(p1 == p2) return 0;

            return p1>p2 ? 1 : 2;
        } else if(player1Rank == 4) {
            List<Integer> player1CheckOrder = player1.hasThreeOfAKind();
            List<Integer> player2CheckOrder = player2.hasThreeOfAKind();
            for(int i = 0; i<3; i++) {
                if(player1CheckOrder.get(i) > player2CheckOrder.get(i)) return 1;
                if(player1CheckOrder.get(i) < player2CheckOrder.get(i)) return 2;
            }

            return 0;
        } else if(player1Rank == 3) {
            List<Integer> player1CheckOrder = player1.hasTwoPairs();
            List<Integer> player2CheckOrder = player2.hasTwoPairs();
            for(int i = 0; i<3; i++) {
                if(player1CheckOrder.get(i) > player2CheckOrder.get(i)) return 1;
                if(player1CheckOrder.get(i) < player2CheckOrder.get(i)) return 2;
            }

            return 0;
        } else if(player1Rank == 2) {
            List<Integer> player1CheckOrder = player1.hasPair();
            List<Integer> player2CheckOrder = player2.hasPair();
            for(int i = 0; i<4; i++) {
                if(player1CheckOrder.get(i) > player2CheckOrder.get(i)) return 1;
                if(player1CheckOrder.get(i) < player2CheckOrder.get(i)) return 2;
            }

            return 0;
        } else if(player1Rank == 1) {
            List<Integer> player1CheckOrder = player1.getValueInDescOrder();
            List<Integer> player2CheckOrder = player2.getValueInDescOrder();
            for(int i = 0; i<5; i++) {
                if(player1CheckOrder.get(i) > player2CheckOrder.get(i)) return 1;
                if(player1CheckOrder.get(i) < player2CheckOrder.get(i)) return 2;
            }
            return 0;
        }

        return 0;
    }
    // Make function to compare two hands

}
