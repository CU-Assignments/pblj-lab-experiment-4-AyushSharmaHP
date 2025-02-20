import java.util.*;

class Card {
    String suit;
    String rank;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

class CardCollection {
    private Map<String, List<Card>> cardMap;
    private Set<String> cardSet;

    public CardCollection() {
        cardMap = new HashMap<>();
        cardSet = new HashSet<>();
    }

    public void addCard(String rank, String suit) {
        String cardKey = rank + " of " + suit;
        if (cardSet.contains(cardKey)) {
            System.out.println("Error: Card \"" + cardKey + "\" already exists.");
            return;
        }
        
        Card card = new Card(rank, suit);
        cardSet.add(cardKey);
        cardMap.computeIfAbsent(suit, k -> new ArrayList<>()).add(card);
        
        System.out.println("Card added: " + card);
    }

    public void findCardsBySuit(String suit) {
        if (!cardMap.containsKey(suit) || cardMap.get(suit).isEmpty()) {
            System.out.println("No cards found for " + suit + ".");
            return;
        }
        
        for (Card card : cardMap.get(suit)) {
            System.out.println(card);
        }
    }

    public void displayAllCards() {
        if (cardSet.isEmpty()) {
            System.out.println("No cards found.");
            return;
        }
        
        for (List<Card> cards : cardMap.values()) {
            for (Card card : cards) {
                System.out.println(card);
            }
        }
    }

    public void removeCard(String rank, String suit) {
        String cardKey = rank + " of " + suit;
        if (!cardSet.contains(cardKey)) {
            System.out.println("Error: Card \"" + cardKey + "\" does not exist.");
            return;
        }
        
        cardSet.remove(cardKey);
        List<Card> cards = cardMap.get(suit);
        cards.removeIf(card -> card.rank.equals(rank));
        if (cards.isEmpty()) cardMap.remove(suit);
        
        System.out.println("Card removed: " + cardKey);
    }
}

public class CardCollectionSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CardCollection collection = new CardCollection();

        while (true) {
            System.out.println("\nChoose an option: \n1. Add Card \n2. Find Cards by Suit \n3. Display All Cards \n4. Remove Card \n5. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter rank: ");
                    String rank = scanner.nextLine();
                    System.out.print("Enter suit: ");
                    String suit = scanner.nextLine();
                    collection.addCard(rank, suit);
                    break;
                case 2:
                    System.out.print("Enter suit to find: ");
                    String searchSuit = scanner.nextLine();
                    collection.findCardsBySuit(searchSuit);
                    break;
                case 3:
                    collection.displayAllCards();
                    break;
                case 4:
                    System.out.print("Enter rank to remove: ");
                    String removeRank = scanner.nextLine();
                    System.out.print("Enter suit to remove: ");
                    String removeSuit = scanner.nextLine();
                    collection.removeCard(removeRank, removeSuit);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}
