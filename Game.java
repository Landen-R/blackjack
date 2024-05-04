import java.util.Scanner;

public class Game {
    private Deck deck;
    private Dealer dealer;
    private Player player;
    private Scanner scanner;

    public Game() {
        deck = new Deck();
        dealer = new Dealer();
        player = new Player(1000); // Starting balance for the player
        scanner = new Scanner(System.in);
        startGame();
    }

    private void startGame() {
        System.out.println("Welcome to Black Jack!");
        boolean play = true;
        while (play) {
            System.out.println("You have $" + player.balance);
            int bet = takeBet();

            // Clear hands before dealing new cards
            player.clearHand();
            dealer.clearHand();

            dealInitialCards();

            if (playerTurn()) { // If player hasn't busted
                if (dealerTurn()) { // Dealer takes turn
                    determineWinner(bet);
                } else {
                    System.out.println("Dealer busts, player wins!");
                    player.balance += bet * 2;
                }
            } else {
                System.out.println("Player busts, dealer wins!");
            }

            play = promptForAnotherRound();
        }
        scanner.close();
        System.out.println("Thank you for playing!");
    }

    private boolean promptForAnotherRound() {
        while (true) {
            System.out.println("Do you want to play another round? (yes/no)");
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("yes") || response.equals("y")) {
                return true;
            } else if (response.equals("no") || response.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please answer 'yes' or 'no'.");
            }
        }
    }

    private boolean dealerTurn() {
        System.out.println("Dealer's turn.");
        while (dealer.shouldDraw()) {
            dealer.addCard(deck.drawCard());
        }
        dealer.printHand();
        return !dealer.isBusted();
    }

    private void determineWinner(int bet) {
        if (player.isBusted()) {
            System.out.println("Dealer wins as player busted!");
        } else if (dealer.isBusted() || player.getHandValue() > dealer.getHandValue()) {
            System.out.println("Player wins!");
            player.balance += bet * 2; // Player wins the bet
        } else if (player.getHandValue() < dealer.getHandValue()) {
            System.out.println("Dealer wins!");
        } else {
            System.out.println("It's a tie!");
            player.balance += bet; // Return the bet
        }
    }

    private int takeBet() {
        System.out.println("Place your bet:");
        int bet = scanner.nextInt();
        scanner.nextLine(); // consume newline left-over
        while (bet > player.balance || bet <= 0) {
            System.out.println("Invalid bet. Your balance is $" + player.balance + ". Bet again:");
            bet = scanner.nextInt();
            scanner.nextLine();
        }
        player.balance -= bet;
        return bet;
    }

    private void dealInitialCards() {
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());

        System.out.println("Your hand:");
        player.printHand();
        System.out.println("Dealer's first card:");
        System.out.println(dealer.hand.get(0));
    }

    private boolean playerTurn() {
        while (true) {
            System.out.println("Do you want to hit or stand?");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("hit")) {
                player.addCard(deck.drawCard());
                player.printHand();
                if (player.isBusted()) {
                    System.out.println("You are busted!");
                    return false;
                }
            } else if (choice.equalsIgnoreCase("stand")) {
                return true;
            }
        }
    }
}
