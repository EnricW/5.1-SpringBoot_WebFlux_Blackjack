package cat.itacademy.s05.S05.enums;

import cat.itacademy.s05.S05.exception.custom.InvalidMoveException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PlayerMove {
    HIT("Hit"),
    STAND("Stand");

    private final String displayName;

    PlayerMove(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    @Override
    public String toString() {
        return displayName;
    }

    @JsonCreator
    public static PlayerMove fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidMoveException("Move cannot be empty.");
        }
        for (PlayerMove move : PlayerMove.values()) {
            if (move.displayName.equalsIgnoreCase(value) || move.name().equalsIgnoreCase(value)) {
                return move;
            }
        }
        throw new InvalidMoveException("Invalid move provided: " + value);
    }
}
