package answers

class Step8_InvalidFsmSpecOperationAnswer extends RuntimeException {
    Step8_InvalidFsmSpecOperationAnswer(operation) {
        super("Operation: ${operation} is invalid according to fsm specification")
    }
}
