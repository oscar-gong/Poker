import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        // Take in hands from command line input then form a list using split

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String cards = scanner.nextLine();
            String[] player1 = cards.substring(0,14).split(" ");
            String[] player2 = cards.substring(15,29).split(" ");
            // for(String a: player1){
            //     System.out.println(a);
            // }


        }
        scanner.close();

        System.out.println("Hello, World!");
    }

    // Make function to check each combination

    private static int isRoyalFlush(String[] hand){
        char suit = hand[0].charAt(1);
        for(String card: hand){
            if(suit != card.charAt(1)){
                return 0;
            }
        }
        return 1;
    }


    // Make function to compare two hands
}
