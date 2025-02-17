package cat.itacademy.s05.S05.enums;

public enum MoveType {
    HIT("Hit"),
    STAND("Stand");

    private final String move;

    MoveType(String move) {
        this.move = move;
    }

    public String getMove() {
        return move;
    }
}
