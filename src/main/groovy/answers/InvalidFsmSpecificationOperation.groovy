package answers

class InvalidFsmSpecificationOperation extends RuntimeException {
    InvalidFsmSpecificationOperation(String message) {
        super("Operation: ${message} is invalid according to fsm specification")
    }
}
