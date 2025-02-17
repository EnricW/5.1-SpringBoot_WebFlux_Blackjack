package cat.itacademy.s05.S05.enums;

public enum Rank {
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 10),
    QUEEN("Q", 10),
    KING("K", 10),
    ACE("A", 11);

    private final String rank;
    private final int value;

    Rank(String rank, int value) {
        this.rank = rank;
        this.value = value;
    }

    public String getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

    public int getAdjustedValue(int currentTotal) {
        if (this == ACE && currentTotal + value > 21) {
            return 1;
        }
        return value;
    }
}
