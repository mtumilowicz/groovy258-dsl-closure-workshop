package answers

class InvalidFsmSpecOperation extends RuntimeException {
    InvalidFsmSpecOperation(String message) {
        super("Operation: ${message} is invalid according to fsm specification")
    }
}
