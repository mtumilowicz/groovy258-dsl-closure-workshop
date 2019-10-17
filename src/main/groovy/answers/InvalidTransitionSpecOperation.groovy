package answers

class InvalidTransitionSpecOperation extends RuntimeException {
    InvalidTransitionSpecOperation(String message) {
        super("Operation: ${message} is invalid according to transition specification")
    }
}
