package answers

class Step9_InvalidFsmSpecOperationAnswer extends RuntimeException {
    Step9_InvalidFsmSpecOperationAnswer(operation) {
        super("Operation: ${operation} is invalid according to fsm specification")
    }
}
