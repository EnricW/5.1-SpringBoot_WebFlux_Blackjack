package cat.itacademy.s05.S05.enums;

public enum GameState {
    IN_PROGRESS("In Progress"),
    ENDED("Ended");

    private final String displayName;

    GameState(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}