package cat.itacademy.s05.S05.model;

public class Player {
    private Long id;
    private String name;
    private int balance;

    public Player() {}

    public Player(String name) {
        this.name = name;
        this.balance = 1000; // Default balance
    }

    public void drawCard(Deck deck) {
        // Logic to draw card
    }

    public String getName() {
        return name;
    }
}