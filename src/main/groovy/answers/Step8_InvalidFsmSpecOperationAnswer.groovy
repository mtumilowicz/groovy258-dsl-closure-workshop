package answers

class Step8_InvalidFsmSpecOperationAnswer extends RuntimeException {
    Step8_InvalidFsmSpecOperationAnswer(String message) {
        super("Operation: ${message} is invalid according to fsm specification")
    }
}
