package cat.itacademy.s05.S05.exception.custom;

public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(String message) {
        super(message);
    }
}
