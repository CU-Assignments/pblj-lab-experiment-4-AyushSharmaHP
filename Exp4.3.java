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
        if (!cardSet.add(cardKey)) {
            System.out.println("Error: Card \"" + cardKey + "\" already exists.");
            return;
        }
        
        cardMap.computeIfAbsent(suit, k -> new ArrayList<>()).add(new Card(rank, suit));
        System.out.println("Card added: " + cardKey);
    }

    public void findCardsBySuit(String suit) {
        List<Card> cards = cardMap.getOrDefault(suit, Collections.emptyList());
        if (cards.isEmpty()) {
            System.out.println("No cards found for " + suit + ".");
            return;
        }
        cards.forEach(System.out::println);
    }

    public void displayAllCards() {
        if (cardSet.isEmpty()) {
            System.out.println("No cards found.");
            return;
        }
        
        cardMap.values().stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparing(c -> c.suit + c.rank))
                .forEach(System.out::println);
    }

    public void removeCard(String rank, String suit) {
        String cardKey = rank + " of " + suit;
        if (!cardSet.remove(cardKey)) {
            System.out.println("Error: Card \"" + cardKey + "\" does not exist.");
            return;
        }
        
        cardMap.getOrDefault(suit, Collections.emptyList()).removeIf(card -> card.rank.equals(rank));
        cardMap.values().removeIf(List::isEmpty);
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
}Experiment 4.3: Ticket Booking System

This program simulates a ticket booking system where multiple users (threads) try to book seats at the same time. The key challenges addressed are:

1) Avoiding Double Booking → Using synchronized methods to ensure no two users book the same seat.
2) Prioritizing VIP Customers → Using thread priorities so VIP users' bookings are processed before regular users.

📌 Core Concepts Used
️1 Synchronized Booking Method
The method bookSeat() is marked as synchronized, ensuring that only one thread can access it at a time.
This prevents race conditions, where two threads might try to book the same seat simultaneously.
  
️2 Thread Priorities for VIP Customers
Threads representing VIP users are assigned Thread.MAX_PRIORITY so they execute first.
Regular users have Thread.NORM_PRIORITY or Thread.MIN_PRIORITY, making them process later.

3 Handling Multiple Users
Each user trying to book a seat is represented by a thread.
Users can select a seat, and if it’s already booked, they receive an error message.


Step-by-Step Execution
1 Initialize the TicketBookingSystem → Allows booking of N seats.
2 Create Multiple Booking Threads → Each user (VIP or Regular) is assigned a thread.
3 Start All Threads → Threads compete for booking, with VIPs processed first.
4 Ensure No Double Booking → synchronized method prevents duplicate seat allocation.
5 Threads Finish Execution & Display Booking Status.


🔹 Why Use Synchronization?
Without synchronized, two threads might book the same seat simultaneously, causing double booking issues. Using synchronized, only one thread at a time can modify the seat booking data.

🔹 Why Use Thread Priorities?
Setting higher priority for VIP users ensures their bookings are processed first, simulating real-world priority-based bookings.

Test Cases

Test Case 1: No Seats Available Initially
Input:
System starts with 5 seats.
No users attempt to book.
Expected Output:
No bookings yet.

Test Case 2: Successful Booking
Input:
Anish (VIP) books Seat 1.
Bobby (Regular) books Seat 2.
Charlie (VIP) books Seat 3.
Expected Output:
Anish (VIP) booked seat 1
Bobby (Regular) booked seat 2
Charlie (VIP) booked seat 3

Test Case 3: Thread Priorities (VIP First)
Input:
Bobby (Regular) books Seat 4 (low priority).
Anish (VIP) books Seat 4 (high priority).
Expected Output:
Anish (VIP) booked seat 4
Bobby (Regular): Seat 4 is already booked!

Test Case 4: Preventing Double Booking
Input:
Anish (VIP) books Seat 1.
Bobby (Regular) tries to book Seat 1 again.
Expected Output:
Anish (VIP) booked seat 1
Bobby (Regular): Seat 1 is already booked!

Test Case 5: Booking After All Seats Are Taken
Input:
All 5 seats are booked.
A new user (Regular) tries to book Seat 3.
Expected Output:
Error: Seat 3 is already booked!

Test Case 6: Invalid Seat Selection
Input:
User tries to book Seat 0 (out of range).
User tries to book Seat 6 (beyond available seats).
Expected Output:
Invalid seat number!

Test Case 7: Simultaneous Bookings (Concurrency Test)
Input:
10 users try booking at the same time for 5 seats.
Expected Output:
5 users successfully book seats.
5 users receive error messages for already booked seats.
