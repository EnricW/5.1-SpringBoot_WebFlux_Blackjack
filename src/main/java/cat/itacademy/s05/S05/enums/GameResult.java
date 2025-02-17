package cat.itacademy.s05.S05.enums;

public enum GameResult {
    WIN("Win"),
    LOSS("Loss"),
    TIE("Tie");

    private final String result;

    GameResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}